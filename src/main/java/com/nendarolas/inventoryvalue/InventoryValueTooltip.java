package com.nendarolas.inventoryvalue;

import java.awt.Color;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ColorUtil;

class InventoryValueTooltip
{
	private static final Color TOOLTIP_TEXT = new Color(238, 238, 238);

	private final Client client;
	private final NendaRolasInventoryValueConfig config;
	private final PriceService priceService;
	private final InventorySlotResolver slotResolver;
	private final InventorySlotExclusions slotExclusions;
	private final TooltipManager tooltipManager;

	@Inject
	InventoryValueTooltip(
		Client client,
		NendaRolasInventoryValueConfig config,
		PriceService priceService,
		InventorySlotResolver slotResolver,
		InventorySlotExclusions slotExclusions,
		TooltipManager tooltipManager)
	{
		this.client = client;
		this.config = config;
		this.priceService = priceService;
		this.slotResolver = slotResolver;
		this.slotExclusions = slotExclusions;
		this.tooltipManager = tooltipManager;
	}

	void addTooltip()
	{
		if (!config.showTooltipValue() || client.isMenuOpen())
		{
			return;
		}

		final MenuEntry[] menuEntries = client.getMenu().getMenuEntries();
		if (menuEntries.length == 0)
		{
			return;
		}

		final MenuEntry menuEntry = menuEntries[menuEntries.length - 1];
		final Widget widget = menuEntry.getWidget();
		if (widget == null || menuEntry.getParam1() != InterfaceID.Inventory.ITEMS)
		{
			return;
		}

		final int slot = slotResolver.getSlot(widget);
		if (slotExclusions.isExcluded(slot))
		{
			return;
		}

		final int itemId = widget.getItemId();
		final int quantity = widget.getItemQuantity();
		final String tooltip = buildTooltip(itemId, quantity);
		if (tooltip != null)
		{
			tooltipManager.add(new Tooltip(ColorUtil.prependColorTag(tooltip, TOOLTIP_TEXT)));
		}
	}

	private String buildTooltip(int itemId, int quantity)
	{
		if (itemId <= 0)
		{
			return null;
		}

		if (!priceService.isGeTradeable(itemId))
		{
			return config.showUntradeable() ? "GE value: " + config.untradeableLabel() : null;
		}

		final long unitValue = priceService.getValue(itemId, 1, false);
		final long stackValue = priceService.getValue(itemId, quantity, true);
		if (stackValue <= 0)
		{
			return null;
		}

		if (quantity > 1)
		{
			return "GE value: " + ValueFormatter.format(stackValue) + " (unit: " + ValueFormatter.format(unitValue) + ")";
		}

		return "GE value: " + ValueFormatter.format(unitValue);
	}
}

package com.nendarolas.inventoryvalue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

class InventoryTotalValueOverlay extends Overlay
{
	private static final Color BACKGROUND = new Color(0, 0, 0, 150);
	private static final Color SHADOW = new Color(0, 0, 0, 190);
	private static final int[] INVENTORY_ICONS = {
		InterfaceID.Toplevel.ICON3,
		InterfaceID.ToplevelOsrsStretch.ICON3,
		InterfaceID.ToplevelPreEoc.ICON3
	};
	private static final int PADDING_X = 4;
	private static final int PADDING_Y = 2;

	private final Client client;
	private final NendaRolasInventoryValueConfig config;
	private final PriceService priceService;
	private final ValueColorResolver valueColorResolver;
	private final InventorySlotExclusions slotExclusions;

	@Inject
	InventoryTotalValueOverlay(
		Client client,
		NendaRolasInventoryValueConfig config,
		PriceService priceService,
		ValueColorResolver valueColorResolver,
		InventorySlotExclusions slotExclusions)
	{
		this.client = client;
		this.config = config;
		this.priceService = priceService;
		this.valueColorResolver = valueColorResolver;
		this.slotExclusions = slotExclusions;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showTotal())
		{
			return null;
		}

		final Widget inventoryIcon = getVisibleInventoryIcon();
		if (inventoryIcon == null)
		{
			return null;
		}

		final ItemContainer inventoryContainer = client.getItemContainer(InventoryID.INV);
		if (inventoryContainer == null)
		{
			return null;
		}

		long total = 0;
		final Item[] items = inventoryContainer.getItems();
		for (int i = 0; i < items.length; i++)
		{
			if (!slotExclusions.isExcluded(i + 1))
			{
				total += priceService.getValue(items[i], true);
			}
		}

		final String text = ValueFormatter.format(total);
		final Font originalFont = graphics.getFont();
		final Rectangle bounds = inventoryIcon.getBounds();
		Font font = originalFont.deriveFont(Font.BOLD, Math.max(8, config.textSize()));
		graphics.setFont(font);

		FontMetrics metrics = graphics.getFontMetrics();
		while (metrics.stringWidth(text) + PADDING_X * 2 > bounds.width + 12 && font.getSize() > 8)
		{
			font = font.deriveFont((float) font.getSize() - 1);
			graphics.setFont(font);
			metrics = graphics.getFontMetrics();
		}

		final int width = Math.min(bounds.width + 12, metrics.stringWidth(text) + PADDING_X * 2);
		final int height = metrics.getHeight() + PADDING_Y * 2;
		final int x = bounds.x + (bounds.width - width) / 2;
		final int y = bounds.y + bounds.height - height + 2;
		final int textX = x + (width - metrics.stringWidth(text)) / 2;
		final int textY = y + PADDING_Y + metrics.getAscent();

		graphics.setColor(BACKGROUND);
		graphics.fillRoundRect(x, y, width, height, 6, 6);
		graphics.setColor(SHADOW);
		graphics.drawString(text, textX + 1, textY + 1);
		graphics.setColor(valueColorResolver.getColor(total));
		graphics.drawString(text, textX, textY);
		graphics.setFont(originalFont);

		return new Dimension(width, height);
	}

	private Widget getVisibleInventoryIcon()
	{
		for (int componentId : INVENTORY_ICONS)
		{
			final Widget widget = client.getWidget(componentId);
			if (widget != null && !widget.isHidden())
			{
				final Rectangle bounds = widget.getBounds();
				if (bounds != null && bounds.width > 0 && bounds.height > 0)
				{
					return widget;
				}
			}
		}

		return null;
	}
}

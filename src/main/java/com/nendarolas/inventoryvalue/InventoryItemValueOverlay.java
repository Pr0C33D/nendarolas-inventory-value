package com.nendarolas.inventoryvalue;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

class InventoryItemValueOverlay extends WidgetItemOverlay
{
	private static final Color SHADOW = new Color(0, 0, 0, 180);

	private final NendaRolasInventoryValueConfig config;
	private final PriceService priceService;
	private final ValueColorResolver valueColorResolver;
	private final InventorySlotResolver slotResolver;
	private final InventorySlotExclusions slotExclusions;

	@Inject
	InventoryItemValueOverlay(
		NendaRolasInventoryValueConfig config,
		PriceService priceService,
		ValueColorResolver valueColorResolver,
		InventorySlotResolver slotResolver,
		InventorySlotExclusions slotExclusions)
	{
		this.config = config;
		this.priceService = priceService;
		this.valueColorResolver = valueColorResolver;
		this.slotResolver = slotResolver;
		this.slotExclusions = slotExclusions;
		showOnInterfaces(net.runelite.api.gameval.InterfaceID.INVENTORY);
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
	{
		if (slotExclusions.isExcluded(slotResolver.getSlot(widgetItem)))
		{
			return;
		}

		if (!priceService.isGeTradeable(itemId))
		{
			if (config.showUntradeable())
			{
				drawLabel(graphics, widgetItem, config.untradeableLabel(), config.untradeableColor());
			}
			return;
		}

		final long value = priceService.getValue(itemId, widgetItem.getQuantity(), config.showStackValue());
		if (value < config.minimumValue())
		{
			return;
		}

		drawLabel(graphics, widgetItem, ValueFormatter.format(value), valueColorResolver.getColor(value));
	}

	private void drawLabel(Graphics2D graphics, WidgetItem widgetItem, String text, Color color)
	{
		if (text == null || text.isEmpty())
		{
			return;
		}

		final Rectangle bounds = widgetItem.getCanvasBounds();
		final Font originalFont = graphics.getFont();
		final Font font = originalFont.deriveFont(Font.BOLD, config.textSize());

		graphics.setFont(font);
		final FontMetrics metrics = graphics.getFontMetrics();
		final int x = bounds.x + (bounds.width - metrics.stringWidth(text)) / 2;
		final int y = bounds.y + bounds.height - 2;

		graphics.setColor(SHADOW);
		graphics.drawString(text, x + 1, y + 1);
		graphics.setColor(color);
		graphics.drawString(text, x, y);
		graphics.setFont(originalFont);
	}
}

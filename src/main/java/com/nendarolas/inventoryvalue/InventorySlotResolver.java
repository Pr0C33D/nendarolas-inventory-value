package com.nendarolas.inventoryvalue;

import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetItem;

class InventorySlotResolver
{
	private static final int COLUMNS = 4;
	private static final int ROWS = 7;

	private final Client client;

	@Inject
	InventorySlotResolver(Client client)
	{
		this.client = client;
	}

	int getSlot(WidgetItem widgetItem)
	{
		final int widgetSlot = getSlot(widgetItem.getWidget());
		if (widgetSlot != -1)
		{
			return widgetSlot;
		}

		final Widget inventory = client.getWidget(InterfaceID.Inventory.ITEMS);
		if (inventory == null)
		{
			return -1;
		}

		final Rectangle inventoryBounds = inventory.getBounds();
		final Rectangle itemBounds = widgetItem.getCanvasBounds();
		if (inventoryBounds == null || itemBounds == null || inventoryBounds.width <= 0 || inventoryBounds.height <= 0)
		{
			return -1;
		}

		final int centerX = itemBounds.x + itemBounds.width / 2;
		final int centerY = itemBounds.y + itemBounds.height / 2;
		final int col = clamp((centerX - inventoryBounds.x) * COLUMNS / inventoryBounds.width, 0, COLUMNS - 1);
		final int row = clamp((centerY - inventoryBounds.y) * ROWS / inventoryBounds.height, 0, ROWS - 1);
		return row * COLUMNS + col + 1;
	}

	int getSlot(Widget widget)
	{
		if (widget == null)
		{
			return -1;
		}

		final int widgetIndex = widget.getIndex();
		return widgetIndex >= 0 && widgetIndex < COLUMNS * ROWS ? widgetIndex + 1 : -1;
	}

	private int clamp(int value, int min, int max)
	{
		return Math.max(min, Math.min(max, value));
	}
}

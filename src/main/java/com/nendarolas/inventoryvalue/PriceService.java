package com.nendarolas.inventoryvalue;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.client.game.ItemManager;

@Singleton
class PriceService
{
	@Inject
	private ItemManager itemManager;

	long getUnitPrice(int itemId)
	{
		if (itemId <= 0)
		{
			return 0;
		}

		final int canonicalId = getCanonicalItemId(itemId);
		if (!isGeTradeable(canonicalId))
		{
			return 0;
		}

		return Math.max(0L, itemManager.getItemPrice(canonicalId));
	}

	boolean isGeTradeable(int itemId)
	{
		if (itemId <= 0)
		{
			return false;
		}

		return itemManager.getItemComposition(getCanonicalItemId(itemId)).isGeTradeable();
	}

	long getValue(Item item, boolean stackValue)
	{
		if (item == null || item.getId() <= 0)
		{
			return 0;
		}

		return getValue(item.getId(), item.getQuantity(), stackValue);
	}

	long getValue(int itemId, int quantity, boolean stackValue)
	{
		if (itemId <= 0)
		{
			return 0;
		}

		final long price = getUnitPrice(itemId);
		if (price <= 0)
		{
			return 0;
		}

		return stackValue ? price * Math.max(1, quantity) : price;
	}

	private int getCanonicalItemId(int itemId)
	{
		final ItemComposition composition = itemManager.getItemComposition(itemId);
		return composition.getNote() != -1 ? composition.getLinkedNoteId() : itemId;
	}
}

package com.nendarolas.inventoryvalue;

import javax.inject.Inject;

class InventorySlotExclusions
{
	private static final int MIN_SLOT = 1;
	private static final int MAX_SLOT = 28;

	private final NendaRolasInventoryValueConfig config;

	@Inject
	InventorySlotExclusions(NendaRolasInventoryValueConfig config)
	{
		this.config = config;
	}

	boolean isExcluded(int slot)
	{
		if (slot < MIN_SLOT || slot > MAX_SLOT)
		{
			return false;
		}

		final String excludedSlots = config.excludedSlots();
		if (excludedSlots == null || excludedSlots.trim().isEmpty())
		{
			return false;
		}

		for (String part : excludedSlots.split(","))
		{
			if (matchesPart(slot, part.trim()))
			{
				return true;
			}
		}

		return false;
	}

	private boolean matchesPart(int slot, String part)
	{
		if (part.isEmpty())
		{
			return false;
		}

		final int dash = part.indexOf('-');
		if (dash >= 0)
		{
			final Integer start = parseSlot(part.substring(0, dash));
			final Integer end = parseSlot(part.substring(dash + 1));
			if (start == null || end == null)
			{
				return false;
			}

			return slot >= Math.min(start, end) && slot <= Math.max(start, end);
		}

		final Integer singleSlot = parseSlot(part);
		return singleSlot != null && slot == singleSlot;
	}

	private Integer parseSlot(String value)
	{
		try
		{
			final int slot = Integer.parseInt(value.trim());
			return slot >= MIN_SLOT && slot <= MAX_SLOT ? slot : null;
		}
		catch (NumberFormatException ex)
		{
			return null;
		}
	}
}

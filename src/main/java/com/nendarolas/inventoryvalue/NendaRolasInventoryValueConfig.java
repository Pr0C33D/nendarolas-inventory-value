package com.nendarolas.inventoryvalue;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("nendarolasinventoryvalue")
public interface NendaRolasInventoryValueConfig extends Config
{
	@ConfigItem(
		keyName = "showTotal",
		name = "Show inventory total",
		description = "Show the total Grand Exchange value on the inventory icon",
		position = 0
	)
	default boolean showTotal()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showStackValue",
		name = "Show stack value",
		description = "Show item stack value instead of one item value",
		position = 1
	)
	default boolean showStackValue()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showUntradeable",
		name = "Show untradeable",
		description = "Show a label under items that are not Grand Exchange tradeable",
		position = 2
	)
	default boolean showUntradeable()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showTooltipValue",
		name = "Show tooltip value",
		description = "Show item value when hovering over inventory items",
		position = 3
	)
	default boolean showTooltipValue()
	{
		return true;
	}

	@ConfigItem(
		keyName = "untradeableLabel",
		name = "Untradeable label",
		description = "Text shown under untradeable items",
		position = 4
	)
	default String untradeableLabel()
	{
		return "UNTR";
	}

	@ConfigItem(
		keyName = "untradeableColor",
		name = "Untradeable color",
		description = "Color of the untradeable item label",
		position = 5
	)
	default Color untradeableColor()
	{
		return new Color(190, 190, 190);
	}

	@Range(
		min = 8,
		max = 18
	)
	@ConfigItem(
		keyName = "textSize",
		name = "Text size",
		description = "Size of the value labels shown on inventory items",
		position = 6
	)
	default int textSize()
	{
		return 11;
	}

	@Range(
		min = 0
	)
	@ConfigItem(
		keyName = "minimumValue",
		name = "Minimum shown value",
		description = "Hide per-item labels below this Grand Exchange value",
		position = 7
	)
	default int minimumValue()
	{
		return 1;
	}

	@ConfigItem(
		keyName = "excludedSlots",
		name = "Excluded slots",
		description = "Inventory slots to ignore, for example: 1,2,5-8. Slots are counted left-to-right, top-to-bottom.",
		position = 8
	)
	default String excludedSlots()
	{
		return "";
	}

	@Range(
		min = 0
	)
	@ConfigItem(
		keyName = "tierOneLimit",
		name = "Tier 1 limit",
		description = "Values below this use the tier 1 color",
		position = 10
	)
	default int tierOneLimit()
	{
		return 5_000;
	}

	@Range(
		min = 0
	)
	@ConfigItem(
		keyName = "tierTwoLimit",
		name = "Tier 2 limit",
		description = "Values below this use the tier 2 color",
		position = 11
	)
	default int tierTwoLimit()
	{
		return 25_000;
	}

	@Range(
		min = 0
	)
	@ConfigItem(
		keyName = "tierThreeLimit",
		name = "Tier 3 limit",
		description = "Values below this use the tier 3 color",
		position = 12
	)
	default int tierThreeLimit()
	{
		return 75_000;
	}

	@Range(
		min = 0
	)
	@ConfigItem(
		keyName = "tierFourLimit",
		name = "Tier 4 limit",
		description = "Values below this use the tier 4 color; higher values use the tier 5 color",
		position = 13
	)
	default int tierFourLimit()
	{
		return 250_000;
	}

	@ConfigItem(
		keyName = "tierOneColor",
		name = "Tier 1 color",
		description = "Color for values under the tier 1 limit",
		position = 20
	)
	default Color tierOneColor()
	{
		return new Color(210, 210, 210);
	}

	@ConfigItem(
		keyName = "tierTwoColor",
		name = "Tier 2 color",
		description = "Color for values between tier 1 and tier 2",
		position = 21
	)
	default Color tierTwoColor()
	{
		return new Color(120, 220, 120);
	}

	@ConfigItem(
		keyName = "tierThreeColor",
		name = "Tier 3 color",
		description = "Color for values between tier 2 and tier 3",
		position = 22
	)
	default Color tierThreeColor()
	{
		return new Color(100, 190, 255);
	}

	@ConfigItem(
		keyName = "tierFourColor",
		name = "Tier 4 color",
		description = "Color for values between tier 3 and tier 4",
		position = 23
	)
	default Color tierFourColor()
	{
		return new Color(255, 180, 70);
	}

	@ConfigItem(
		keyName = "tierFiveColor",
		name = "Tier 5 color",
		description = "Color for values above the tier 4 limit",
		position = 24
	)
	default Color tierFiveColor()
	{
		return new Color(255, 90, 180);
	}

	@ConfigItem(
		keyName = "bugReportContact",
		name = "Bug report contact",
		description = "Send bug reports or suggestions to this email",
		position = 99
	)
	default String bugReportContact()
	{
		return "nendarolas@gmail.com";
	}
}

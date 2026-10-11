package com.nendarolas.inventoryvalue;

import java.util.Locale;

final class ValueFormatter
{
	private ValueFormatter()
	{
	}

	static String format(long value)
	{
		if (value >= 1_000_000_000L)
		{
			return trim(value / 1_000_000_000.0) + "B";
		}

		if (value >= 1_000_000L)
		{
			return trim(value / 1_000_000.0) + "M";
		}

		if (value >= 1_000L)
		{
			return trim(value / 1_000.0) + "K";
		}

		return Long.toString(value);
	}

	private static String trim(double value)
	{
		final String formatted = value >= 10
			? String.format(Locale.ROOT, "%.0f", value)
			: String.format(Locale.ROOT, "%.1f", value);
		return formatted.endsWith(".0") ? formatted.substring(0, formatted.length() - 2) : formatted;
	}
}

package com.nendarolas.inventoryvalue;

import java.awt.Color;
import javax.inject.Inject;

class ValueColorResolver
{
	private final NendaRolasInventoryValueConfig config;

	@Inject
	ValueColorResolver(NendaRolasInventoryValueConfig config)
	{
		this.config = config;
	}

	Color getColor(long value)
	{
		if (value < config.tierOneLimit())
		{
			return config.tierOneColor();
		}

		if (value < config.tierTwoLimit())
		{
			return config.tierTwoColor();
		}

		if (value < config.tierThreeLimit())
		{
			return config.tierThreeColor();
		}

		if (value < config.tierFourLimit())
		{
			return config.tierFourColor();
		}

		return config.tierFiveColor();
	}
}

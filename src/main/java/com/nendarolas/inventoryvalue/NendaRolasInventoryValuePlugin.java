package com.nendarolas.inventoryvalue;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.events.BeforeRender;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "NendaRolas Inventory Value",
	description = "Shows Grand Exchange values on inventory items and a total inventory value",
	tags = {"ge", "grand exchange", "inventory", "items", "price", "value"}
)
public class NendaRolasInventoryValuePlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private InventoryItemValueOverlay itemValueOverlay;

	@Inject
	private InventoryTotalValueOverlay totalValueOverlay;

	@Inject
	private InventoryValueTooltip inventoryValueTooltip;

	@Override
	protected void startUp()
	{
		overlayManager.add(itemValueOverlay);
		overlayManager.add(totalValueOverlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(itemValueOverlay);
		overlayManager.remove(totalValueOverlay);
	}

	@Subscribe
	public void onBeforeRender(BeforeRender event)
	{
		inventoryValueTooltip.addTooltip();
	}

	@Provides
	NendaRolasInventoryValueConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NendaRolasInventoryValueConfig.class);
	}
}

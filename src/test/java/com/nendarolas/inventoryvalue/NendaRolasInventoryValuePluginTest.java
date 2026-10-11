package com.nendarolas.inventoryvalue;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class NendaRolasInventoryValuePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(NendaRolasInventoryValuePlugin.class);
		RuneLite.main(args);
	}
}

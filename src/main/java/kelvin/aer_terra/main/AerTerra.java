package kelvin.aer_terra.main;

import kelvin.aer_terra.registry.Initialization;
import net.fabricmc.api.ModInitializer;

public class AerTerra implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
		Initialization.Init();
	}
}

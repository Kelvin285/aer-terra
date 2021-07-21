package kelvin.aer_terra.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BiomeRegistry {
	public static RegistryKey<Biome> AER_TERRA_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier("aer_terra", "aer_terra"));

	public static Biome AER_TERRA;
	public static void RegisterBiomes()
	{
		//AER_TERRA = serverWorld.getServer().registryAccess().registryOrThrow(Registry.BIOME).get(myBiomeRegistryKey);
	}

}

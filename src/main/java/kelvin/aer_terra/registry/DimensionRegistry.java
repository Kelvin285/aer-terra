package kelvin.aer_terra.registry;

import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionType;

public class DimensionRegistry {

    public static final RegistryKey<DimensionType> AER_TERRA = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier("aer_terra:aer_terra"));
    public static void RegisterDimensions() {
        CustomPortalApiRegistry.addPortal(Blocks.STONE_BRICKS, new Identifier("aer_terra:aer_terra"), 104, 154, 171);
    }
}

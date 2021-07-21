package kelvin.trewrite.registry;

import kelvin.trewrite.main.Trewrite;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

public class DimensionRegistry {
    public static void RegisterDimensions() {
        CustomPortalApiRegistry.addPortal(Blocks.STONE_BRICKS, PortalIgnitionSource.WATER, new Identifier("trewrite:aer_terra"), 104, 154, 171);
    }
}

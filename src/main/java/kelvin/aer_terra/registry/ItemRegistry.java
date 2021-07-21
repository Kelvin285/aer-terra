package kelvin.aer_terra.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    public static Item RIFTSTONE, TERRA_GRASS_BLOCK, TERRA_DIRT, TERRASTONE, TERRA_SAND;

    private static Item register(String id, Item item) {
        return (Item) Registry.register(Registry.ITEM, id, item);
    }

    public static void RegisterItems() {
        RIFTSTONE = register("aer_terra:riftstone", new BlockItem(BlockRegistry.RIFTSTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_GRASS_BLOCK = register("aer_terra:terra_grass_block", new BlockItem(BlockRegistry.TERRA_GRASS_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_DIRT = register("aer_terra:terra_dirt", new BlockItem(BlockRegistry.TERRA_DIRT, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRASTONE = register("aer_terra:terrastone", new BlockItem(BlockRegistry.TERRASTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_SAND = register("aer_terra:terra_sand", new BlockItem(BlockRegistry.TERRA_SAND, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

    }
}

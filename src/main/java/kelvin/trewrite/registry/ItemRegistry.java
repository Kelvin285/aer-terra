package kelvin.trewrite.registry;

import net.minecraft.block.Block;
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
        RIFTSTONE = register("trewrite:riftstone", new BlockItem(BlockRegistry.RIFTSTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_GRASS_BLOCK = register("trewrite:terra_grass_block", new BlockItem(BlockRegistry.TERRA_GRASS_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_DIRT = register("trewrite:terra_dirt", new BlockItem(BlockRegistry.TERRA_DIRT, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRASTONE = register("trewrite:terrastone", new BlockItem(BlockRegistry.TERRASTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_SAND = register("trewrite:terra_sand", new BlockItem(BlockRegistry.TERRA_SAND, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

    }
}

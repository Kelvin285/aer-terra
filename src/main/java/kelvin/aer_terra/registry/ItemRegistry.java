package kelvin.aer_terra.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    public static Item ELECTRIFIED_TERRASTONE, TERRA_GRASS_BLOCK, TERRA_DIRT, TERRASTONE, TERRA_SAND,
    ELECTRIFIED_DIRT, ELECTRIFIED_GRASS_BLOCK, ELECTRIFIED_SAND, TERRAFROST_STONE, TERRAFROST_DIRT,
    TERRAFROST_GRASS_BLOCK, TERRAFROST_SAND, FLAMESTONE, FIRE_GRASS_BLOCK, FIRE_SAND, FIRE_DIRT,
    TERRAWOOD_LOG, TERRAWOOD_LEAVES, TERRAWOOD_SAPLING, TERRA_GRASS, TERRAFROST_GRASS, FIRE_GRASS,
    ELECTRIFIED_GRASS;

    private static Item register(String id, Item item) {
        return (Item) Registry.register(Registry.ITEM, id, item);
    }

    public static void RegisterItems() {
        ELECTRIFIED_TERRASTONE = register("aer_terra:electrified_terrastone", new BlockItem(BlockRegistry.ELECTRIFIED_TERRASTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_GRASS_BLOCK = register("aer_terra:terra_grass_block", new BlockItem(BlockRegistry.TERRA_GRASS_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_DIRT = register("aer_terra:terra_dirt", new BlockItem(BlockRegistry.TERRA_DIRT, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRASTONE = register("aer_terra:terrastone", new BlockItem(BlockRegistry.TERRASTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_SAND = register("aer_terra:terra_sand", new BlockItem(BlockRegistry.TERRA_SAND, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        ELECTRIFIED_DIRT = register("aer_terra:electrified_dirt", new BlockItem(BlockRegistry.ELECTRIFIED_DIRT, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        ELECTRIFIED_GRASS_BLOCK = register("aer_terra:electrified_grass_block", new BlockItem(BlockRegistry.ELECTRIFIED_GRASS_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        ELECTRIFIED_SAND = register("aer_terra:electrified_sand", new BlockItem(BlockRegistry.ELECTRIFIED_SAND, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRAFROST_STONE = register("aer_terra:terrafrost_stone", new BlockItem(BlockRegistry.TERRAFROST_STONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRAFROST_DIRT = register("aer_terra:terrafrost_dirt", new BlockItem(BlockRegistry.TERRAFROST_DIRT, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRAFROST_GRASS_BLOCK = register("aer_terra:terrafrost_grass_block", new BlockItem(BlockRegistry.TERRAFROST_GRASS_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRAFROST_SAND = register("aer_terra:terrafrost_sand", new BlockItem(BlockRegistry.TERRAFROST_SAND, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        FLAMESTONE = register("aer_terra:flamestone", new BlockItem(BlockRegistry.FLAMESTONE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        FIRE_GRASS_BLOCK = register("aer_terra:fire_grass_block", new BlockItem(BlockRegistry.FIRE_GRASS_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        FIRE_SAND = register("aer_terra:fire_sand", new BlockItem(BlockRegistry.FIRE_SAND, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        FIRE_DIRT = register("aer_terra:fire_dirt", new BlockItem(BlockRegistry.FIRE_DIRT, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRAWOOD_LOG = register("aer_terra:terrawood_log", new BlockItem(BlockRegistry.TERRAWOOD_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRAWOOD_LEAVES = register("aer_terra:terrawood_leaves", new BlockItem(BlockRegistry.TERRAWOOD_LEAVES, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRAWOOD_SAPLING = register("aer_terra:terrawood_sapling", new BlockItem(BlockRegistry.TERRAWOOD_SAPLING, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRA_GRASS = register("aer_terra:terra_grass", new BlockItem(BlockRegistry.TERRA_GRASS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        ELECTRIFIED_GRASS = register("aer_terra:electrified_grass", new BlockItem(BlockRegistry.ELECTRIFIED_GRASS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        FIRE_GRASS = register("aer_terra:fire_grass", new BlockItem(BlockRegistry.FIRE_GRASS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        TERRAFROST_GRASS = register("aer_terra:terrafrost_grass", new BlockItem(BlockRegistry.TERRAFROST_GRASS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
    }
}

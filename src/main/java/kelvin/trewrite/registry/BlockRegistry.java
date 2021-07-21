package kelvin.trewrite.registry;

import kelvin.trewrite.blocks.TerraGrassBlock;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {

	public static Block RIFTSTONE;
	public static Block TERRASTONE;
	public static Block TERRA_GRASS_BLOCK;
	public static Block TERRA_SAND;
	public static Block TERRA_DIRT;

	public static void RegisterBlocks() {
		RIFTSTONE = register("trewrite:riftstone", new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)));
		TERRA_GRASS_BLOCK = register("trewrite:terra_grass_block", new TerraGrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK)));
		TERRA_DIRT = register("trewrite:terra_dirt", new Block(AbstractBlock.Settings.copy(Blocks.DIRT)));
		TERRA_SAND = register("trewrite:terra_sand", new Block(AbstractBlock.Settings.copy(Blocks.SAND)));
		TERRASTONE = register("trewrite:terrastone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	}

	private static Block register(String id, Block block) {
		return (Block)Registry.register(Registry.BLOCK, id, block);
	}
}

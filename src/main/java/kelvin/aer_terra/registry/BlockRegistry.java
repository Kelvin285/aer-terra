package kelvin.aer_terra.registry;

import kelvin.aer_terra.blocks.TerraGrassBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {

	public static Block RIFTSTONE;
	public static Block TERRASTONE;
	public static Block TERRA_GRASS_BLOCK;
	public static Block TERRA_SAND;
	public static Block TERRA_DIRT;

	public static void RegisterBlocks() {
		RIFTSTONE = register("aer_terra:riftstone", new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)));
		TERRA_GRASS_BLOCK = register("aer_terra:terra_grass_block", new TerraGrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK)));
		TERRA_DIRT = register("aer_terra:terra_dirt", new Block(AbstractBlock.Settings.copy(Blocks.DIRT)));
		TERRA_SAND = register("aer_terra:terra_sand", new Block(AbstractBlock.Settings.copy(Blocks.SAND)));
		TERRASTONE = register("aer_terra:terrastone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	}

	private static Block register(String id, Block block) {
		return (Block)Registry.register(Registry.BLOCK, id, block);
	}
}

package kelvin.trewrite.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class BlockRegistry {
	
	public static Block EBON_GRASS;
	public static Block EBON_STONE;
	public static Block EBON_LEAVES;
	public static Block EBON_SAND;
	public static Block EBON_SANDSTONE;
	public static Block EBON_LOG;
	public static Block EBON_PLANKS;
	public static Block VILE_MUSHROOM;
	
	public static Block CRIMSON_GRASS;
	public static Block CRIMSON_STONE;
	public static Block CRIMSON_LEAVES;
	public static Block CRIMSON_SAND;
	public static Block CRIMSON_SANDSTONE;
	public static Block SHADEWOOD_LOG;
	public static Block SHADEWOOD_PLANKS;
	public static Block VICIOUS_MUSHROOM;
	
	public static Block BLUE_CRYSTAL;
	public static Block MUD;
	
	public static Block GLOWING_MUSHROOM_BLOCK;
	
	public static Block MUSHROOM_GRASS;
	public static Block GLOWING_MUSHROOM;
	public static Block GLOWING_MUSHROOM_STEM;
	public static Block GLOWING_MUSHROOM_TOP;
	
	public static Block MARBLE;
	public static Block GRANITE;
	
	public static Block JUNGLE_GRASS;
	
	public static void RegisterBlocks() {
		EBON_GRASS = Blocks.MYCELIUM;
		EBON_STONE = Blocks.ANDESITE;
		EBON_LEAVES = Blocks.AZALEA_LEAVES;
		EBON_SAND = Blocks.RED_SAND;
		EBON_SANDSTONE = Blocks.RED_SANDSTONE;
		EBON_LOG = Blocks.BIRCH_LOG;
		EBON_PLANKS = Blocks.BIRCH_PLANKS;
		VILE_MUSHROOM = Blocks.BROWN_MUSHROOM;
		
		CRIMSON_GRASS = Blocks.PODZOL;
		CRIMSON_STONE = Blocks.GRANITE;
		CRIMSON_LEAVES = Blocks.AZALEA_LEAVES_FLOWERS;
		CRIMSON_SAND = Blocks.RED_CONCRETE_POWDER;
		CRIMSON_SANDSTONE = Blocks.RED_CONCRETE;
		SHADEWOOD_LOG = Blocks.ACACIA_LOG;
		SHADEWOOD_PLANKS = Blocks.ACACIA_PLANKS;
		VICIOUS_MUSHROOM = Blocks.RED_MUSHROOM;
		
		BLUE_CRYSTAL = Blocks.AMETHYST_BLOCK;
		MUD = Blocks.COARSE_DIRT;
		
		GLOWING_MUSHROOM_BLOCK = Blocks.BROWN_MUSHROOM_BLOCK;
		
		MUSHROOM_GRASS = Blocks.MOSS_BLOCK;
		GLOWING_MUSHROOM = Blocks.WARPED_FUNGUS;
		GLOWING_MUSHROOM_STEM = Blocks.WARPED_HYPHAE;
		GLOWING_MUSHROOM_TOP = Blocks.WARPED_WART_BLOCK;
		
		MARBLE = Blocks.DIORITE;
		GRANITE = Blocks.QUARTZ_BLOCK;
		
		JUNGLE_GRASS = Blocks.ROOTED_DIRT;
	}
}

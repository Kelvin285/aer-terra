package kelvin.aer_terra.registry;

import kelvin.aer_terra.blocks.*;
import kelvin.aer_terra.blocks.FireBlock;
import kelvin.aer_terra.world.features.TerrawoodSaplingGenerator;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class BlockRegistry {

	public static Block ELECTRIFIED_TERRASTONE;
	public static Block ELECTRIFIED_GRASS_BLOCK;
	public static Block ELECTRIFIED_DIRT;
	public static Block ELECTRIFIED_SAND;
	public static Block TERRAFROST_GRASS_BLOCK;
	public static Block TERRAFROST_DIRT;
	public static Block TERRAFROST_STONE;
	public static Block TERRAFROST_SAND;
	public static Block FLAMESTONE;
	public static Block FIRE_GRASS_BLOCK;
	public static Block FIRE_DIRT;
	public static Block FIRE_SAND;
	public static Block TERRASTONE;
	public static Block TERRA_GRASS_BLOCK;
	public static Block TERRA_SAND;
	public static Block TERRA_DIRT;
	public static Block TERRAWOOD_LOG;
	public static Block TERRAWOOD_LEAVES;
	public static Block TERRAWOOD_SAPLING;
	public static Block TERRA_GRASS;
	public static Block TERRAFROST_GRASS;
	public static Block ELECTRIFIED_GRASS;
	public static Block FIRE_GRASS;

	public static ArrayList<Block> dirt = new ArrayList<Block>();
	public static ArrayList<Block> logs = new ArrayList<Block>();

	private static PillarBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
		return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (state) -> {
			return state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor;
		}).strength(2.0F).sounds(BlockSoundGroup.WOOD));
	}

	public static void RegisterBlocks() {
		TERRA_DIRT = register("aer_terra:terra_dirt", new Block(AbstractBlock.Settings.copy(Blocks.DIRT)));
		TERRA_GRASS_BLOCK = register("aer_terra:terra_grass_block", new TerraGrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK), BlockRegistry.TERRA_DIRT));
		TERRA_SAND = register("aer_terra:terra_sand", new FallingBlock(AbstractBlock.Settings.copy(Blocks.SAND)));
		TERRASTONE = register("aer_terra:terrastone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));

		TERRAFROST_DIRT = register("aer_terra:terrafrost_dirt", new Block(AbstractBlock.Settings.copy(Blocks.DIRT).slipperiness(Blocks.ICE.getSlipperiness())));
		TERRAFROST_GRASS_BLOCK = register("aer_terra:terrafrost_grass_block", new TerraGrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).slipperiness(Blocks.ICE.getSlipperiness()), BlockRegistry.TERRA_DIRT));
		TERRAFROST_SAND = register("aer_terra:terrafrost_sand", new FallingBlock(AbstractBlock.Settings.copy(Blocks.SAND).slipperiness(Blocks.ICE.getSlipperiness())));
		TERRAFROST_STONE = register("aer_terra:terrafrost_stone", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).sounds(BlockSoundGroup.STONE).slipperiness(Blocks.ICE.getSlipperiness())));

		FIRE_DIRT = register("aer_terra:fire_dirt", new FireBlock(AbstractBlock.Settings.copy(Blocks.DIRT)));
		FIRE_GRASS_BLOCK = register("aer_terra:fire_grass_block", new FireGrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK), BlockRegistry.FIRE_DIRT));
		FIRE_SAND = register("aer_terra:fire_sand", new FireSandBlock(AbstractBlock.Settings.copy(Blocks.SAND)));
		FLAMESTONE = register("aer_terra:flamestone", new ColdFireBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));

		ELECTRIFIED_DIRT = register("aer_terra:electrified_dirt", new ElectrifiedBlock(AbstractBlock.Settings.copy(Blocks.DIRT).velocityMultiplier(1.15F).slipperiness(Blocks.ICE.getSlipperiness())));
		ELECTRIFIED_GRASS_BLOCK = register("aer_terra:electrified_grass_block", new ElectrifiedGrassBlock(AbstractBlock.Settings.copy(Blocks.DIRT).velocityMultiplier(1.15F).slipperiness(Blocks.ICE.getSlipperiness()), BlockRegistry.ELECTRIFIED_DIRT, new Block[]{BlockRegistry.ELECTRIFIED_DIRT, BlockRegistry.TERRA_DIRT, BlockRegistry.TERRAFROST_DIRT, BlockRegistry.FIRE_DIRT}));
		ELECTRIFIED_TERRASTONE = register("aer_terra:electrified_terrastone", new ElectrifiedBlock(AbstractBlock.Settings.copy(Blocks.OBSIDIAN).sounds(BlockSoundGroup.STONE).velocityMultiplier(1.15F).slipperiness(Blocks.ICE.getSlipperiness())));
		ELECTRIFIED_SAND = register("aer_terra:electrified_sand", new ElectrifiedFallingBlock(AbstractBlock.Settings.copy(Blocks.SAND).velocityMultiplier(1.15F).slipperiness(Blocks.ICE.getSlipperiness())));

		TERRAWOOD_LOG = register("aer_terra:terrawood_log", createLogBlock(MapColor.BROWN, MapColor.SPRUCE_BROWN));
		TERRAWOOD_LEAVES = register("aer_terra:terrawood_leaves", new TerraLeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
		TERRAWOOD_SAPLING = register("aer_terra:terrawood_sapling", new TerraSaplingBlock(new TerrawoodSaplingGenerator(), AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));

		TERRA_GRASS = register("aer_terra:terra_grass", new TerraPlantBlock(AbstractBlock.Settings.copy(Blocks.GRASS)));
		ELECTRIFIED_GRASS = register("aer_terra:electrified_grass", new TerraPlantBlock(AbstractBlock.Settings.copy(Blocks.GRASS)));
		TERRAFROST_GRASS = register("aer_terra:terrafrost_grass", new TerraPlantBlock(AbstractBlock.Settings.copy(Blocks.GRASS)));
		FIRE_GRASS = register("aer_terra:fire_grass", new TerraPlantBlock(AbstractBlock.Settings.copy(Blocks.GRASS)));

		dirt.add(TERRA_DIRT);
		dirt.add(ELECTRIFIED_DIRT);
		dirt.add(TERRAFROST_DIRT);
		dirt.add(FIRE_DIRT);
		dirt.add(TERRA_GRASS_BLOCK);
		dirt.add(ELECTRIFIED_GRASS_BLOCK);
		dirt.add(TERRAFROST_GRASS_BLOCK);
		dirt.add(FIRE_GRASS_BLOCK);

		logs.add(TERRAWOOD_LOG);

		BlockRenderLayerMap.INSTANCE.putBlock(TERRAWOOD_LEAVES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TERRAWOOD_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TERRA_GRASS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TERRAFROST_GRASS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ELECTRIFIED_GRASS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(FIRE_GRASS, RenderLayer.getCutout());

	}

	private static Block register(String id, Block block) {
		return (Block)Registry.register(Registry.BLOCK, id, block);
	}
}

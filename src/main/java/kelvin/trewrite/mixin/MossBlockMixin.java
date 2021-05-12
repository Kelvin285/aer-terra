package kelvin.trewrite.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.MossBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

@Mixin(MossBlock.class)
public class MossBlockMixin extends Block implements Fertilizable {
	
	public MossBlockMixin(Settings settings) {
		super(settings);
	}
	
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.up()).isAir();
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return world.getBlockState(pos.up()).isAir();
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		Feature.VEGETATION_PATCH.generate(new FeatureContext(world, world.getChunkManager().getChunkGenerator(), random,
				pos.up(), ConfiguredFeatures.WARPED_FOREST_VEGETATION.getConfig()));
	}

}

package kelvin.aer_terra.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Random;

public class FireGrassBlock extends FireBlock {
    private Block[] spread_to_block;
    private Block dirt;
    public FireGrassBlock(AbstractBlock.Settings settings, Block dirt, Block[] spreadable) {
        super(settings);
        this.spread_to_block = spreadable;
        this.dirt = dirt;
    }

    public FireGrassBlock(AbstractBlock.Settings settings, Block dirt) {
        super(settings);
        this.dirt = dirt;
        spread_to_block = new Block[]{dirt};
    }

    private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(state, world, pos)) {
            world.setBlockState(pos, dirt.getDefaultState());
        } else {
            if (world.getLightLevel(pos.up()) >= 9) {
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

                    for (int j = 0; j < spread_to_block.length; j++) {
                        if (world.getBlockState(blockPos).isOf(spread_to_block[j]) && canSpread(getDefaultState(), world, blockPos)) {
                            world.setBlockState(blockPos, getDefaultState());
                        }
                    }

                }
            }

        }
    }
}

package kelvin.aer_terra.blocks;

import kelvin.aer_terra.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class TerraLeavesBlock extends LeavesBlock {
    public TerraLeavesBlock(Settings settings) {
        super(settings);
    }

    private static int getDistanceFromLog(BlockState state) {
        if (BlockRegistry.logs.contains(state.getBlock())) {
            return 0;
        } else {
            return state.getBlock() instanceof TerraLeavesBlock ? (Integer)state.get(DISTANCE) : 7;
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int i = getDistanceFromLog(neighborState) + 1;
        if (i != 1 || (Integer)state.get(DISTANCE) != i) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        return state;
    }

    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
        int distance = 7;
        BlockPos.Mutable mutable_pos = new BlockPos.Mutable();

        Direction[] directions = Direction.values();

        for(int i = 0; i < directions.length; ++i) {
            Direction direction = directions[i];
            mutable_pos.set(pos, direction);
            distance = Math.min(distance, getDistanceFromLog(world.getBlockState(mutable_pos)) + 1);
            if (distance == 1) {
                break;
            }
        }

        return (BlockState)state.with(DISTANCE, distance);
    }
}

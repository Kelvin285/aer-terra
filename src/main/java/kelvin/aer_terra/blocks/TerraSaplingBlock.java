package kelvin.aer_terra.blocks;

import kelvin.aer_terra.registry.BlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TerraSaplingBlock extends SaplingBlock {
    public TerraSaplingBlock(SaplingGenerator generator, AbstractBlock.Settings settings) {
        super(generator, settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return BlockRegistry.dirt.contains(floor.getBlock());
    }
}

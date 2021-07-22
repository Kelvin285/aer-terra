package kelvin.aer_terra.blocks;

import kelvin.aer_terra.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TerraPlantBlock extends PlantBlock {
    public TerraPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return BlockRegistry.dirt.contains(floor.getBlock());
    }
}

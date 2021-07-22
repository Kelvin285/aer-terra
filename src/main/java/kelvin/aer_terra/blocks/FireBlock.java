package kelvin.aer_terra.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MagmaBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class FireBlock extends MagmaBlock {
    public FireBlock(Settings settings) {
        super(settings);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0) {
            double d = (double)pos.getX() + random.nextDouble();
            double e = (double)pos.getY() + 1.05D;
            double f = (double)pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.05D, 0.0D);
        }
    }
}

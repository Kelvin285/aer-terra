package kelvin.aer_terra.world.features;

import kelvin.aer_terra.registry.FeatureRegistry;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TerrawoodSaplingGenerator extends SaplingGenerator {
    public TerrawoodSaplingGenerator() {
    }

    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        if (random.nextInt(10) == 0) {
            return FeatureRegistry.FANCY_TERRAWOOD_TREE;
        } else {
            return FeatureRegistry.TERRAWOOD_TREE;
        }
    }
}

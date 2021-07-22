package kelvin.aer_terra.registry;

import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.OptionalInt;

public class FeatureRegistry {
    public static ConfiguredFeature<TreeFeatureConfig, ?> TERRAWOOD_TREE;
    public static ConfiguredFeature<TreeFeatureConfig, ?> FANCY_TERRAWOOD_TREE;

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return (ConfiguredFeature) Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

    public static void RegisterFeatures() {
        TERRAWOOD_TREE = register("aer_terra:terrawood", Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.TERRAWOOD_LOG.getDefaultState()), new StraightTrunkPlacer(4, 2, 0), new SimpleBlockStateProvider(BlockRegistry.TERRAWOOD_LEAVES.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.TERRAWOOD_SAPLING.getDefaultState()), new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()));
        FANCY_TERRAWOOD_TREE = register("aer_terra:fancy_terrawood", Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.TERRAWOOD_LOG.getDefaultState()), new LargeOakTrunkPlacer(3, 11, 0), new SimpleBlockStateProvider(BlockRegistry.TERRAWOOD_LEAVES.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.TERRAWOOD_SAPLING.getDefaultState()), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().build()));
    }

}

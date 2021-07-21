package kelvin.aer_terra.mixin.common;

import kelvin.aer_terra.main.resources.IslandArea;
import kelvin.aer_terra.main.resources.Resources;
import kelvin.aer_terra.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.particle.CloudParticle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.BlockSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureWeightSampler;
import net.minecraft.world.gen.chunk.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator {

    @Shadow
    private NoiseSampler surfaceDepthNoise;
    @Shadow
    private DoublePerlinNoiseSampler edgeDensityNoise;
    @Shadow
    private DoublePerlinNoiseSampler fluidLevelNoise;
    @Shadow
    private DoublePerlinNoiseSampler fluidTypeNoise;
    @Shadow
    protected BlockState defaultBlock;
    @Shadow
    protected BlockState defaultFluid;
    @Shadow
    protected Supplier<ChunkGeneratorSettings> settings;
    @Shadow
    private long seed;

    public NoiseChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
    }

    @Shadow
    private void buildBedrock(Chunk chunk, Random random) {

    }
    public IslandArea GetIslandDistanceField(double x, double y, double z) {
        double offset_x = this.edgeDensityNoise.sample(z * 0.1F, y * 0.75F, 0) * 25;
        double offset_z = this.edgeDensityNoise.sample(x * 0.1F, y * 0.75F, 0) * 25;

        double variant = Math.abs(this.surfaceDepthNoise.sample(x * 0.01F, z * 0.01F, 3.5F, 75));
        double offset_y = this.surfaceDepthNoise.sample(x * 0.1F, z * 0.1F, 3.5F, 75) * 80 * variant;
        x += offset_x;
        z += offset_z;
        y += offset_y;
        int island_bottom = getSeaLevel() + 30;
        int island_top = island_bottom + 64;
        int island_height = island_top - island_bottom;
        int island_center = island_bottom + island_height / 2;

        double rift_distance = 50;

        double center_radius = 350;
        double cx = x / center_radius;
        double cz = z / center_radius;
        double cy = ((y - island_center) / (double)island_height);

        double center_distance = Resources.getDistanceFromDeltas(cx, cy, cz);

        double inner_distance = center_distance - 1.0D;

        double total_distance = inner_distance;

        double total_radius = 4000;
        double tx = x / total_radius;
        double tz = z / total_radius;
        double outer_distance = Resources.getDistanceFromDeltas(tx, 0, tz) - 1.0D;
        double center_cylinder = Resources.getDistanceFromDeltas(cx, 0, cz) - 1.0D;

        if (center_distance <= 1.0F) {
            if (center_cylinder >= 0.9F) {
                return IslandArea.rift;
            }
            return IslandArea.land;
        }

        if (center_distance >= 1.1F) {
            IslandArea area = IslandArea.air;
            if (outer_distance < 0 && y > island_bottom && y < island_top) {
                area = IslandArea.land;

                if (center_distance <= 1.2F) {
                    area = IslandArea.rift;
                }

                if (Math.abs(x) <= rift_distance + 75 || Math.abs(z) <= rift_distance + 75) {
                    area = IslandArea.rift;

                    if (Math.abs(x) <= rift_distance || Math.abs(z) <= rift_distance) {
                        area = IslandArea.air;
                    }

                }
            }

            return area;
        }

        return IslandArea.air;
    }

    private Random random;
    private int rotation = 0;

@Inject(at = @At("HEAD"), method = "buildSurface", cancellable = true)
public void buildSurface(ChunkRegion region, Chunk chunk, CallbackInfo info) {
    if (random == null) {
        random = new Random(seed);
        rotation = random.nextInt(4);
    }
    if (this.defaultBlock == BlockRegistry.TERRASTONE.getDefaultState()) {
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.x;
        int j = chunkPos.z;
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setTerrainSeed(i, j);
        ChunkPos chunkPos2 = chunk.getPos();
        int k = chunkPos2.getStartX();
        int l = chunkPos2.getStartZ();
        double d = 0.0625D;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        Biome AER_TERRA = this.biomeSource.getBiomes().get(0);
        Biome AER_TERRA_DESERT = this.biomeSource.getBiomes().get(1);

        for(int m = 0; m < 16; ++m) {
            for(int n = 0; n < 16; ++n) {
                int o = k + m;
                int p = l + n;
                int q = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, m, n) + 1;
                double e = this.surfaceDepthNoise.sample((double)o * 0.0625D, (double)p * 0.0625D, 0.0625D, (double)m * 0.0625D) * 15.0D;
                int r = ((ChunkGeneratorSettings)this.settings.get()).getMinSurfaceLevel();

                int quadrant = 0;
                int x = 0;
                int z = p;
                if (x <= 0 && z <= 0) {
                    quadrant = 0;
                }
                else if (x <= 0 && z > 0) {
                    quadrant = 1;
                }
                else if (x > 0 && z > 0) {
                    quadrant = 2;
                } else if (x > 0 && z <= 0) {
                    quadrant = 3;
                }
                quadrant += rotation;
                quadrant %= 4;



                double center_radius = 350;
                double cx = x / center_radius;
                double cz = z / center_radius;
                double center_distance = Resources.getDistanceFromDeltas(cx, 0, cz);

                Biome current = AER_TERRA;

                if (center_distance >= 1.15F) {
                    if (quadrant == 0) {
                        current = AER_TERRA_DESERT;
                    }
                }

                current.buildSurface(chunkRandom, chunk, o, p, q, e, this.defaultBlock, this.defaultFluid, this.getSeaLevel(), r, region.getSeed());
            }
        }

        this.buildBedrock(chunk, chunkRandom);
        info.cancel();
    }
}



public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
    int island_bottom = getSeaLevel() + 30;
    int island_top = island_bottom + 64;
    return island_top;
}

    /**
     *
     * @param structures
     * @param aquiferSampler
     * @param blockInterpolator
     * @param weightSampler
     * @param x
     * @param y
     * @param z
     * @param weight
     * @return
     */


    @Inject(at = @At("HEAD"), method = "getBlockState", cancellable = true)
    public void getBlockState(StructureWeightSampler structures, AquiferSampler aquiferSampler, BlockSource blockInterpolator, WeightSampler weightSampler, int x, int y, int z, double weight, CallbackInfoReturnable<BlockState> info) {

        if (this.defaultBlock == BlockRegistry.TERRASTONE.getDefaultState()) {
            int surfaceLevel = this.settings.get().getMinSurfaceLevel() + getSeaLevel();

            double sea = this.edgeDensityNoise.sample(x * 0.1F, y * 0.75F, z * 0.1F);

            double sea_density = y / (double)(getSeaLevel() * 1.25D);
            double sea_field = sea - sea_density;

            double sea_noise = this.surfaceDepthNoise.sample(x * 0.1F, z * 0.1F, 1.25F, 3.0F);
            int sea_height = (int)Math.abs(sea_noise * getSeaLevel() * 0.75F) + 25;

            IslandArea island_area = GetIslandDistanceField(x, y, z);

            if (sea_field > 0 || y < sea_height || island_area == IslandArea.land) {
                info.setReturnValue(BlockRegistry.TERRASTONE.getDefaultState());
                return;
            }

            if (island_area == IslandArea.rift) {
                info.setReturnValue(BlockRegistry.RIFTSTONE.getDefaultState());
                return;
            }

            if (y < getSeaLevel()) {
                info.setReturnValue(Blocks.WATER.getDefaultState());
                return;
            }
            info.setReturnValue(Blocks.AIR.getDefaultState());
        }
    }
}

package kelvin.aer_terra.mixin.common;

import kelvin.aer_terra.main.resources.IslandArea;
import kelvin.aer_terra.main.resources.Resources;
import kelvin.aer_terra.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.BlockSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
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

    @Override
    public Pool<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        Biome b = getTerraBiomeAt(pos);
        return b.getSpawnSettings().getSpawnEntries(group);
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        if (this.defaultBlock == BlockRegistry.TERRASTONE.getDefaultState()) {
            ChunkPos chunkPos = region.getCenterPos();
            int i = chunkPos.getStartX();
            int j = chunkPos.getStartZ();
            BlockPos blockPos = new BlockPos(i, region.getBottomY(), j);
            Biome biome = getTerraBiomeAt(chunkPos);
            ChunkRandom chunkRandom = new ChunkRandom();
            long l = chunkRandom.setPopulationSeed(region.getSeed(), i, j);

            try {
                biome.generateFeatureStep(accessor, this, region, l, chunkRandom, blockPos);
            } catch (Exception var13) {
                CrashReport crashReport = CrashReport.create(var13, "Biome decoration");
                crashReport.addElement("Generation").add("CenterX", chunkPos.x).add("CenterZ", chunkPos.z).add("Seed", l).add("Biome", biome);
                throw new CrashException(crashReport);
            }
        } else {
            super.generateFeatures(region, accessor);
        }
    }

    private Random random;
    private int rotation = 0;

    @Inject(at = @At("HEAD"), method = "populateEntities", cancellable = true)
    public void populateEntities(ChunkRegion region, CallbackInfo info) {
        if (this.defaultBlock == BlockRegistry.TERRASTONE.getDefaultState()) {
            ChunkPos chunkPos = region.getCenterPos();
            Biome biome = getTerraBiomeAt(chunkPos.getStartPos());
            ChunkRandom chunkRandom = new ChunkRandom();
            chunkRandom.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
            SpawnHelper.populateEntities(region, biome, chunkPos, chunkRandom);

            info.cancel();
        }
    }

    public Biome getTerraBiomeAt(ChunkPos pos) {
        return getTerraBiomeAt(pos.x, pos.z);
    }

    public Biome getTerraBiomeAt(BlockPos pos) {
        return getTerraBiomeAt(pos.getX(), pos.getZ());
    }

    public Biome getTerraBiomeAt(int x, int z) {
        Biome AER_TERRA = this.biomeSource.getBiomes().get(0);
        Biome AER_TERRA_DESERT = this.biomeSource.getBiomes().get(1);
        Biome AER_TERRA_FLAME = this.biomeSource.getBiomes().get(2);
        Biome AER_TERRA_FROST = this.biomeSource.getBiomes().get(3);
        Biome AER_TERRA_RIFT = this.biomeSource.getBiomes().get(4);
        int quadrant = 0;

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

        if (center_distance >= 1.0F) {
            if (quadrant == 0) {
                current = AER_TERRA_DESERT;
            }
            else if (quadrant == 1) {
                current = AER_TERRA_FROST;
            } else if (quadrant == 2) {
                current = AER_TERRA_FLAME;
            } else if (quadrant == 3) {
                current = AER_TERRA_RIFT;
            }
        }

        return current;
    }


    @Inject(at = @At("HEAD"), method = "buildSurface", cancellable = true)
    public void buildSurface(ChunkRegion region, Chunk chunk, CallbackInfo info) {

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
            Biome AER_TERRA_FLAME = this.biomeSource.getBiomes().get(2);
            Biome AER_TERRA_FROST = this.biomeSource.getBiomes().get(3);
            Biome AER_TERRA_RIFT = this.biomeSource.getBiomes().get(4);

            BlockPos.Mutable pos = new BlockPos.Mutable();
            for(int m = 0; m < 16; ++m) {
                for(int n = 0; n < 16; ++n) {
                    int o = k + m;
                    int p = l + n;
                    int q = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, m, n) + 1;
                    double e = this.surfaceDepthNoise.sample((double)o * 0.0625D, (double)p * 0.0625D, 0.0625D, (double)m * 0.0625D) * 15.0D;
                    int r = ((ChunkGeneratorSettings)this.settings.get()).getMinSurfaceLevel();

                    int x = o;
                    int z = p;

                    int quadrant = 0;

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

                    if (center_distance >= 1.0F) {
                        if (quadrant == 0) {
                            current = AER_TERRA_DESERT;
                        }
                        else if (quadrant == 1) {
                            current = AER_TERRA_FROST;
                        } else if (quadrant == 2) {
                            current = AER_TERRA_FLAME;
                        } else if (quadrant == 3) {
                            current = AER_TERRA_RIFT;
                        }
                    }

                    current.buildSurface(chunkRandom, chunk, o, p, q, e, this.defaultBlock, this.defaultFluid, this.getSeaLevel(), r, region.getSeed());

                    if (center_distance > 1.0F)
                    {
                        for (int y = getSeaLevel() + 1; y < 255; y++) {
                            pos.set(x, y, z);
                            if (chunk.getBlockState(pos).getBlock() == BlockRegistry.TERRASTONE) {
                                if (quadrant == 1) {
                                    chunk.setBlockState(pos, BlockRegistry.TERRAFROST_STONE.getDefaultState(), false);
                                } else if (quadrant == 2) {
                                    chunk.setBlockState(pos, BlockRegistry.FLAMESTONE.getDefaultState(), false);
                                } else if (quadrant == 3) {
                                    chunk.setBlockState(pos, BlockRegistry.ELECTRIFIED_TERRASTONE.getDefaultState(), false);
                                }
                            }
                            if (chunk.getBlockState(pos).getBlock() == Blocks.WATER) {
                                if (quadrant == 1) {
                                    if (y + 1 < 255) {
                                        pos.set(x, y + 1, z);
                                        boolean air = chunk.getBlockState(pos).getBlock() == Blocks.AIR;
                                        pos.set(x, y, z);
                                        if (air) {
                                            chunk.setBlockState(pos, Blocks.ICE.getDefaultState(), false);
                                        }
                                    }
                                } else if (quadrant == 2) {
                                    chunk.setBlockState(pos, Blocks.LAVA.getDefaultState(), false);
                                }
                            }
                        }
                    }
                }
            }

            this.buildBedrock(chunk, chunkRandom);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "getSeaLevel", cancellable = true)
    public void getSeaLevel(CallbackInfoReturnable<Integer> info) {
        if (this.defaultBlock == BlockRegistry.TERRASTONE.getDefaultState()) {
            info.setReturnValue(32);
        }
    }


    public int getIslandBottom() {
        return Math.max(80, getSeaLevel() + 30);
    }

    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        int island_bottom = getIslandBottom();
        int island_top = island_bottom + 64;
        return island_top;
    }


    public IslandArea GetIslandDistanceField(double x, double y, double z, int quadrant) {

        double offset_x = this.edgeDensityNoise.sample(z * 0.1F, y * 0.75F, 0) * 25;
        double offset_z = this.edgeDensityNoise.sample(x * 0.1F, y * 0.75F, 0) * 25;

        double turbulence = Math.abs(this.surfaceDepthNoise.sample(x * 0.01F, z * 0.01F, 3.5F, 75));

        double X = x;
        double Z = z;
        double Y = y;
        double height = Math.abs(this.surfaceDepthNoise.sample(z * 0.01F, x * 0.01F, 1.0F, 1.0F));

        x += offset_x;
        z += offset_z;



        boolean in_quadrant = false;
        {

            int island_bottom = getIslandBottom();
            int island_top = island_bottom + 64;
            int island_height = island_top - island_bottom;
            int island_center = island_bottom + island_height / 2;
            double center_radius = 350;
            double cx = x / center_radius;
            double cz = z / center_radius;
            double cy = ((y - island_center) / (double)island_height);

            double center_distance = Resources.getDistanceFromDeltas(cx, cy, cz);

            if (center_distance >= 1.0F) {
                in_quadrant = true;
            }
        }

        double overheight = Math.abs(this.surfaceDepthNoise.sample(z * 0.01F, x * 0.01F, 1.0F, 1.0F));

        //0 = Dunes, 1 = Frost, 2 = Flame, 3 = Rift
        if (in_quadrant) {
            int island_bottom = getIslandBottom();
            int island_top = island_bottom + 64;
            int island_height = island_top - island_bottom;
            double center_radius = 350;
            double cx = x / center_radius;
            double cz = z / center_radius;

            double center_distance = Resources.getDistanceFromDeltas(cx, 0, cz) - 0.25D;
            if (center_distance < 0.0D) center_distance = 0.0D;
            if (center_distance > 1.0D) center_distance = 1.0D;

            if (center_distance >= 1.0D) {
                if (quadrant == 0) {
                    overheight = 0;
                    height *= 1.5F * center_distance;
                } else if (quadrant == 1) {
                    turbulence *= 1.5F * center_distance;
                    overheight *= 1.5F * center_distance;
                    height *= 2 * center_distance;
                    if (height < 0) height = Math.abs(height) * 0.5F * center_distance;
                    if (overheight < 0) overheight = Math.abs(overheight) * 0.5F * center_distance;
                    if (turbulence < 0) turbulence = 0;
                    height *= -1;
                    overheight *= -1;
                } else if (quadrant == 2) {
                    overheight *= 4 * center_distance;
                    height *= 0.5F * center_distance;
                    overheight = Math.abs(overheight);
                    overheight *= -1;
                } else if (quadrant == 3) {
                    overheight *= 2.0F * center_distance;
                }
            }
        }

        double offset_y = this.surfaceDepthNoise.sample(X * 0.1F, Z * 0.1F, 3.5F, 75) * 80 * turbulence + ((overheight + height) / 2.0F) * 80;

        y += offset_y;


        int island_bottom = getIslandBottom();
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

        IslandArea area = IslandArea.air;

        if (center_distance <= 1.0F) {
            area = IslandArea.land;
            if (center_cylinder >= 0.9F) {
                area = IslandArea.rift;
            }
        }

        if (center_distance > 1.0F) {
            y -= offset_y;

            if (y < island_top && y > island_center) {
                //area = IslandArea.water;
            }
            island_bottom += turbulence * 15;
            island_top -= offset_y;
            if (center_distance >= 1.1F) {
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
            }
            if (in_quadrant) {
                if (quadrant == 3) {
                    //the rift
                    if (area == IslandArea.rift) {
                        area = IslandArea.land;
                    }
                    double island = this.edgeDensityNoise.sample(x * 0.25F, y * 0.25F, z * 0.25F);
                    double island2 = this.edgeDensityNoise.sample(x * 0.1F, 0, z * 0.1F) * turbulence;

                    if (island < 0 || island2 < 0) {
                        area = IslandArea.air;
                    }
                }
            }
        }

        return area;
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
        if (random == null) {
            random = new Random(seed);
            rotation = random.nextInt(4);
        }
        int quadrant = 0;

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

        if (this.defaultBlock == BlockRegistry.TERRASTONE.getDefaultState()) {
            int surfaceLevel = this.settings.get().getMinSurfaceLevel() + getSeaLevel();

            double sea = this.edgeDensityNoise.sample(x * 0.1F, y * 0.75F, z * 0.1F);

            double sea_density = y / (double)(getSeaLevel() * 1.25D);
            double sea_field = sea - sea_density;

            double sea_noise = this.surfaceDepthNoise.sample(x * 0.1F, z * 0.1F, 1.25F, 3.0F);
            int sea_height = (int)Math.abs(sea_noise * getSeaLevel() * 0.75F) + 25;

            IslandArea island_area = GetIslandDistanceField(x, y, z, quadrant);

            if (sea_field > 0 || y < sea_height || island_area == IslandArea.land) {
                info.setReturnValue(BlockRegistry.TERRASTONE.getDefaultState());
                return;
            }

            if (island_area == IslandArea.rift) {
                info.setReturnValue(BlockRegistry.ELECTRIFIED_TERRASTONE.getDefaultState());
                return;
            }

            if (y < getSeaLevel() || island_area == IslandArea.water) {
                info.setReturnValue(Blocks.WATER.getDefaultState());
                return;
            }
            info.setReturnValue(Blocks.AIR.getDefaultState());
        }
    }
}

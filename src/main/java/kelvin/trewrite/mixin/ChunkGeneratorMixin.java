package kelvin.trewrite.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fastnoise.FastNoise;
import kelvin.trewrite.main.resources.DistanceField;
import kelvin.trewrite.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CaveVinesBodyBlock;
import net.minecraft.block.CaveVinesHeadBlock;
import net.minecraft.block.CoralBlock;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
	
	private FastNoise noise;
	private Random random;
	private boolean corruption = true;
	private boolean right_jungle = true;
	
	@Inject(at = @At("HEAD"), method = "getWorldHeight", cancellable = true)
	public void getWorldHeight(CallbackInfoReturnable<Integer> info) {
		info.setReturnValue(512);
	}
	
	private float GetTerrainNoise(int x, int y, int z) {
		return noise.GetSimplexFractal(x * 1.25f, y * 2.0f, z * 1.25f) * 5;
	}
	
	@Inject(at = @At("HEAD"), method = "generateFeatures", cancellable = true)
	public void generateFeatures(ChunkRegion region, StructureAccessor accessor, CallbackInfo info) {
		if (noise == null) {
			
			noise = new FastNoise((int)region.toServerWorld().getSeed());
			random = new Random(region.toServerWorld().getSeed());
			corruption = random.nextInt(10) >= 4;
			right_jungle = random.nextBoolean();
		}
		
		
		ChunkPos chunkPos = region.getCenterPos();
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockPos.Mutable placing_pos = new BlockPos.Mutable();

		int base_height = 75;
		float threshold = -0.8f;
		
		int stone_height = 20;
		int underworld_height = -190;
		int underworld_base = -256 + 25;
		
		int purity_radius = 350;
		
		int world_radius = 2400;
		
		int biome_width = (int)(900 * 0.3f);
		int biome_length = (int)(1200 * 0.3);
		
		int snow_position = right_jungle ? 1200 : -1200;
		
		BlockState stone = corruption ? BlockRegistry.EBON_STONE.getDefaultState() : BlockRegistry.CRIMSON_STONE.getDefaultState();
		BlockState grass = corruption ? BlockRegistry.EBON_GRASS.getDefaultState() : BlockRegistry.CRIMSON_GRASS.getDefaultState();
		BlockState sand = corruption ? BlockRegistry.EBON_SAND.getDefaultState() : BlockRegistry.CRIMSON_SAND.getDefaultState();
		BlockState sandstone = corruption ? BlockRegistry.EBON_SANDSTONE.getDefaultState() : BlockRegistry.CRIMSON_SANDSTONE.getDefaultState();

		for (int x = chunkPos.getStartX(); x <= chunkPos.getEndX(); x++) {
			for (int z = chunkPos.getStartZ(); z <= chunkPos.getEndZ(); z++) {
				float flatness = noise.GetSimplex(x, z);
				int height_offset = 0;
				
				double world_distance = Math.sqrt(x * x + z * z);

				
				boolean beach = world_distance >= 2400;
				boolean jungle = world_distance > 1900 && (right_jungle ? (x > 0) : (x < 0)) && !beach;
				
				boolean snow = ((x + snow_position) * (x + snow_position)) / (float)(biome_width * biome_width) + (z * z) / (float)(biome_length * biome_length) <= 1;
				boolean desert = ((x - snow_position) * (x - snow_position)) / (float)(biome_width * biome_width) + (z * z) / (float)(biome_length * biome_length) <= 1;
				
				if (world_distance >= 2400) {
					height_offset = (int)(60.0f * (world_distance - 2400) / 300.0f);
					if (height_offset < 0) height_offset = 0;
					if (height_offset > 60) height_offset = 60;
				}
				
				
				if (flatness < 0.05f) flatness = 0.05f;
				
				float sine = (float)Math.sin(Math.PI * (world_distance - 2200) / 2400);
				
				flatness = MathHelper.lerp(sine > 0 ? sine : 0, flatness, 1);
				
				for (int y = region.getBottomY(); y <= region.getTopY(); y++) {
					placing_pos.set(x, y, z);
					BlockState state = Blocks.AIR.getDefaultState();
					
					float stone_density = (y - stone_height + height_offset) / 15.0f;
					
					float density = (y - base_height + height_offset) / 15.0f;
					float n = GetTerrainNoise(x, y, z);
					float combined = n * flatness + density;
					
					float cave_density = y / 256.0f;
					if (cave_density > 0) cave_density = 0;
					cave_density = -cave_density;
					cave_density += 0.05f;
					
					float underworld_density = (y - underworld_height) / 5.0f;
					
					float underworld_base_density = (y - underworld_base) / 15.0f;
					
					float combined_stone = n + stone_density;
					float combined_cave = (n * n) * cave_density;
					float jungle_cave = jungle ? n : 0;

					float combined_underworld = n + underworld_density;
					float combined_underworld_base = n + underworld_base_density;
					
					boolean pure = Math.sqrt(x * x + y * y + z * z) <= purity_radius;
					if (y < 0) {
						pure = n <= 0;
					}

					
					if (y < base_height - 30) {
						state = Blocks.WATER.getDefaultState();
					}
					
					if (combined < threshold) {
						if (jungle) {
							state = BlockRegistry.MUD.getDefaultState();
						} else {
							if (snow) {
								state = Blocks.SNOW_BLOCK.getDefaultState();
							} else {
								if (beach || desert) {
									state = pure ? Blocks.SAND.getDefaultState() : sand;
								} else {
									state = Blocks.DIRT.getDefaultState();
								}
							}
						}
					}
					
					if (combined_stone < threshold) {
						if (jungle) {
							state = BlockRegistry.MUD.getDefaultState();
						} else {
							if (snow && !pure && y > underworld_height + 60) {
								state = Blocks.PACKED_ICE.getDefaultState();
							} else {
								if (desert && y > underworld_height + 50) {
									state = sandstone;
								} else {
									state = pure ? Blocks.STONE.getDefaultState() : stone;
								}
							}
						}
						
					}
					
					
					if (combined_cave >= 0.8f || jungle_cave <= -0.8f) {
						if (combined >= threshold) {
							state = Blocks.AIR.getDefaultState();
						} else {
							state = Blocks.CAVE_AIR.getDefaultState();
							if (y < underworld_height + 5 || y >= -140 + n * 15 && y <= -130) {
								state = Blocks.LAVA.getDefaultState();
							}
						}
					}
					
					if (combined_underworld < threshold) {
						state = Blocks.CAVE_AIR.getDefaultState();
					}
					
					if (y < underworld_base) {
						state = Blocks.LAVA.getDefaultState();
					}
					
					if (combined_underworld_base < threshold) {
						state = Blocks.DEEPSLATE.getDefaultState();
					}
					
					if (y <= -253) {
						state = Blocks.BEDROCK.getDefaultState();
					}
					region.setBlockState(placing_pos, state, 0);
				}
			}
		}
		
		for (int x = chunkPos.getStartX(); x <= chunkPos.getEndX(); x++) {
			for (int z = chunkPos.getStartZ(); z <= chunkPos.getEndZ(); z++) {
				
				double world_distance = Math.sqrt(x * x + z * z);

				
				boolean beach = world_distance >= 2400;
				
				for (int y = region.getBottomY(); y <= region.getTopY(); y++) {
					if (y >= 252) continue;
					boolean pure = Math.sqrt(x * x + y * y + z * z) <= purity_radius;

					int cave_biome = (int)(noise.GetCellular(x * 0.5f, y, z * 0.5f) * 10);
					
					placing_pos.set(x, y, z);
					if (region.isChunkLoaded(placing_pos)) {
						
						if (region.getBlockState(placing_pos) == Blocks.LAVA.getDefaultState()) {
							boolean place_stone = false;
							placing_pos.set(x, y - 1, z);
							if (region.isChunkLoaded(placing_pos)) {
								if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
									place_stone = true;
								}
							}
							placing_pos.set(x - 1, y, z);
							if (region.isChunkLoaded(placing_pos)) {
								if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
									place_stone = true;
								}
							}
							placing_pos.set(x + 1, y, z);
							if (region.isChunkLoaded(placing_pos)) {
								if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
									place_stone = true;
								}
							}
							placing_pos.set(x, y, z - 1);
							if (region.isChunkLoaded(placing_pos)) {
								if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
									place_stone = true;
								}
							}
							placing_pos.set(x, y, z + 1);
							if (region.isChunkLoaded(placing_pos)) {
								if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
									place_stone = true;
								}
							}
							if (place_stone) {
								placing_pos.set(x, y, z);
								region.setBlockState(placing_pos, pure ? Blocks.STONE.getDefaultState() : stone, 0);
							}
						}
						
						placing_pos.set(x, y + 1, z);
						if (region.isChunkLoaded(placing_pos)) {
							
							if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState()) {
								placing_pos.set(x, y, z);
								if (region.getBlockState(placing_pos) == Blocks.DIRT.getDefaultState())
								region.setBlockState(placing_pos, pure ? Blocks.GRASS_BLOCK.getDefaultState() : grass, 0);
								
								if (region.getBlockState(placing_pos) == BlockRegistry.MUD.getDefaultState())
									region.setBlockState(placing_pos, BlockRegistry.JUNGLE_GRASS.getDefaultState(), 0);
							}
							if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
								placing_pos.set(x, y, z);
								if (region.getBlockState(placing_pos) == BlockRegistry.MUD.getDefaultState())
									region.setBlockState(placing_pos, BlockRegistry.JUNGLE_GRASS.getDefaultState(), 0);
							}
							if (region.getBlockState(placing_pos) == (pure ? Blocks.SAND.getDefaultState() : sand)) {
								placing_pos.set(x, y, z);
								if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState())
								region.setBlockState(placing_pos, pure ? Blocks.SANDSTONE.getDefaultState() : sandstone, 0);
							}
							if (random.nextInt(100) == 0 && cave_biome != 1 && cave_biome != 2 && cave_biome != 3) {
								if (region.getBlockState(placing_pos) == Blocks.STONE.getDefaultState() ||
										region.getBlockState(placing_pos) == BlockRegistry.EBON_STONE.getDefaultState() ||
												region.getBlockState(placing_pos) == BlockRegistry.CRIMSON_STONE.getDefaultState() ||
												region.getBlockState(placing_pos) == Blocks.DIRT.getDefaultState() ||
														region.getBlockState(placing_pos) == BlockRegistry.MUD.getDefaultState()) {
									if (random.nextInt(5) >= 2) {
										int height = random.nextInt(4) + 1;
										if (pure) {
											for (int i = y; i > y - height - 1; i--) {
												placing_pos.set(x, i, z);
												if (region.isChunkLoaded(placing_pos)) {
													if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
															region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
													region.setBlockState(placing_pos, Blocks.CAVE_VINES_PLANT.getDefaultState(), 0);
												}
											}
											placing_pos.set(x, y - height, z);
											if (region.isChunkLoaded(placing_pos)) {
												if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
														region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
												region.setBlockState(placing_pos, Blocks.CAVE_VINES.getDefaultState(), 0);
											}
										} else {
											if (corruption) {
												for (int i = y; i > y - height - 1; i--) {
													placing_pos.set(x, i, z);
													if (region.isChunkLoaded(placing_pos)) {
														if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
																region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
														region.setBlockState(placing_pos, Blocks.WEEPING_VINES_PLANT.getDefaultState(), 0);
													}
												}
												placing_pos.set(x, y - height, z);
												if (region.isChunkLoaded(placing_pos)) {
													if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
															region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
													region.setBlockState(placing_pos, Blocks.WEEPING_VINES.getDefaultState(), 0);
												}
											} else {
												for (int i = y; i > y - height - 1; i--) {
													placing_pos.set(x, i, z);
													if (region.isChunkLoaded(placing_pos)) {
														if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
																region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
														region.setBlockState(placing_pos, Blocks.CAVE_VINES_PLANT.getDefaultState().with(CaveVinesBodyBlock.BERRIES, true), 0);
													}
												}
												placing_pos.set(x, y - height, z);
												if (region.isChunkLoaded(placing_pos)) {
													if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
															region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
													region.setBlockState(placing_pos, Blocks.CAVE_VINES.getDefaultState().with(CaveVinesHeadBlock.BERRIES, true), 0);
												}
											}
										}
									}
									else {
										placing_pos.set(x, y, z);
										if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
												region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
										region.setBlockState(placing_pos, Blocks.HANGING_ROOTS.getDefaultState(), 0);
									}
								}
								
							} else if (random.nextInt(350) == 0) {
								
								if (y < stone_height) /*TODO: Dripstone*/
								{
									placing_pos.set(x, y, z);
									if (region.getBlockState(placing_pos) == Blocks.STONE.getDefaultState())
									{
										placing_pos.set(x, y + 1, z);
										if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
										{
											region.setBlockState(placing_pos, Blocks.POINTED_DRIPSTONE.getDefaultState(), 0);
										}
									}
								}
							}
							
						}
						
						if (random.nextInt(1000 * 2) == 0)
						this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.DIRT, Blocks.COPPER_ORE, 5);
						
						if (random.nextInt(1000) == 0)
						{
							this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.COPPER_ORE, 10);

						}
						
						if (random.nextInt(1700) == 0)
						{
							this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.IRON_ORE, 15);
						}
						
						if (random.nextInt(700) == 0)
							this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.COAL_ORE, 10);
						
						if (random.nextInt(1500) == 0)
							this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.DIRT, Blocks.COAL_ORE, 10);
						
						
						if (y <= 0) {
							if (random.nextInt(2500) == 0)
								this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.GOLD_ORE, 10);
							
							if (random.nextInt(5000) == 0)
								this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.DIAMOND_ORE, 2);
						}
						
						if (y <= -100) {
							if (random.nextInt(1500) == 0)
								this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.REDSTONE_ORE, 10);
							
							if (random.nextInt(1500) == 0)
								this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.LAPIS_ORE, 10);
						}
						

						if (random.nextInt(300) == 0)
						{
							this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.DIRT, Blocks.STONE, 30);
							this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.SAND, Blocks.STONE, 30);
							this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.SNOW_BLOCK, Blocks.PACKED_ICE, 30);
							this.GenerateOre(region, x, y, z, pos, placing_pos, BlockRegistry.MUD, Blocks.STONE, 30);
							this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.DIRT, 30);
						}
						
						if (y <= -130) {
							if (random.nextInt(200) == 0)
							{
								this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.STONE, Blocks.MAGMA_BLOCK, 30);
								this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.DEEPSLATE, Blocks.MAGMA_BLOCK, 30);
							}
							if (random.nextInt(5000) == 0)
							{
								this.GenerateOre(region, x, y, z, pos, placing_pos, Blocks.DEEPSLATE, Blocks.ANCIENT_DEBRIS, 2);
							}
						}
						
						
						
						
						GeneratePurityGrass(region, x, y, z, pos, placing_pos);
						GeneratePurityTrees(region, x, y, z, pos, placing_pos);
						
						GenerateJungleTrees(region, x, y, z, pos, placing_pos);

						GenerateCorruptionTrees(region, x, y, z, pos, placing_pos);
						GenerateCorruptMushrooms(region, x, y, z, pos, placing_pos);

						GenerateCrimsonTrees(region, x, y, z, pos, placing_pos);
						GenerateCrimsonMushrooms(region, x, y, z, pos, placing_pos);
						
						if (!beach)
						GenerateDesertPlants(region, x, y, z, pos, placing_pos);
						
						GenerateSpruceTrees(region, x, y, z, pos, placing_pos);
						GenerateTaigaPlants(region, x, y, z, pos, placing_pos);
						
						placing_pos.set(x, y, z);
						if (region.getBlockState(placing_pos) == Blocks.STONE.getDefaultState()) 
						{
							placing_pos.set(x, y + 1, z);
							if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
								placing_pos.set(x, y, z);
								if (random.nextInt(300) == 0) {
									this.GenerateGemTree(region, x, y, z, pos, placing_pos, BlockRegistry.BLUE_CRYSTAL);
								}
							}
						}
						
						if (y <= 0)
						if (cave_biome == 0) { /*FUNGUS AMONG US*/
							placing_pos.set(x, y, z);
							if (region.getBlockState(placing_pos) == Blocks.STONE.getDefaultState() ||
									region.getBlockState(placing_pos) == BlockRegistry.EBON_STONE.getDefaultState() ||
											region.getBlockState(placing_pos) == BlockRegistry.CRIMSON_STONE.getDefaultState()) {
								region.setBlockState(placing_pos, BlockRegistry.MUD.getDefaultState(), 0);
								placing_pos.set(x, y + 1, z);
								if (region.isChunkLoaded(placing_pos))
								if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
										region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
									
									
									if (random.nextInt(100) == 0) {
										if (random.nextBoolean()) {
											FungusBlock fungus = (FungusBlock)Blocks.WARPED_FUNGUS;
											//fungus.grow(region.toServerWorld(), random, placing_pos, Blocks.WARPED_FUNGUS.getDefaultState());
											
											int height = random.nextInt(5) + 7;
											for (int j = y; j < y + height; j++) {
												placing_pos.set(x, j, z);
												if (region.isChunkLoaded(placing_pos))
													if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
													region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
														region.setBlockState(placing_pos, Blocks.WARPED_HYPHAE.getDefaultState(), 0);
											}
											for (int i = - 2; i <= + 2; i++) {
												for (int j = - 2; j <= + 2; j++) {
													placing_pos.set(x + i, y + height, z + j);
													
													if (Math.abs(i) == 2 && Math.abs(j) == 2) {
														if (random.nextInt(4) == 0) continue;
													}
													
													if (Math.abs(i) == 1 && j == 0 || Math.abs(j) == 1 && i == 0 || i == 0 && j == 0) {
														for (int k = 0; k < 2; k++) {
															if (i != 0 || j != 0) {
																if (k == 1) {
																	if (random.nextInt(4) == 0) continue;
																}
															}
															placing_pos.set(x + i, y + height + 1 + k, z + j);
															if (region.isChunkLoaded(placing_pos)) {
																region.setBlockState(placing_pos, Blocks.WARPED_WART_BLOCK.getDefaultState(), 0);
																if (random.nextInt(10) == 0) {
																	region.setBlockState(placing_pos, Blocks.SHROOMLIGHT.getDefaultState(), 0);
																}
															}
														}
													}
													
													if (region.isChunkLoaded(placing_pos))
														if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
														region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
														{
															region.setBlockState(placing_pos, Blocks.WARPED_WART_BLOCK.getDefaultState(), 0);
															if (random.nextInt(10) == 0) {
																region.setBlockState(placing_pos, Blocks.SHROOMLIGHT.getDefaultState(), 0);
															} else {
																if (i == -2 || i == 2 || j == -2 || j == 2) {
																	int depth = random.nextInt(3);
																	for (int k = y + height; k > y + height - depth; k--) {
																		placing_pos.set(x + i, k, z + j);
																		if (region.isChunkLoaded(placing_pos))
																			region.setBlockState(placing_pos, Blocks.WARPED_WART_BLOCK.getDefaultState(), 0);
																	}
																} else {
																	if (random.nextInt(10) == 0)  {
																		placing_pos.set(x + i, y + height - 1, z + j);
																		if (region.isChunkLoaded(placing_pos))
																			region.setBlockState(placing_pos, Blocks.SHROOMLIGHT.getDefaultState(), 0);
																	}
																}
															}
														}
												}
											}
										} else {
											region.setBlockState(placing_pos, Blocks.WARPED_FUNGUS.getDefaultState(), 0);
										}
									} else if (random.nextInt(100) == 0) {
										region.setBlockState(placing_pos, Blocks.WARPED_ROOTS.getDefaultState(), 0);
									} else if (random.nextInt(100) == 0) {
										int height = random.nextInt(15) + 3;
										for (int j = y; j < y + height; j++) {
											placing_pos.set(x, j, z);
											if (region.isChunkLoaded(placing_pos))
												if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
														region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
													region.setBlockState(placing_pos, Blocks.TWISTING_VINES.getDefaultState(), 0);
										}
										
									}
									
									placing_pos.set(x, y, z);
									region.setBlockState(placing_pos, BlockRegistry.MUSHROOM_GRASS.getDefaultState(), 0);
								}
							}
						} else {
							if (cave_biome == 1) {
								if (region.getBlockState(placing_pos) == BlockRegistry.EBON_STONE.getDefaultState() ||
										region.getBlockState(placing_pos) == BlockRegistry.CRIMSON_STONE.getDefaultState() ||
										region.getBlockState(placing_pos) == Blocks.STONE.getDefaultState())
									region.setBlockState(placing_pos, BlockRegistry.MARBLE.getDefaultState(), 0);
							} else {
								if (cave_biome == 2) {
									if (region.getBlockState(placing_pos) == BlockRegistry.EBON_STONE.getDefaultState() ||
											region.getBlockState(placing_pos) == BlockRegistry.CRIMSON_STONE.getDefaultState() ||
											region.getBlockState(placing_pos) == Blocks.STONE.getDefaultState())
										region.setBlockState(placing_pos, BlockRegistry.GRANITE.getDefaultState(), 0);
								} else {
									if (cave_biome == 3) {
										placing_pos.set(x, y, z);

										if (region.getBlockState(placing_pos) == BlockRegistry.EBON_STONE.getDefaultState() ||
												region.getBlockState(placing_pos) == BlockRegistry.CRIMSON_STONE.getDefaultState() ||
												region.getBlockState(placing_pos) == Blocks.STONE.getDefaultState())
										{
											region.setBlockState(placing_pos, BlockRegistry.BLUE_CRYSTAL.getDefaultState(), 0);
											if (random.nextInt(50) == 0) {
												placing_pos.set(x, y + 1, z);
												if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
														region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
												{
													int r = random.nextInt(4);
													switch (r) {
													case 0:
														region.setBlockState(placing_pos, Blocks.AMETHYST_CLUSTER.getDefaultState(), 0);
														break;
													case 1:
														region.setBlockState(placing_pos, Blocks.SMALL_AMETHYST_BUD.getDefaultState(), 0);
														break;
													case 2:
														region.setBlockState(placing_pos, Blocks.MEDIUM_AMETHYST_BUD.getDefaultState(), 0);
														break;
													case 3:
														region.setBlockState(placing_pos, Blocks.LARGE_AMETHYST_BUD.getDefaultState(), 0);
														break;
													}
												}
											}
										}
										
									} else {
										if (cave_biome == 4) {
											if (region.getBlockState(placing_pos) == BlockRegistry.EBON_STONE.getDefaultState() ||
													region.getBlockState(placing_pos) == BlockRegistry.CRIMSON_STONE.getDefaultState() ||
													region.getBlockState(placing_pos) == Blocks.STONE.getDefaultState() ||
													region.getBlockState(placing_pos) == Blocks.COBWEB.getDefaultState())
											{
												placing_pos.set(x, y + 1, z);
												if (region.getBlockState(placing_pos) == Blocks.AIR.getDefaultState() ||
														region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState())
												{
													if (random.nextInt(10) <= 4) {
														region.setBlockState(placing_pos, Blocks.COBWEB.getDefaultState(), 0);
													}
												}
											}
											
										}
									}
								}
							}
						}
						
						if (y <= -100) 
						{
							GenerateLowCaveFeatures(region, x, y, z, pos, placing_pos);
						}
						
					}
					
				}
				
			}
		}
//		
//		
//		
//		//generate structures
//
		GenerateUnderworldTowers(region, chunkPos, pos, placing_pos);
		
		info.cancel();
	}
	
	private boolean GenerateUnderworldTowers(ChunkRegion region, ChunkPos chunkPos, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		if (region.getRandom().nextInt(10) > 1) {
			return false;
		}
		int x = chunkPos.getStartX() + random.nextInt(chunkPos.getEndX() - chunkPos.getStartX() + 1);
		int y = random.nextInt(16) - 245;
		int z = chunkPos.getStartZ() + random.nextInt(chunkPos.getEndZ() - chunkPos.getStartZ() + 1);
		
		pos.set(x, y, z);
		if (region.getBlockState(pos).isSolidBlock(region, pos)) {
			int floor_size_x = region.getRandom().nextInt(2) + 1;
			int floor_size_z = region.getRandom().nextInt(2) + 1;
			
			int room_size = 10;
			Block room_material = random.nextInt(10) == 0 ? Blocks.MAGMA_BLOCK : Blocks.RED_NETHER_BRICKS; // temporarily red nether bricks
			
			
			
			//build bottom floor rooms
			for (int floor_x = 0; floor_x < floor_size_x; floor_x++) {
				for(int floor_z = 0; floor_z < floor_size_z; floor_z++) {
					int tower_height = 1 + region.getRandom().nextInt(5);
					//build main tower
					if (random.nextInt(4) <= 1 || (floor_x == 0 && floor_z == 0))
					for (int h = 0; h < tower_height; h++) {
						GenerateUnderworldTowerRoom(region, x + floor_x * room_size - floor_x, y + h * room_size - h, z + floor_z * room_size - floor_z, placing_pos, room_size, room_material, new boolean[] {random.nextBoolean() & (h == tower_height - 1), h != 0, random.nextInt(10) == 0, random.nextInt(10) == 0, random.nextInt(10) == 0, random.nextInt(10) == 0});
					}
				}
			}
			
			return true; // successfully generated
		}
		return false;
	}
	
	/*
	 * Doorways: up, down, left, right, front, back
	 */
	private void GenerateUnderworldTowerRoom(ChunkRegion region, int x, int y, int z, BlockPos.Mutable placing_pos, int room_size, Block room_material, boolean[] doorways) {
		for (int X = 0; X < room_size; X++) {
			for (int Y = 0; Y < room_size; Y++) {
				for (int Z = 0; Z < room_size; Z++) {
					placing_pos.set(x + X, y + Y, z + Z);
					if (region.isChunkLoaded(placing_pos)) {
						if (X == 0 || Y == 0 || Z == 0 || X == room_size - 1 || Y == room_size - 1 || Z == room_size - 1) {
							region.setBlockState(placing_pos, room_material.getDefaultState(), 0);
						} else {
							region.setBlockState(placing_pos, Blocks.CAVE_AIR.getDefaultState(), 0);
						}
					}
					
				}
			}
		}
		int hole_width = 4;
		int door_width = 2;
		int door_height = 3;
		
		if (doorways[0] || doorways[1]) {
			int hx = region.getRandom().nextInt(room_size - hole_width - 2) + 1;
			int hz = region.getRandom().nextInt(room_size - hole_width - 2) + 1;
			
			for (int hole_x = 0; hole_x < hole_width; hole_x++) {
				for (int hole_z = 0; hole_z < hole_width; hole_z++) {
					
					if (doorways[0]) {
						placing_pos.set(
								x + hole_x + hx,
								y + room_size - 1,
								z + hole_z + hz);
						region.setBlockState(placing_pos, Blocks.CAVE_AIR.getDefaultState(), 0);
					}
					if (doorways[1]) {
						placing_pos.set(
								x + hole_x + hx,
								y,
								z + hole_z + hz);
						region.setBlockState(placing_pos, Blocks.CAVE_AIR.getDefaultState(), 0);
					}
				}
			}
		}
		
		if (doorways[2] || doorways[3] || doorways[4] || doorways[5]) {
			for (int door_x = 0; door_x < door_width; door_x++) {
				for (int door_y = 0; door_y < door_height; door_y++) {
					if (doorways[2]) {
						placing_pos.set(
								x,
								y + door_y + 1,
								z + 4 + door_x);
						region.setBlockState(placing_pos, Blocks.CAVE_AIR.getDefaultState(), 0);
					}
					if (doorways[3]) {
						placing_pos.set(
								x + room_size - 1,
								y + door_y + 1,
								z + 4 + door_x);
						region.setBlockState(placing_pos, Blocks.CAVE_AIR.getDefaultState(), 0);
					}
					if (doorways[4]) {
						placing_pos.set(
								x + 4 + door_x,
								y + door_y + 1,
								z);
						region.setBlockState(placing_pos, Blocks.CAVE_AIR.getDefaultState(), 0);
					}
					if (doorways[5]) {
						placing_pos.set(
								x + 4 + door_x,
								y + door_y + 1,
								z + room_size - 1);
						region.setBlockState(placing_pos, Blocks.CAVE_AIR.getDefaultState(), 0);
					}
				}
			}
		}
	}
	
	private void GenerateCorruptionTrees(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == BlockRegistry.EBON_GRASS) {
			if (region.getRandom().nextInt(100) == 0) {
				int height = region.getRandom().nextInt(8) + 8;
				BlockPos leaf_pos = pos.up(height);
				BlockPos leaf_pos_cone = pos.up(height - 3);
				BlockPos.Mutable testing_pos = new BlockPos.Mutable();
				
				boolean bare = region.getRandom().nextInt(50) == 0;
				
				Quaternion rotation = new Quaternion((region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), (region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), (region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), true);

				Vec3f translation = new Vec3f();
				
				for (int X = -10; X < 10; X++) {
					for (int Y = 0; Y < 24; Y++) {
						for (int Z = -10; Z < 10; Z++) {
							placing_pos.set(x + X, y + Y, z + Z);
							translation.set(X, Y, Z);
							translation.rotate(rotation);
							
							testing_pos.set(x + (int)Math.floor(translation.getX()), y + (int)Math.floor(translation.getY()), z + (int)Math.floor(translation.getZ()));
							if (region.isChunkLoaded(placing_pos)) {
								
								if (DistanceField.Cylinder(testing_pos, pos, height, 1)) {
									if (region.getRandom().nextInt(10) == 0)
										region.setBlockState(placing_pos, BlockRegistry.EBON_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 2), 0);
								}
								
								if (DistanceField.cone(testing_pos, pos, height, 1.5f)) {
									region.setBlockState(placing_pos, BlockRegistry.EBON_LOG.getDefaultState(), 0);
								}
								
								if (!bare) {
									if (DistanceField.sphere(testing_pos, leaf_pos, 3) &&
											DistanceField.cone(testing_pos, leaf_pos_cone, 7, 4)) {
										if (region.getRandom().nextInt(10) < 9)
										region.setBlockState(placing_pos, BlockRegistry.EBON_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 2), 0);
									}
								} else {
									if (DistanceField.sphere(testing_pos, leaf_pos, 2)) {
										if (region.getRandom().nextInt(10) == 0)
										region.setBlockState(placing_pos, BlockRegistry.EBON_LOG.getDefaultState(), 0);
									}
								}
								
								
							}
						}
					}
				}
			}
		}
	}
	
	private void GenerateCrimsonTrees(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == BlockRegistry.CRIMSON_GRASS) {
			if (region.getRandom().nextInt(100) == 0) {
				int height = region.getRandom().nextInt(8) + 8;
				BlockPos leaf_pos = pos.up(height);
				BlockPos leaf_pos_cone = pos.up(height - 3);
				BlockPos.Mutable testing_pos = new BlockPos.Mutable();
				
				boolean bare = region.getRandom().nextInt(50) == 0;
				
				Quaternion rotation = new Quaternion((region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), (region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), (region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), true);

				Vec3f translation = new Vec3f();
				
				for (int X = -10; X < 10; X++) {
					for (int Y = 0; Y < 24; Y++) {
						for (int Z = -10; Z < 10; Z++) {
							placing_pos.set(x + X, y + Y, z + Z);
							translation.set(X, Y, Z);
							translation.rotate(rotation);
							
							testing_pos.set(x + (int)Math.floor(translation.getX()), y + (int)Math.floor(translation.getY()), z + (int)Math.floor(translation.getZ()));
							if (region.isChunkLoaded(placing_pos)) {
								
								if (DistanceField.Cylinder(testing_pos, pos, height, 1)) {
									if (region.getRandom().nextInt(10) == 0)
										region.setBlockState(placing_pos, BlockRegistry.CRIMSON_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 2), 0);
								}
								
								if (DistanceField.cone(testing_pos, pos, height, 1.5f)) {
									region.setBlockState(placing_pos, BlockRegistry.SHADEWOOD_LOG.getDefaultState(), 0);
								}
								
								if (!bare) {
									if (DistanceField.sphere(testing_pos, leaf_pos, 3) &&
											DistanceField.cone(testing_pos, leaf_pos_cone, 7, 4)) {
										if (region.getRandom().nextInt(10) < 9)
										region.setBlockState(placing_pos, BlockRegistry.CRIMSON_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 2), 0);
									}
								} else {
									if (DistanceField.sphere(testing_pos, leaf_pos, 2)) {
										if (region.getRandom().nextInt(10) == 0)
										region.setBlockState(placing_pos, BlockRegistry.SHADEWOOD_LOG.getDefaultState(), 0);
									}
								}
								
								
							}
						}
					}
				}
			}
		}
	}
	
	private void GenerateJungleTrees(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		BlockState log = corruption ? BlockRegistry.EBON_LOG.getDefaultState() : BlockRegistry.SHADEWOOD_LOG.getDefaultState();
		BlockState leaf = corruption ? BlockRegistry.EBON_LEAVES.getDefaultState() : BlockRegistry.CRIMSON_LEAVES.getDefaultState();
		BlockState grass = corruption ? BlockRegistry.EBON_GRASS.getDefaultState() : BlockRegistry.CRIMSON_GRASS.getDefaultState();
		if (y <= 0) {
			grass = BlockRegistry.JUNGLE_GRASS.getDefaultState();
			log = Blocks.JUNGLE_LOG.getDefaultState();
			leaf = Blocks.JUNGLE_LEAVES.getDefaultState();
		}
		if (region.getBlockState(pos).getBlock() == BlockRegistry.JUNGLE_GRASS) {
			region.setBlockState(pos, grass, 0);
			
			if (region.getRandom().nextInt(200) == 0) {
				int height = region.getRandom().nextInt(8) + 8;
				BlockPos leaf_pos = pos.up(height);
				BlockPos leaf_pos_cone = pos.up(height - 3);
				BlockPos.Mutable testing_pos = new BlockPos.Mutable();
				
				boolean bare = region.getRandom().nextInt(50) == 0;
				
				Quaternion rotation = new Quaternion((region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), (region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), (region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), true);

				Vec3f translation = new Vec3f();
				
				for (int X = -10; X < 10; X++) {
					for (int Y = 0; Y < 24; Y++) {
						for (int Z = -10; Z < 10; Z++) {
							placing_pos.set(x + X, y + Y, z + Z);
							translation.set(X, Y, Z);
							translation.rotate(rotation);
							
							testing_pos.set(x + (int)Math.floor(translation.getX()), y + (int)Math.floor(translation.getY()), z + (int)Math.floor(translation.getZ()));
							if (region.isChunkLoaded(placing_pos)) {
								
								if (DistanceField.Cylinder(testing_pos, pos, height, 1)) {
									if (region.getRandom().nextInt(10) == 0)
										region.setBlockState(placing_pos, leaf.with(LeavesBlock.PERSISTENT, true), 0);
								}
								
								if (DistanceField.cone(testing_pos, pos, height, 1.5f)) {
									region.setBlockState(placing_pos, log, 0);
								}
								
								if (!bare) {
									if (DistanceField.sphere(testing_pos, leaf_pos, 3) &&
											DistanceField.cone(testing_pos, leaf_pos_cone, 7, 4)) {
										if (region.getRandom().nextInt(10) < 9)
										region.setBlockState(placing_pos, leaf.with(LeavesBlock.PERSISTENT, true), 0);
									}
								} else {
									if (DistanceField.sphere(testing_pos, leaf_pos, 2)) {
										if (region.getRandom().nextInt(10) == 0)
										region.setBlockState(placing_pos, log, 0);
									}
								}
								
								
							}
						}
					}
				}
			} else if (region.getRandom().nextInt(100) == 0) {
				int height = random.nextInt(10) > 0 ? (random.nextInt(50) + 10) : (random.nextInt(3));
				int leaves = random.nextInt(3);
				for (int k = -2; k < height; k++) {
					if (leaves > 0) {
						if (random.nextInt(5) == 0 && k >= height * 0.5f) {
							int radius = random.nextInt(3) + 10;
							
							int rx = random.nextInt(4) - 1;
							int rz = random.nextInt(4) - 1;
							placing_pos.set(x + rx, y + k, z + rz);
							if (region.isChunkLoaded(placing_pos)) {
								region.setBlockState(placing_pos, log, 0);
							}
							
							for (int i = -radius / 2; i <= radius / 2; i++) {
								for (int j = -radius / 2; j <= radius / 2; j++) {
									for (int k1 = 0; k1 < 2; k1++) {
										int current_radius = radius / 2 - k1;
										placing_pos.set(x + i + rx, y + k + k1, z + j + rz);
										if (Math.sqrt(i * i + j * j) <= current_radius) {
											if (region.isChunkLoaded(placing_pos)) {
												region.setBlockState(placing_pos, leaf.with(LeavesBlock.PERSISTENT, true), 0);
											}
										}
									}
								}
							}
							leaves--;
						}
					}
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 2; j++) {
							placing_pos.set(x + i, y + k, z + j);
							if (region.isChunkLoaded(placing_pos)) {
								region.setBlockState(placing_pos, log, 0);
							}
						}
					}
				}
				//top of the tree
				int radius = random.nextInt(3) + 10;
				for (int i = -radius / 2; i <= radius / 2; i++) {
					for (int j = -radius / 2; j <= radius / 2; j++) {
						for (int k = 0; k < 2; k++) {
							int current_radius = radius / 2 - k;
							placing_pos.set(x + i, y + height + k, z + j);
							if (Math.sqrt(i * i + j * j) <= current_radius) {
								if (region.isChunkLoaded(placing_pos)) {
									region.setBlockState(placing_pos, leaf.with(LeavesBlock.PERSISTENT, true), 0);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void GeneratePurityTrees(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK) {
			if (region.getRandom().nextInt(100) == 0) {
				int height = region.getRandom().nextInt(8) + 8;
				BlockPos leaf_pos = pos.up(height);
				BlockPos leaf_pos_cone = pos.up(height - 3);
				BlockPos.Mutable testing_pos = new BlockPos.Mutable();
				
				boolean bare = region.getRandom().nextInt(50) == 0;
				
				Quaternion rotation = new Quaternion((region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), (region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), (region.getRandom().nextInt(90)-45) * (15.0f / 45.0f), true);

				Vec3f translation = new Vec3f();
				
				for (int X = -10; X < 10; X++) {
					for (int Y = 0; Y < 24; Y++) {
						for (int Z = -10; Z < 10; Z++) {
							placing_pos.set(x + X, y + Y, z + Z);
							translation.set(X, Y, Z);
							translation.rotate(rotation);
							
							testing_pos.set(x + (int)Math.floor(translation.getX()), y + (int)Math.floor(translation.getY()), z + (int)Math.floor(translation.getZ()));
							if (region.isChunkLoaded(placing_pos)) {
								
								if (DistanceField.Cylinder(testing_pos, pos, height, 1)) {
									if (region.getRandom().nextInt(10) == 0)
										region.setBlockState(placing_pos, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 2), 0);
								}
								
								if (DistanceField.cone(testing_pos, pos, height, 1.5f)) {
									region.setBlockState(placing_pos, Blocks.OAK_LOG.getDefaultState(), 0);
								}
								
								if (!bare) {
									if (DistanceField.sphere(testing_pos, leaf_pos, 3) &&
											DistanceField.cone(testing_pos, leaf_pos_cone, 7, 4)) {
										if (region.getRandom().nextInt(10) < 9)
										region.setBlockState(placing_pos, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 2), 0);
									}
								} else {
									if (DistanceField.sphere(testing_pos, leaf_pos, 2)) {
										if (region.getRandom().nextInt(10) == 0)
										region.setBlockState(placing_pos, Blocks.OAK_LOG.getDefaultState(), 0);
									}
								}
								
								
							}
						}
					}
				}
			}
		}
	}
	
	private void GenerateSpruceTrees(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == Blocks.SNOW_BLOCK) {
			if (region.getRandom().nextInt(300) == 0) {
				placing_pos.set(x, y + 1, z);
				if (region.isChunkLoaded(placing_pos)) {
					if (region.getBlockState(placing_pos) != Blocks.AIR.getDefaultState()) return;
				}
				int height = (random.nextInt(3) + 1) * 2 + 6;
				for (int i = 0; i < height; i++) {
					
					
					if (i > 3 && i % 2 == 0) {
						for (int j = -2; j <= 2; j++) {
							for (int k = -2; k <= 2; k++) {
								if (Math.sqrt(j * j + k * k) <= 2) {
									placing_pos.set(x + j, y + 1 + i, z + k);
									if (region.isChunkLoaded(placing_pos)) region.setBlockState(placing_pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 4), 0);
								}
							}
						}
					}
					placing_pos.set(x, y + 1 + i, z);
					if (region.isChunkLoaded(placing_pos)) {
						region.setBlockState(placing_pos, Blocks.SPRUCE_LOG.getDefaultState(), 0);
					}
				}
				if (region.isChunkLoaded(placing_pos)) {
					region.setBlockState(placing_pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 4), 0);
				}
				placing_pos.set(x, y + height + 1, z);
				if (region.isChunkLoaded(placing_pos)) {
					region.setBlockState(placing_pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 4), 0);
				}
				placing_pos.set(x - 1, y + height + 1, z);
				if (region.isChunkLoaded(placing_pos)) {
					region.setBlockState(placing_pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 4), 0);
				}
				placing_pos.set(x + 1, y + height + 1, z);
				if (region.isChunkLoaded(placing_pos)) {
					region.setBlockState(placing_pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 4), 0);
				}
				placing_pos.set(x, y + height + 1, z - 1);
				if (region.isChunkLoaded(placing_pos)) {
					region.setBlockState(placing_pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 4), 0);
				}
				placing_pos.set(x, y + height + 1, z + 1);
				if (region.isChunkLoaded(placing_pos)) {
					region.setBlockState(placing_pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 4), 0);
				}
				placing_pos.set(x, y + height + 2, z);
				if (region.isChunkLoaded(placing_pos)) {
					region.setBlockState(placing_pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 4), 0);
				}
			}
		}
	}
	
	private void GenerateTaigaPlants(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == Blocks.SNOW_BLOCK) {
			if (region.getRandom().nextInt(50) == 0) {
				placing_pos.set(x, y + 1, z);
				if (region.isChunkLoaded(placing_pos)) {
					if (region.getBlockState(placing_pos) != Blocks.AIR.getDefaultState()) return;
					if (random.nextInt(10) == 0) {
						region.setBlockState(placing_pos, Blocks.DEAD_BUSH.getDefaultState(), 0);
					}
					if (random.nextInt(4) == 0) {
						region.setBlockState(placing_pos, Blocks.SWEET_BERRY_BUSH.getDefaultState(), 0);
					}
					if (random.nextInt(5) == 0) {
						region.setBlockState(placing_pos, Blocks.ROSE_BUSH.getDefaultState(), 0);
					}
					if (random.nextInt(10) == 0) {
						region.setBlockState(placing_pos, Blocks.AZURE_BLUET.getDefaultState(), 0);
					}
					
				}
			}
		}
	}
	
	private void GenerateGemTree(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos, Block gem) {
		int height = random.nextInt(5) + 5;
		for (int i = 0; i <= height + 1; i++) {
			placing_pos.set(x, y + i, z);
			if (region.isChunkLoaded(placing_pos))
			{
				if (i <= height)
				region.setBlockState(placing_pos, Blocks.BLACKSTONE.getDefaultState(), 0);
			}
			if (i >= height * 0.5f) {
				for (int j = 0; j < 4; j++) {
					if (random.nextInt(4) == 0) {
						placing_pos.set(x + random.nextInt(3) - 1, y + i, z + random.nextInt(3) - 1);
						if (region.isChunkLoaded(placing_pos))
							region.setBlockState(placing_pos, gem.getDefaultState(), 0);
					}
				}
			}
		}
	}
	
	private void GenerateDesertPlants(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == Blocks.SAND ||
				region.getBlockState(pos).getBlock() == BlockRegistry.CRIMSON_SAND ||
				region.getBlockState(pos).getBlock() == BlockRegistry.EBON_SAND) {
			if (region.getRandom().nextInt(50) == 0) {
				placing_pos.set(x, y + 1, z);
				if (region.isChunkLoaded(placing_pos)) {
					if (region.getBlockState(placing_pos) != Blocks.AIR.getDefaultState()) return;
					
					if (random.nextInt(2) == 0) {
						region.setBlockState(placing_pos, Blocks.DEAD_BUSH.getDefaultState(), 0);
						if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.BONE_BLOCK.getDefaultState(), 0);
							for (int i = 0; i < 4; i++) {
								if (random.nextInt(4) == 0) {
									placing_pos.set(x + random.nextInt(3) - 1, y + 1, z + random.nextInt(3) - 1);
									if (region.isChunkLoaded(placing_pos))
										region.setBlockState(placing_pos, Blocks.BONE_BLOCK.getDefaultState(), 0);
								}
							}
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.DEAD_BRAIN_CORAL_FAN.getDefaultState().with(CoralBlock.WATERLOGGED, false), 0);
						} else if (random.nextInt(20) == 0) {
							region.setBlockState(placing_pos, Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState(), 0);
						} else if (random.nextInt(20) == 0) {
							region.setBlockState(placing_pos, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 0);
							if (random.nextInt(4) == 0) {
								placing_pos.set(x, y + 2, z);
								if (region.isChunkLoaded(placing_pos))
									region.setBlockState(placing_pos, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 0);
							}
						} else if (random.nextInt(10) == 0) {
							//dead tree
							int height = random.nextInt(5) + 5;
							for (int i = 0; i <= height + 1; i++) {
								placing_pos.set(x, y + 1 + i, z);
								if (region.isChunkLoaded(placing_pos))
								{
									if (i <= height)
									region.setBlockState(placing_pos, corruption ? BlockRegistry.EBON_LOG.getDefaultState() : BlockRegistry.SHADEWOOD_LOG.getDefaultState(), 0);
								}
								if (i >= height * 0.5f) {
									for (int j = 0; j < 4; j++) {
										if (random.nextInt(4) == 0) {
											placing_pos.set(x + random.nextInt(3) - 1, y + i, z + random.nextInt(3) - 1);
											if (region.isChunkLoaded(placing_pos))
												region.setBlockState(placing_pos, corruption ? BlockRegistry.EBON_LOG.getDefaultState() : BlockRegistry.SHADEWOOD_LOG.getDefaultState(), 0);
										}
									}
								}
							}
						}
					} else {
						for (int i = 0; i < random.nextInt(3) + 2; i++) {
							placing_pos.set(x, y + 1 + i, z);
							if (region.isChunkLoaded(placing_pos))
							{
								region.setBlockState(placing_pos, Blocks.CACTUS.getDefaultState(), 0);
							}
						}
					}
					
				}
				
			}
		}
	}
	
	private void GenerateCrimsonMushrooms(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == BlockRegistry.CRIMSON_GRASS) {
			if (region.getRandom().nextInt(100) == 0) {
				placing_pos.set(x, y + 1, z);
				if (region.isChunkLoaded(placing_pos))
				{
					region.setBlockState(placing_pos, BlockRegistry.VICIOUS_MUSHROOM.getDefaultState(), 0);
					if (random.nextInt(50) == 0) {
						region.setBlockState(placing_pos, Blocks.WITHER_ROSE.getDefaultState(), 0);
					} else if (random.nextInt(50) == 0) {
						region.setBlockState(placing_pos, Blocks.POINTED_DRIPSTONE.getDefaultState(), 0);
					}
				}
			}
		}
	}
	
	private void GenerateCorruptMushrooms(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == BlockRegistry.EBON_GRASS) {
			if (region.getRandom().nextInt(100) == 0) {
				placing_pos.set(x, y + 1, z);
				if (region.isChunkLoaded(placing_pos))
				{
					region.setBlockState(placing_pos, BlockRegistry.VILE_MUSHROOM.getDefaultState(), 0);
					if (random.nextInt(50) == 0) {
						region.setBlockState(placing_pos, Blocks.WITHER_ROSE.getDefaultState(), 0);
					}
				}
			}
		}
	}
	
	private void GenerateOre(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos, Block place_in, Block ore, int spread) {
		GenerateOre(region, x, y, z, placing_pos, place_in, ore, spread);
		if (place_in == Blocks.STONE) { 
			GenerateOre(region, x, y, z, placing_pos, Blocks.SANDSTONE, ore, spread);
			GenerateOre(region, x, y, z, placing_pos, Blocks.PACKED_ICE, ore, spread);
			GenerateOre(region, x, y, z, placing_pos, BlockRegistry.MUD, ore, spread);
			GenerateOre(region, x, y, z, placing_pos, BlockRegistry.EBON_SANDSTONE, ore, spread);
			GenerateOre(region, x, y, z, placing_pos, BlockRegistry.CRIMSON_SANDSTONE, ore, spread);
			GenerateOre(region, x, y, z, placing_pos, BlockRegistry.EBON_STONE, ore, spread);
			GenerateOre(region, x, y, z, placing_pos, BlockRegistry.CRIMSON_STONE, ore, spread);
		}
	}
	
	private void GenerateOre(ChunkRegion region, int x, int y, int z, BlockPos.Mutable placing_pos, Block place_in, Block ore, int spread) {
		if (spread <= 0) return;
		placing_pos.set(x, y, z);
		
		if (region.isChunkLoaded(placing_pos))
		if (region.getBlockState(placing_pos).getBlock() == place_in) {
			region.setBlockState(placing_pos, ore.getDefaultState(), 0);
			spread--;
			GenerateOre(region, x + random.nextInt(3) - 1, y + random.nextInt(3) - 1, z + random.nextInt(3) - 1, placing_pos, place_in, ore, spread);
			if (random.nextBoolean()) {
				GenerateOre(region, x + random.nextInt(3) - 1, y + random.nextInt(3) - 1, z + random.nextInt(3) - 1, placing_pos, place_in, ore, spread);
			}
		}
	}
	
	private void GenerateLowCaveFeatures(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == Blocks.STONE) {
			//basalt towers
			if (random.nextInt(50) == 0) {
				int height = random.nextInt(4) + 3;
				for (int i = y; i < y + height + 1; i++) {
					placing_pos.set(x, i, z);
					if (region.isChunkLoaded(placing_pos)) {
						region.setBlockState(placing_pos, Blocks.BASALT.getDefaultState(), 0);
					}
				}
			}
		}
	}
	
	private void GeneratePurityGrass(ChunkRegion region, int x, int y, int z, BlockPos.Mutable pos, BlockPos.Mutable placing_pos) {
		pos.set(x, y, z);
		if (region.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK || region.getBlockState(pos).getBlock() == BlockRegistry.JUNGLE_GRASS) {
			placing_pos.set(x, y + 1, z);
			if (region.isChunkLoaded(placing_pos)) {
				if (region.getBlockState(placing_pos) == Blocks.CAVE_AIR.getDefaultState()) {
					if (region.getRandom().nextInt(10) == 0) {
						
						region.setBlockState(placing_pos, Blocks.GRASS.getDefaultState(), 0);
						if (random.nextInt(50) == 0) {
							region.setBlockState(placing_pos, Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 0);
							if (random.nextInt(5) == 0) {
								placing_pos.set(x, y + 2, z);
								if (region.isChunkLoaded(placing_pos))
								region.setBlockState(placing_pos, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 0);
							}
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.SMALL_DRIPLEAF.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.POINTED_DRIPSTONE.getDefaultState(), 0);
						} else if (random.nextInt(50) == 0) {
							region.setBlockState(placing_pos, Blocks.SWEET_BERRY_BUSH.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.AZURE_BLUET.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.BLUE_ORCHID.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.DANDELION.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.BIG_DRIPLEAF.getDefaultState(), 0);
						} else if (random.nextInt(25) == 0) {
							region.setBlockState(placing_pos, Blocks.SHROOMLIGHT.getDefaultState(), 0);
						}
						
					}
					if (random.nextInt(100) == 0) {
						int height = 3 + random.nextInt(4);
						for (int i = 0; i < height; i++) {
							placing_pos.set(x, y + 1 + i, z);
							if (region.isChunkLoaded(placing_pos)) 
							{
								region.setBlockState(placing_pos, Blocks.BIG_DRIPLEAF_STEM.getDefaultState(), 0);
							}
						}
						placing_pos.set(x, y + 1 + height, z);
						if (region.isChunkLoaded(placing_pos)) 
						{
							region.setBlockState(placing_pos, Blocks.BIG_DRIPLEAF.getDefaultState(), 0);
						}
					}
				} else {
					if (region.getRandom().nextInt(10) == 0) {
						
						region.setBlockState(placing_pos, Blocks.GRASS.getDefaultState(), 0);
						if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.SUNFLOWER.getDefaultState(), 0);
							placing_pos.set(x, y + 1, z);
							if (region.isChunkLoaded(placing_pos)) {
								region.setBlockState(placing_pos, Blocks.SUNFLOWER.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER), 0);
							}
						} else if (random.nextInt(50) == 0) {
							region.setBlockState(placing_pos, Blocks.SMALL_DRIPLEAF.getDefaultState(), 0);
						} else if (random.nextInt(50) == 0) {
							region.setBlockState(placing_pos, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 0);
							if (random.nextInt(5) == 0) {
								placing_pos.set(x, y + 2, z);
								if (region.isChunkLoaded(placing_pos))
								region.setBlockState(placing_pos, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 0);
							}
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.OXEYE_DAISY.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.ROSE_BUSH.getDefaultState(), 0);
							placing_pos.set(x, y + 1, z);
							if (region.isChunkLoaded(placing_pos)) {
								region.setBlockState(placing_pos, Blocks.ROSE_BUSH.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER), 0);
							}
						} else if (random.nextInt(50) == 0) {
							region.setBlockState(placing_pos, Blocks.SWEET_BERRY_BUSH.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.AZURE_BLUET.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.BLUE_ORCHID.getDefaultState(), 0);
						} else if (random.nextInt(10) == 0) {
							region.setBlockState(placing_pos, Blocks.DANDELION.getDefaultState(), 0);
						} else if (random.nextInt(25) == 0) {
							region.setBlockState(placing_pos, Blocks.CRIMSON_FUNGUS.getDefaultState(), 0);
						}
					}
				}
			}
			
			
		}
	}
}

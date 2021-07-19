package kelvin.trewrite.main.resources;

import java.util.List;
import java.util.Random;

import kelvin.trewrite.entities.monsters.SlimeMonsterEntity;
import kelvin.trewrite.registry.BlockRegistry;
import kelvin.trewrite.registry.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class EntitySpawner {
	private static int min_spawn_radius = 24;
	private static int max_spawn_radius = min_spawn_radius * 2;
	private static Random random = new Random();
	
	private static long last_spawned = 0;
	
	private static int caves = 30;
	
	public static void TrySpawnEntities(BlockPos.Mutable pos, World world) {
		if (world.isClient) return; 
		List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class, new Box(
				pos.getX() - max_spawn_radius, pos.getY() - max_spawn_radius, pos.getZ() - max_spawn_radius,
				pos.getX() + max_spawn_radius, pos.getY() + max_spawn_radius, pos.getZ() + max_spawn_radius
				));
		if (entities.size() >= 40) return;
		if (System.currentTimeMillis() >= last_spawned + 2000) { // minimum of 5 seconds between each entity spawn
			int xp = random.nextInt(2) * 2 - 1;
			int yp = random.nextInt(2) * 2 - 1;
			int zp = random.nextInt(2) * 2 - 1;
			int xr = random.nextInt(max_spawn_radius - min_spawn_radius) + min_spawn_radius;
			int yr = random.nextInt(max_spawn_radius - min_spawn_radius) + min_spawn_radius;
			int zr = random.nextInt(max_spawn_radius - min_spawn_radius) + min_spawn_radius;
			
			pos.set(pos.getX() + xp * xr, pos.getY() + yp * yr, pos.getZ() + zp * zr);
			
			TrySpawnEntity(pos, world);
		}
	}
	
	private static void TrySpawnEntity(BlockPos.Mutable pos, World world) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		boolean day = world.isDay();
		
		if (day) {
			DoDaySpawn(x, y, z, pos, world);
		} else {
			DoNightSpawn(x, y, z, pos, world);
		}
		
	}
	
	private static void DoDaySpawn(int x, int y, int z, BlockPos.Mutable pos, World world) {
		Block block = world.getBlockState(pos).getBlock();

		if (y > caves) {
			if (!block.getDefaultState().isAir()) {
				if (block != BlockRegistry.CRIMSON_GRASS &&
						block != BlockRegistry.EBON_GRASS &&
						block != BlockRegistry.CRIMSON_STONE &&
						block != BlockRegistry.EBON_STONE && 
						block != BlockRegistry.CRIMSON_SANDSTONE &&
						block != BlockRegistry.EBON_SANDSTONE &&
						block != BlockRegistry.CRIMSON_SAND &&
						block != BlockRegistry.EBON_SAND) {
					if (world.getBlockState(pos.up()).isAir()) 
					{
						SlimeMonsterEntity slime = new SlimeMonsterEntity(EntityRegistry.BLUE_SLIME, world);
						slime.setPos(x, y + 1, z);
						slime.teleport(x, y + 1, z);
						
						System.out.println("spawn! " + x + ", " + y + ", " + z);
						
						SpawnEntity(world, slime);
					}
				}
			}
			
		}
	}
	
	private static void DoNightSpawn(int x, int y, int z, BlockPos.Mutable pos, World world) {

		Block block = world.getBlockState(pos).getBlock();
		if (y > caves) {
			if (!block.getDefaultState().isAir()) {
				if (block != BlockRegistry.CRIMSON_GRASS &&
						block != BlockRegistry.EBON_GRASS &&
						block != BlockRegistry.CRIMSON_STONE &&
						block != BlockRegistry.EBON_STONE && 
						block != BlockRegistry.CRIMSON_SANDSTONE &&
						block != BlockRegistry.EBON_SANDSTONE &&
						block != BlockRegistry.CRIMSON_SAND &&
						block != BlockRegistry.EBON_SAND) {
					if (world.getBlockState(pos.up()).isAir()) 
					{
						ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, world);
						zombie.setPos(x, y + 1, z);
						zombie.teleport(x, y + 1, z);
						
						SpawnEntity(world, zombie);
						
					}
				}
			}
		}
	}
	
	private static void SpawnEntity(World world, Entity entity) {
		last_spawned = System.currentTimeMillis();
		world.spawnEntity(entity);
	}
}

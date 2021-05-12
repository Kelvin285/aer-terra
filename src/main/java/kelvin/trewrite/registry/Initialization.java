package kelvin.trewrite.registry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;

import kelvin.trewrite.main.resources.Resources;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.HorizontalVoronoiBiomeAccessType;
import net.minecraft.world.dimension.DimensionType;

public class Initialization {
	
	private static EntityAttribute register_attribute(String id, EntityAttribute attribute) {
		return (EntityAttribute) Registry.register(Registry.ATTRIBUTE, id, attribute);
	}
	
	public static final EntityAttribute MODIFIED_MAX_HEALTH = register_attribute("trewrite.max_health",
			(new ClampedEntityAttribute("attribute.name.trewrite.max_health", 20.0D, 1.0D, 1024.0D)).setTracked(true));
	

	private static final DimensionType MODIFIED_OVERWORLD = DimensionType.create(OptionalLong.empty(), true, false, false, true, 1.0D, false, false, true, false, true, 
			-256,
			256, 
			256, 
			HorizontalVoronoiBiomeAccessType.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getId(),
			DimensionType.OVERWORLD_ID, 0.0F);
	
	public static HashMap<String, Boolean> player_strike_direction = new HashMap<String, Boolean>();

	
	
	public static void Init() {
		ModifyEntities();
		
		EntityRegistry.Register();
		BiomeRegistry.RegisterBiomes();
		ModifyWorldHeight();
		
		BlockRegistry.RegisterBlocks();
	}
	

	
	private static void ModifyWorldHeight() {
		//OVERWORLD: 12
		Field[] dimension_fields = DimensionType.class.getDeclaredFields();
		for (int i = 0; i < dimension_fields.length; i++) {
			try {
				Resources.makeFieldAccessible(dimension_fields[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(dimension_fields[i].getName() + ", " + i);
		}
		int overworld_num = 12;
		Field overworld_field = dimension_fields[overworld_num];
		
		try {
			Resources.makeFieldAccessible(overworld_field);
			overworld_field.set(null, MODIFIED_OVERWORLD);
		}catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * OVERWORLD = create(OptionalLong.empty(), true, false, false, true, 1.0D, false, false, true, false, true, -64,
				384, 384, HorizontalVoronoiBiomeAccessType.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getId(),
				OVERWORLD_ID, 0.0F);
		 */
	}
	
	private static void ModifyEntities() {
		ModifyPlayer();
		ModifyZombie();
	}
	
	private static void ModifyZombie() {
		EntityDimensions STANDING_DIMENSIONS = EntityDimensions.changing(1.2F, 2.8F);
		Map<EntityPose, EntityDimensions> POSE_DIMENSIONS = ImmutableMap.<EntityPose, EntityDimensions>builder().put(EntityPose.STANDING, STANDING_DIMENSIONS)
				.put(EntityPose.SLEEPING, EntityDimensions.fixed(0.2F, 0.2F))
				.put(EntityPose.FALL_FLYING, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.SWIMMING, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.CROUCHING, EntityDimensions.changing(1.2F, 1.8F))
				.put(EntityPose.DYING, EntityDimensions.fixed(0.2F, 0.2F)).build();
		
		Field[] dimension_fields = ZombieEntity.class.getDeclaredFields();
		
		for (int i = 0; i < dimension_fields.length; i++) {
			try {
				Resources.makeFieldAccessible(dimension_fields[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			dimension_fields[0].set(null, STANDING_DIMENSIONS);
			dimension_fields[1].set(null, POSE_DIMENSIONS);
		}catch (Exception e) {
			
		}
		
		Field[] size_fields = EntityDimensions.class.getDeclaredFields();
		for (int i = 0; i < size_fields.length; i++) {
			try {
				Resources.makeFieldAccessible(size_fields[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			size_fields[0].set(EntityType.ZOMBIE.getDimensions(), 1.2F);
			size_fields[1].set(EntityType.ZOMBIE.getDimensions(), 2.8F);
		}catch (Exception e) {
			
		}
	}
	
	private static void ModifyPlayer() {
		//	private static final EntityAttributeModifier SPRINTING_SPEED_BOOST;
		Field sp = LivingEntity.class.getDeclaredFields()[3];
		Field sp_id = LivingEntity.class.getDeclaredFields()[0];

		try {
			Resources.makeFieldAccessible(sp);
			Resources.makeFieldAccessible(sp_id);
			sp.set(null, new EntityAttributeModifier((UUID) sp_id.get(null), "Sprinting speed boost", 0.30000001192092896D * 2.0f, Operation.MULTIPLY_TOTAL));

		} catch (Exception e) {
			e.printStackTrace();
		}
		EntityDimensions STANDING_DIMENSIONS = EntityDimensions.changing(1.2F, 2.8F);
		Map<EntityPose, EntityDimensions> POSE_DIMENSIONS = ImmutableMap.<EntityPose, EntityDimensions>builder().put(EntityPose.STANDING, STANDING_DIMENSIONS)
				.put(EntityPose.SLEEPING, EntityDimensions.fixed(0.2F, 0.2F))
				.put(EntityPose.FALL_FLYING, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.SWIMMING, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(1.2F, 1.2F))
				.put(EntityPose.CROUCHING, EntityDimensions.changing(1.2F, 1.8F))
				.put(EntityPose.DYING, EntityDimensions.fixed(0.2F, 0.2F)).build();
		
		Field[] dimension_fields = PlayerEntity.class.getDeclaredFields();
		
		for (int i = 0; i < dimension_fields.length; i++) {
			try {
				Resources.makeFieldAccessible(dimension_fields[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			dimension_fields[0].set(null, STANDING_DIMENSIONS);
			dimension_fields[1].set(null, POSE_DIMENSIONS);
		}catch (Exception e) {
			
		}
		
		Field[] size_fields = EntityDimensions.class.getDeclaredFields();
		for (int i = 0; i < size_fields.length; i++) {
			try {
				Resources.makeFieldAccessible(size_fields[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			size_fields[0].set(EntityType.PLAYER.getDimensions(), 1.2F);
			size_fields[1].set(EntityType.PLAYER.getDimensions(), 2.8F);
		}catch (Exception e) {
			
		}
	}
}

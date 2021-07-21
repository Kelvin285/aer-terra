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

	public static HashMap<String, Boolean> player_strike_direction = new HashMap<String, Boolean>();

	
	
	public static void Init() {
		EntityRegistry.Register();
		BiomeRegistry.RegisterBiomes();
		DimensionRegistry.RegisterDimensions();

		BlockRegistry.RegisterBlocks();
		ItemRegistry.RegisterItems();
	}

}

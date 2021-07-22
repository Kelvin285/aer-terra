package kelvin.aer_terra.registry;

import java.util.HashMap;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

public class Initialization {
	
	private static EntityAttribute register_attribute(String id, EntityAttribute attribute) {
		return (EntityAttribute) Registry.register(Registry.ATTRIBUTE, id, attribute);
	}
	
	public static final EntityAttribute MODIFIED_MAX_HEALTH = register_attribute("aer_terra.max_health",
			(new ClampedEntityAttribute("attribute.name.aer_terra.max_health", 20.0D, 1.0D, 1024.0D)).setTracked(true));

	public static HashMap<String, Boolean> player_strike_direction = new HashMap<String, Boolean>();

	
	
	public static void Init() {
		EntityRegistry.Register();
		BiomeRegistry.RegisterBiomes();
		DimensionRegistry.RegisterDimensions();

		BlockRegistry.RegisterBlocks();
		ItemRegistry.RegisterItems();
		FeatureRegistry.RegisterFeatures();
	}

}

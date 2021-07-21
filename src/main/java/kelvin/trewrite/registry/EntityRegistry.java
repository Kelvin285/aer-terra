package kelvin.trewrite.registry;

import java.lang.reflect.Field;
import java.util.Map;

import kelvin.trewrite.entities.models.monster.SlimeMonsterRenderer;
import kelvin.trewrite.entities.monsters.SlimeMonsterEntity;
import kelvin.trewrite.main.resources.Resources;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
	
	public static EntityType<SlimeMonsterEntity> BLUE_SLIME;
	
	
	@SuppressWarnings("unchecked")
	private static <T extends Entity> void register_model(EntityType<? extends T> type, EntityRendererFactory<T> factory) {
		Map<EntityType<?>, EntityRendererFactory<?>> rendererFactories = null;
		Field rf = EntityRenderers.class.getDeclaredFields()[1];
		try {
			Resources.makeFieldAccessible(rf);
			
			rendererFactories = (Map<EntityType<?>, EntityRendererFactory<?>>) rf.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rendererFactories.put(type, factory);
	}
	
	public static void Register() {
		BLUE_SLIME = Registry.register(
				Registry.ENTITY_TYPE,
				new Identifier("trewrite", "blue_slime"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC, SlimeMonsterEntity::new).size(EntityDimensions.fixed(2.04F, 2.04F)).build()
				);
		RegisterAttributes();
		RegisterModels();
	}
	
	public static void RegisterAttributes() {
		FabricDefaultAttributeRegistry.register(BLUE_SLIME, HostileEntity.createHostileAttributes());

	}
	
	public static void RegisterModels() {
		EntityRendererRegistry.INSTANCE.register(BLUE_SLIME, 
				(context) -> {
					return new SlimeMonsterRenderer(context, "trewrite:textures/entities/slime/blue_slime.png");
				}
				);
	}
}

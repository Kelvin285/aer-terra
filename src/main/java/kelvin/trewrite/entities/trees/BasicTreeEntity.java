package kelvin.trewrite.entities.trees;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import kelvin.trewrite.registry.EntityRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class BasicTreeEntity extends LivingEntity {

	private static final EntityDimensions MARKER_DIMENSIONS = new EntityDimensions(2, 8, true);

	public static ArrayList<ItemStack> armorItems = new ArrayList<ItemStack>();
	
	public BasicTreeEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public Iterable<ItemStack> getArmorItems() {
		return armorItems;
	}

	@Override
	public ItemStack getEquippedStack(EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void equipStack(EquipmentSlot slot, ItemStack stack) {
		
	}

	@Override
	public Arm getMainArm() {
		return Arm.LEFT;
	}
	
	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	protected void tickCramming() {
		List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox());

		for (int i = 0; i < list.size(); ++i) {
			Entity entity = (Entity) list.get(i);
			if (this.squaredDistanceTo(entity) <= 0.2D) {
				entity.pushAwayFrom(this);
			}
		}

	}
	
	public boolean damage(DamageSource source, float amount) {
		return false; 
	}
	
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		double d = this.getBoundingBox().getAverageSideLength() * 4.0D;
		if (Double.isNaN(d) || d == 0.0D) {
			d = 4.0D;
		}

		d *= 64.0D;
		return distance < d * d;
	}
	
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0;
	}
	
	public void tick() {
		super.tick();
	}
	
	protected SoundEvent getFallSound(int distance) {
		return SoundEvents.ENTITY_ARMOR_STAND_FALL;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_ARMOR_STAND_HIT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
	}
	
	public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
	}
	
	public boolean isAffectedBySplashPotions() {
		return false;
	}
	
	public boolean isMobOrPlayer() {
		return false;
	}
	
	public EntityDimensions getDimensions(EntityPose pose) {
		return EntityRegistry.BASIC_TREE.getDimensions();
		
	}
	
	@Environment(EnvType.CLIENT)
   public Box getVisibilityBoundingBox() {
      return this.getBoundingBox();
   }
	
	@Environment(EnvType.CLIENT)
	public ItemStack getPickBlockStack() {
		return new ItemStack(Items.OAK_PLANKS);
	}

}

package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.world.World;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends LivingEntity {
	
	protected ZombieEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	

	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(10);	
		getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(30);	
		
	}
	
	@Inject(at = @At("HEAD"), method = "convertInWater", cancellable = true)
	protected void convertInWater(CallbackInfo info) {
		info.cancel();
	}
	
	@Override
	public boolean isSubmergedIn(Tag<Fluid> tag) {
		if (tag == FluidTags.WATER) return false;
		return super.isSubmergedIn(tag);
	}
	
	@Inject(at = @At("HEAD"), method = "damage", cancellable = true)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
		if (source == DamageSource.ON_FIRE) this.damage(DamageSource.LAVA, 20);
	}
}

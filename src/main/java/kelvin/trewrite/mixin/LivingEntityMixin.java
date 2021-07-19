package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow
	private static TrackedData<Float> HEALTH;
	
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	@Shadow
	public boolean canMoveVoluntarily() {
		return false;
	}
	@Shadow
	public boolean method_29920() {
		return false;
	}
	@Shadow
	public boolean canWalkOnFluid(Fluid fluid) {
		return false;
	}
	@Shadow
	public boolean isFallFlying() {
		return false;
	}
	@Shadow
	public Vec3d method_26318(Vec3d vec3d, float f) {
		return null;
	}
	
	@Shadow
	public boolean hasStatusEffect(StatusEffect levitation) {
		return false;
	}

	@Shadow
	public StatusEffectInstance getStatusEffect(StatusEffect levitation) {
		return null;
	}
	
	@Inject(at = @At("HEAD"), method = "getJumpVelocity", cancellable = true)
	protected void getJumpVelocity(CallbackInfoReturnable<Float> info) {
		//info.setReturnValue(0.42F * this.getJumpVelocityMultiplier() * 2.75f); // terraria jump height
		info.setReturnValue(0.42F * this.getJumpVelocityMultiplier() * 2f);
	}
	@Inject(at = @At("HEAD"), method = "computeFallDamage", cancellable = true)
	protected void computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> info) {
		info.setReturnValue(MathHelper.ceil((fallDistance - 15)));
	}
	
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		this.stepHeight = 1;
	}
	
	@Shadow
	public float getMaxHealth() {
		return 0;
	}
	
	@Shadow
	public double getAttributeValue(EntityAttribute attribute) {
		return 0;
	}
	
	private boolean startDamage;
	@Inject(at = @At("HEAD"), method = "damage", cancellable = true)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
		if (source == DamageSource.LAVA) return;
		if (!startDamage) {
			startDamage = true;
			this.damage(source, amount / 10.0f);
			startDamage = false;
			info.cancel();
		}
	}
}

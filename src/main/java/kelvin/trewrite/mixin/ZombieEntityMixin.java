package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
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
}

package kelvin.trewrite.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	
	protected float dodge_ticks = 0;
	protected float stamina = 100; //max stamina = 100
	protected boolean stamina_recovery = false;
	protected float stamina_wait = 0;
	protected float weapon_wait = 0;
	
	@Shadow
	protected HungerManager hungerManager;
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	public boolean isSprinting() {
		if (this.stamina_recovery) {
			this.setSprinting(false);
			return false;
		}
		return super.isSprinting();
		//return this.getFlag(3);
	}
	
	@Inject(at = @At("HEAD"), method = "canTakeDamage", cancellable = true)
	public void canTakeDamage(CallbackInfoReturnable<Boolean> info) {
		if (this.dodge_ticks > 0) {
			info.setReturnValue(false);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getActiveEyeHeight", cancellable = true)
	public void getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> info) {
	      switch(pose.ordinal()) {
	      case 1:
	      case 2:
	      case 3:
	    	  info.setReturnValue(1.4F);
	    	  break;
	      case 4:
	         info.setReturnValue(1.62F);
	         break;
	      default:
	         info.setReturnValue(2.62F);
	         break;
	      }
	   }
	
	
	@Inject(at = @At("HEAD"), method = "jump", cancellable = true)
	public void jump(CallbackInfo info) {
		if (this.isSneaking()) {
			if (this.isOnGround() && stamina_wait == 0 && stamina_recovery == false) {
				double length = this.getVelocity().length();
				Vec3d vel = this.getVelocity();
				if (length > 0.01f) {
					this.setVelocity(new Vec3d(vel.x / length, 0.1f, vel.z / length).multiply(3, 1, 3));
					this.stamina -= 20;
					stamina_wait = 10;
					this.dodge_ticks = 15;
				}
			}
			info.cancel();
		} else {
			if (!stamina_recovery) {
				this.stamina -= 20;
				stamina_wait = 20;
			} else {
				info.cancel();
			}
		}
		
	}
	
	
	@Override
	public void swingHand(Hand hand, boolean bl) {
		if (handSwingTicks == 0) {
			
			if (!((this.getMainHandStack().getItem() instanceof SwordItem || this.getMainHandStack().getItem() instanceof TridentItem) && (weapon_wait > 0 || stamina_recovery))) {
				this.handSwingTicks = 0;
				this.handSwingProgress = 0;
				this.handSwinging = true;
			}

		} else {
			return;
		}
		if (weapon_wait == 0 && !stamina_recovery) {
			
			
			if (this.getMainHandStack() != null)
				if (this.getMainHandStack().getItem() instanceof SwordItem || this.getMainHandStack().getItem() instanceof TridentItem) {
					stamina -= 10;
					stamina_wait = 20;
					
					if (this.isOnGround()) {
						float speed = 0.35f;
						if (this.isSprinting()) {
							speed = 1.25f;
							stamina -= 15;
							doAttack(true);
						}
						Vec3d move = new Vec3d(0, 0, speed).rotateY((float)Math.toRadians(-this.yaw));
						this.setVelocity(move.x, this.getVelocity().y, move.z);
					}
				}
			
			super.swingHand(hand, bl);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getMovementSpeed", cancellable = true)
	public void getMovementSpeed(CallbackInfoReturnable<Float> info) {
		if (this.handSwinging) { 
			if (this.getMainHandStack() != null)
				if (this.getMainHandStack().getItem() instanceof SwordItem)
					info.setReturnValue(0.0f);
		}
	}
	@Override
	public void tickHandSwing() {
		if (this.handSwinging) { 
			if (this.handSwingProgress < 1) {
				this.handSwingProgress += 0.15f;
				this.handSwingTicks++;
			} else {
				this.handSwingProgress = 0;
				this.handSwinging = false;
				this.handSwingTicks = 0;
			}
		}
	}
	
	@Shadow
	public void attack(Entity target) {
		
	}
	
	private void doAttack(boolean hit_players) {
		Vec3d move = new Vec3d(0, 0, 2).rotateY((float)Math.toRadians(-this.yaw));
		Vec3d b1 = new Vec3d(this.getPos().x + move.x, this.getPos().y, this.getPos().z + move.z);
		List<Entity> entities = world.getOtherEntities(this, new Box(b1.x - getWidth() * 0.5f, b1.y - getHeight() * 0.5f, b1.z - getWidth() * 0.5f, b1.x + getWidth() * 1.5f, b1.y + getHeight() * 1.5f, b1.z + getWidth() * 1.5f));
		for(Entity e : entities) {
			if (e instanceof PlayerEntity && !hit_players) continue;
			e.handleAttack(this);
			this.attack(e);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(10);
		
		if (this.handSwingProgress > 0.4 && this.getMainHandStack() != null) {
			if (this.getMainHandStack().getItem() instanceof ToolItem ||
					this.getMainHandStack().getItem() instanceof SwordItem ||
					this.getMainHandStack().getItem() instanceof TridentItem) {
				doAttack(this.handSwingProgress > 0.6f);
			}
			
		}
		
		if (this.stamina_recovery) { 
			this.setSprinting(false);
		}
		
		if (this.isSprinting() && this.isOnGround() && this.getVelocity().length() > 0.1f) {
			this.stamina-=0.2f;
			stamina_wait = 20;
		}
		
		if (stamina_wait > 0) {
			stamina_wait--;
		} else {
			stamina++;
			if (stamina >= 100) {
				stamina = 100;
				stamina_recovery = false;
			}
		}
		if (stamina <= 0) { 
			stamina = 0;
			stamina_recovery = true;
		}
		
		if (weapon_wait > 0) {
			weapon_wait--;
		} else {
			weapon_wait = 0;
		}
		
		if (dodge_ticks > 0) {
			dodge_ticks--;
		} else {
			dodge_ticks = 0;
		}
		
		hungerManager.setFoodLevel((int)((stamina / 100) * 20));
	}
}

package kelvin.trewrite.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import kelvin.trewrite.registry.Initialization;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> {
	
	public PlayerEntityModelMixin(ModelPart root) {
		super(root);
	}

	@Shadow
	private List<ModelPart> parts;
	@Shadow
	private ModelPart leftSleeve;
	@Shadow
	private ModelPart rightSleeve;
	@Shadow
	private ModelPart leftPants;
	@Shadow
	private ModelPart rightPants;
	@Shadow
	private ModelPart jacket;
	@Shadow
	private ModelPart cloak;
	@Shadow
	private ModelPart ear;
	@Shadow
	private boolean thinArms;
		
	@Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
	public void setAngles(T player, float distance_traveled, float movement_speed, float ticks, float movement_tilt, float possibly_rotation_yaw, CallbackInfo info) {
		//System.out.println(distance_traveled + ", " + movement_speed + ", " + ticks + ", " + movement_tilt + ", " + possibly_rotation_yaw);
		
		if (!Initialization.player_strike_direction.containsKey(player.getDisplayName().asString())) {
			Initialization.player_strike_direction.put(player.getDisplayName().asString(), false);
		}
		
		if (!player.isSubmergedInWater() && (player.isOnGround() == false && player.isSprinting() && (player.fallDistance > 1 || player.getVelocity().y > 0) || player.fallDistance > 10)) {
			
			this.leftArm.pitch = (float)MathHelper.lerp(0.25f, this.leftArm.pitch, Math.toRadians(-15) + Math.sin(distance_traveled * 0.5f) * 0.25f);
			this.leftArm.roll = (float)MathHelper.lerp(0.25f, this.leftArm.roll, Math.toRadians(-70) + Math.cos(distance_traveled * 0.5f) * 0.25f);
			
			this.rightArm.pitch = (float)MathHelper.lerp(0.25f, this.rightArm.pitch, Math.toRadians(-15) - Math.sin(distance_traveled * 0.5f) * 0.25f);
			this.rightArm.roll = (float)MathHelper.lerp(0.25f, this.rightArm.roll, Math.toRadians(70) - Math.cos(distance_traveled * 0.5f) * 0.25f);
			
			if (this.leftLeg.pitch > 0) {
				this.leftLeg.pitch = (float)MathHelper.lerp(0.25f, this.leftLeg.pitch, Math.toRadians(70));
				this.rightLeg.pitch = (float)MathHelper.lerp(0.25f, this.rightLeg.pitch, -Math.toRadians(70));
			} else {
				this.leftLeg.pitch = (float)MathHelper.lerp(0.25f, this.leftLeg.pitch, -Math.toRadians(70));
				this.rightLeg.pitch = (float)MathHelper.lerp(0.25f, this.rightLeg.pitch, Math.toRadians(70));
			}
			
		} else {
			float multiplier = (player.isOnGround() || player.isSubmergedInWater()) ? 1 : 0.1f;
			if (!player.isSprinting()) {
				this.leftLeg.pitch = (float)MathHelper.lerp(0.25f * multiplier, this.leftLeg.pitch, Math.cos(distance_traveled * 0.5f));
				this.rightLeg.pitch = (float)MathHelper.lerp(0.25f * multiplier, this.rightLeg.pitch, -Math.cos(distance_traveled * 0.5f));
				
				this.leftArm.pitch = (float)MathHelper.lerp(0.25f * multiplier, this.leftArm.pitch, Math.cos(distance_traveled * 0.5f));
				this.rightArm.pitch = (float)MathHelper.lerp(0.25f * multiplier, this.rightArm.pitch, -Math.cos(distance_traveled * 0.5f));
				
			} else {
				this.leftLeg.pitch = (float)MathHelper.lerp(0.25f * multiplier, this.leftLeg.pitch, Math.cos(distance_traveled * 0.5f) * 2);
				this.rightLeg.pitch = (float)MathHelper.lerp(0.25f * multiplier, this.rightLeg.pitch, -Math.cos(distance_traveled * 0.5f) * 2);
				
				this.leftArm.pitch = (float)MathHelper.lerp(0.25f * multiplier, this.leftArm.pitch, Math.cos(distance_traveled * 0.5f) * 2);
				this.rightArm.pitch = (float)MathHelper.lerp(0.25f * multiplier, this.rightArm.pitch, -Math.cos(distance_traveled * 0.5f) * 2);
				
			}

			if (player.isOnGround() || player.isSubmergedInWater()) {
				this.rightArm.roll = (float)MathHelper.lerp(0.25f, this.rightArm.roll, -Math.abs(this.rightArm.pitch) * 0.1f);
				this.leftArm.roll = (float)MathHelper.lerp(0.25f, this.leftArm.roll, Math.abs(this.leftArm.pitch) * 0.1f);
			}
			
			this.leftLeg.pitch = (float)MathHelper.lerp(multiplier * (1.0f - movement_speed), this.leftLeg.pitch, 0);
			this.rightLeg.pitch = (float)MathHelper.lerp(multiplier * (1.0f - movement_speed), this.rightLeg.pitch, 0);
			this.rightArm.pitch = (float)MathHelper.lerp(multiplier * (1.0f - movement_speed), this.rightArm.pitch, 0);
			this.leftArm.pitch = (float)MathHelper.lerp(multiplier * (1.0f - movement_speed), this.leftArm.pitch, 0);
		}
		if (player.handSwinging) { 
			this.leftLeg.pitch = (float)MathHelper.lerp((1.0f - movement_speed), this.leftLeg.pitch, (float)Math.toRadians(15));

			this.leftArm.pitch = (float)MathHelper.lerp((1.0f - movement_speed), this.leftArm.pitch, (float)Math.toRadians(15));
			this.leftArm.roll = (float)MathHelper.lerp((1.0f - movement_speed), this.leftArm.roll, (float)Math.toRadians(-15));
		}
		if (player.handSwinging) {
			
			if (player.getMainHandStack() != null) {
				Item item = player.getMainHandStack().getItem();
				
				if (item instanceof SwordItem || item instanceof TridentItem) {
					float frame = (float)Math.floor(player.handSwingProgress * 10);

					this.rightArm.yaw = (float)Math.toRadians(180);

					if (Initialization.player_strike_direction.get(player.getDisplayName().asString())) {
						//swing from the left
						
						if (frame >= 0) {
							this.rightArm.roll = (float)Math.toRadians(270);
							this.rightArm.pitch = (float)MathHelper.lerp(frame / 3.0f, (float)Math.toRadians(45), (float)Math.toRadians(30));							
						}
						if (frame >= 3) {
							this.rightArm.roll = (float)Math.toRadians(270);
							this.rightArm.pitch = (float)MathHelper.lerp((frame - 3) / 3.0f, (float)Math.toRadians(30), (float)Math.toRadians(180));
						}
						if (frame >= 7) {
							this.rightArm.roll = (float)Math.toRadians(270);
							this.rightArm.pitch = (float)Math.toRadians(180);
						}
					} else {
						//swing from the right
							
						if (frame >= 0) {
							//preparing to swing
							this.rightArm.roll = (float)Math.toRadians(115);
							this.rightArm.pitch = (float)MathHelper.lerp(frame / 3.0f, (float)Math.toRadians(-25), (float)Math.toRadians(-35));
						}
						
						if (frame >= 3) {
							this.rightArm.roll = (float)MathHelper.lerp((frame - 3) / 4.0f, (float)Math.toRadians(115), (float)Math.toRadians(130));
							this.rightArm.pitch = (float)MathHelper.lerp((frame - 3) / 4.0f, (float)Math.toRadians(-35), (float)Math.toRadians(160));
						}
						
						if (frame >= 7) {
							this.rightArm.roll = (float)Math.toRadians(130);
							this.rightArm.pitch = (float)Math.toRadians(160);
						}
						//finished swinging
					}
					
					
				}
				if (item instanceof AxeItem) {
					if (Initialization.player_strike_direction.get(player.getDisplayName().asString())) {
						//swing from the left
						this.rightArm.roll = (float)(Math.toRadians(90)) + (float)Math.cos(ticks * 0.5f) * 0.1f;
						this.rightArm.pitch = (float)Math.toRadians(-115 * (1.0f - player.handSwingProgress)) + (float)Math.sin(ticks * 0.5f) * 0.1f;
						
					} else {
						//swing from the right
						this.rightArm.roll = (float)(Math.toRadians(90)) + (float)Math.cos(ticks * 0.5f) * 0.1f;
						this.rightArm.pitch = (float)Math.toRadians(-180 * (1.0f - player.handSwingProgress) + 180) + (float)Math.sin(ticks * 0.5f) * 0.1f;
						this.rightArm.yaw = (float)Math.toRadians(180);
					}
				}
				if (item instanceof PickaxeItem) {
					this.rightArm.pitch = (float)Math.toRadians(-180 * (1.0f - player.handSwingProgress));
				}
				if (item instanceof ShovelItem || item instanceof HoeItem) {
					this.rightArm.pitch = (float)Math.toRadians(-115 * player.handSwingProgress);
				}
			}
		} else {
			this.rightArm.yaw = 0;
			this.leftArm.yaw = 0;
			if (player.lastHandSwingProgress > 0) {
				Initialization.player_strike_direction.replace(player.getDisplayName().asString(), !Initialization.player_strike_direction.get(player.getDisplayName().asString()));
			}
		}
		
		
		this.head.pitch = (float)Math.toRadians(player.pitch);
		this.head.yaw = (float)Math.toRadians(movement_tilt + 45 * ((player.handSwinging && !player.isSprinting()) ? 1 : 0));
		
		
		this.leftPants.copyTransform(this.leftLeg);
		this.rightPants.copyTransform(this.rightLeg);
		this.leftSleeve.copyTransform(this.leftArm);
		this.rightSleeve.copyTransform(this.rightArm);
		this.jacket.copyTransform(this.body);
		if (player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
			if (player.isInSneakingPose()) {
				this.cloak.pivotZ = 1.4F;
				this.cloak.pivotY = 1.85F;
			} else {
				this.cloak.pivotZ = 0.0F;
				this.cloak.pivotY = 0.0F;
			}
		} else if (player.isInSneakingPose()) {
			this.cloak.pivotZ = 0.3F;
			this.cloak.pivotY = 0.8F;
		} else {
			this.cloak.pivotZ = -1.1F;
			this.cloak.pivotY = -0.85F;
		}
		
		info.cancel();
	}
	
	@Inject(at = @At("HEAD"), method = "setArmAngle", cancellable = true)
	public void setArmAngle(Arm arm, MatrixStack matrices, CallbackInfo info) {
		
		//info.cancel();
	}
}

package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;

@Mixin(Camera.class)
public class CameraMixin {
	@Shadow
	protected void setRotation(float yaw, float pitch) {
		
	}

	@Shadow
	protected void setPos(double x, double y, double z) {
		
	}
	
	@Shadow
	protected void setPos(Vec3d pos) {
		
	}
	
	@Shadow
	private double clipToSpace(double desiredCameraDistance) {
		return 0;
	}
	
	@Shadow
	protected void moveBy(double x, double y, double z) {
		
	}
	
	@Shadow
	float yaw;
	@Shadow
	float pitch;
	@Shadow
	private boolean thirdPerson;
	@Shadow
	private float cameraY;
	@Shadow
	private float lastCameraY;
	@Shadow
	private boolean ready;
	@Shadow
	private BlockView area;
	@Shadow
	private Entity focusedEntity;
	
	@Inject(at = @At("HEAD"), method = "update", cancellable = true)
	public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView,
			float tickDelta, CallbackInfo info) {
		
		
		
		if (thirdPerson) {
			this.ready = true;
			this.area = area;
			this.focusedEntity = focusedEntity;
			this.thirdPerson = thirdPerson;
			this.setRotation(focusedEntity.getYaw(tickDelta), focusedEntity.getPitch(tickDelta));
			this.setPos(MathHelper.lerp((double) tickDelta, focusedEntity.prevX, focusedEntity.getX()),
					MathHelper.lerp((double) tickDelta, focusedEntity.prevY, focusedEntity.getY())
							+ (double) MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY),
					MathHelper.lerp((double) tickDelta, focusedEntity.prevZ, focusedEntity.getZ()));
			if (inverseView) {
				this.setRotation(this.yaw + 180.0F, -this.pitch);
			}

			this.moveBy(-this.clipToSpace(8.0D), 0.0D, 0.0D);
			info.cancel();
		}

	}
}

package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import kelvin.trewrite.registry.Initialization;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
	
	@Inject(at = @At("HEAD"), method = "scale", cancellable = true)
	public void scale(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, CallbackInfo info) {
		float g = (2.6f / 1.6f);
		matrixStack.scale(0.9375F * g, 0.9375F * g, 0.9375F * g);
		info.cancel();
	}
	
	@Inject(at = @At("HEAD"), method = "setupTransforms", cancellable = true)
	protected void setupTransforms(AbstractClientPlayerEntity player, MatrixStack matrixStack,
			float f, float g, float h, CallbackInfo info) {
		
		Vec3f rotation = new Vec3f(0, 0, -1);
		
		float avg_vel = (float)(Math.abs(player.getVelocity().x) + Math.abs(player.getVelocity().z)) * 5;
		if (avg_vel > 1) avg_vel = 1;
		
		matrixStack.multiply(rotation.getDegreesQuaternion((float)(player.getVelocity().x) * 50.0f * avg_vel));
		
		rotation = new Vec3f(1, 0, 0);
		
		matrixStack.multiply(rotation.getDegreesQuaternion((float)(player.getVelocity().z) * 50.0f * avg_vel));
		

		if (player.handSwinging == true && !player.isInSwimmingPose() && !player.isSprinting()) {
			rotation = new Vec3f(0, 1, 0);
			matrixStack.multiply(rotation.getDegreesQuaternion(45));
		}
		
	}
}

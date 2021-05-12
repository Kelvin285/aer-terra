package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;

@Mixin(ZombieEntityRenderer.class)
public abstract class ZombieEntityRendererMixin extends MobEntityRenderer<ZombieEntity, BipedEntityModel<ZombieEntity>> {
	public ZombieEntityRendererMixin(Context context, BipedEntityModel<ZombieEntity> entityModel, float f) {
		super(context, entityModel, f);
	}

	@Override
	public void scale(ZombieEntity zombieEntity, MatrixStack matrixStack, float f) {
		float g = (2.6f / 1.6f);
		matrixStack.scale(0.9375F * g, 0.9375F * g, 0.9375F * g);
	}
}

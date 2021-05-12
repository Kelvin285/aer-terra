package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Shadow
	private RecipeBookWidget recipeBook;
	@Shadow
	private static Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");
	@Shadow
	private float mouseX;
	@Shadow
	private float mouseY;
	@Shadow
	private boolean open;
	@Shadow
	private boolean narrow;
	@Shadow
	private boolean mouseDown;
	
	@Inject(at = @At("HEAD"), method = "init", cancellable = true)
	protected void init(CallbackInfo info) {
		if (this.client.interactionManager.hasCreativeInventory()) {
			return;
		} else {
			super.init();
			this.narrow = this.width < 379;
			this.recipeBook.initialize(this.width, this.height, this.client, this.narrow,
					(AbstractRecipeScreenHandler) this.handler);
			this.open = true;
			this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
			this.children.add(this.recipeBook);
			this.setInitialFocus(this.recipeBook);
			
			//this is what is being changed (the recipe book button location)
			
			this.addButton(new TexturedButtonWidget(this.x + 180, this.height / 2 + 18 * 3, 20, 18, 0, 0, 19,
					RECIPE_BUTTON_TEXTURE, (buttonWidget) -> {
						this.recipeBook.reset(this.narrow);
						this.recipeBook.toggleOpen();
						this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
						((TexturedButtonWidget) buttonWidget).setPos(this.x + 180, this.height / 2 + 18 * 3);
						this.mouseDown = true;
					}));
			info.cancel();
		}
	}
	
	private static boolean skip = false;
	@Inject(at = @At("HEAD"), method = "drawEntity", cancellable = true)
	private static void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity, CallbackInfo info) {
		float f = (float) Math.atan((double) (mouseX / 40.0F));
		float g = (float) Math.atan((double) (mouseY / 40.0F));
		MatrixStack matrixStack = RenderSystem.getModelViewStack();
		matrixStack.push();
		matrixStack.translate((double) x, (double) y, 1050.0D);
		matrixStack.scale(1.6F / 2.6F, 1.6F / 2.6F, -1.6F / 2.6F);
		RenderSystem.applyModelViewMatrix();
		MatrixStack matrixStack2 = new MatrixStack();
		matrixStack2.translate(0.0D, 0.0D, 1000.0D);
		matrixStack2.scale((float) size, (float) size, (float) size);
		Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
		Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0F);
		quaternion.hamiltonProduct(quaternion2);
		matrixStack2.multiply(quaternion);
		float h = entity.bodyYaw;
		float i = entity.yaw;
		float j = entity.pitch;
		float k = entity.prevHeadYaw;
		float l = entity.headYaw;
		entity.bodyYaw = 180.0F + f * 20.0F;
		entity.yaw = 180.0F + f * 40.0F;
		entity.pitch = -g * 20.0F;
		entity.headYaw = entity.yaw;
		entity.prevHeadYaw = entity.yaw;
		DiffuseLighting.method_34742();
		EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
		quaternion2.conjugate();
		entityRenderDispatcher.setRotation(quaternion2);
		entityRenderDispatcher.setRenderShadows(false);
		Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		RenderSystem.runAsFancy(() -> {
			entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack2, immediate, 15728880);
		});
		immediate.draw();
		entityRenderDispatcher.setRenderShadows(true);
		entity.bodyYaw = h;
		entity.yaw = i;
		entity.pitch = j;
		entity.prevHeadYaw = k;
		entity.headYaw = l;
		matrixStack.pop();
		RenderSystem.applyModelViewMatrix();
		DiffuseLighting.enableGuiDepthLighting();
		info.cancel();
	}
	
}

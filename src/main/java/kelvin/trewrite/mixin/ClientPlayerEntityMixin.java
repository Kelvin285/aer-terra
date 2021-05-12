package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
	public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}
	@Shadow
	private MinecraftClient client;
	@Shadow
	private int ticksLeftToDoubleTapSprint;
	@Shadow
	private Input input;
	
	@Shadow
	private boolean isWalking() {
		return false;
	}
	
	
	@Inject(at = @At("HEAD"), method = "tickMovement", cancellable = true)
	public void tickMovement(CallbackInfo info) {
		boolean bl = this.input.jumping;
		boolean bl2 = this.input.sneaking;
		boolean bl3 = this.isWalking();
		
		boolean bl5 = true;
		if ((this.onGround || this.isSubmergedInWater()) && !bl2 && !bl3 && this.isWalking() && !this.isSprinting()
				&& bl5 && !this.isUsingItem() && !this.hasStatusEffect(StatusEffects.BLINDNESS)) {
			if (this.ticksLeftToDoubleTapSprint <= 0 && !this.client.options.keySprint.isPressed()) {
				this.ticksLeftToDoubleTapSprint = 7;
			} else {
				this.setSprinting(true);
			}
		}
	}
}

package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

@Mixin(PlayerInteractEntityC2SPacket.class)
public class PlayerInteractEntityC2SPacketMixin {
	
	@Shadow
	public static PlayerInteractEntityC2SPacket interact(Entity entity, boolean playerSneaking, Hand hand) {
		return null;
	}
	
	@Inject(at = @At("HEAD"), method = "attack", cancellable = true)
	private static void attack(Entity entity, boolean playerSneaking, CallbackInfoReturnable<PlayerInteractEntityC2SPacket> info) {
		info.setReturnValue(interact(entity, playerSneaking, Hand.MAIN_HAND));
	}
}

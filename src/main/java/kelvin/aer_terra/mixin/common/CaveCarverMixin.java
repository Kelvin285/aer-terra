package kelvin.aer_terra.mixin.common;

import net.minecraft.world.gen.carver.CaveCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CaveCarver.class)
public class CaveCarverMixin {

    @Inject(at = @At("HEAD"), method = "isPositionExcluded", cancellable = true)
    private static void isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, double floorY, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(false);
    }
}

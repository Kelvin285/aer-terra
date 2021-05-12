package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
@Mixin(ConcretePowderBlock.class)
public class ConcretePowderBlockMixin {
	@Inject(at = @At("RETURN"), method = "shouldHarden", cancellable = true)
	private static void shouldHarden(BlockView world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(false);
	}
}

package kelvin.trewrite.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.trewrite.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.Material;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.world.WorldView;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {
	
	@Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
	public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		Iterator var4 = Type.HORIZONTAL.iterator();

		Direction direction;
		Material material;
		do {
			if (!var4.hasNext()) {
				BlockState blockState2 = world.getBlockState(pos.down());
				info.setReturnValue((blockState2.isOf(Blocks.CACTUS) || blockState2.isOf(Blocks.SAND)
						|| blockState2.isOf(Blocks.RED_SAND) || blockState2.isOf(BlockRegistry.CRIMSON_SAND))
						&& !world.getBlockState(pos.up()).getMaterial().isLiquid());
			}

			direction = (Direction) var4.next();
			BlockState blockState = world.getBlockState(pos.offset(direction));
			material = blockState.getMaterial();
		} while (!material.isSolid() && !world.getFluidState(pos.offset(direction)).isIn(FluidTags.LAVA));

		info.setReturnValue(false);
	}
}

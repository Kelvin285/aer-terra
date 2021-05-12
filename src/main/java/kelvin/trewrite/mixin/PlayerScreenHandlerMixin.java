package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingInventory> {

	public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	public void Constructor(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo info) {
		this.slots.remove(this.slots.size() - 1);
		int n = 44;
		for (int i = 0; i < 3; i++) {
			this.addSlot(new Slot(inventory, n++, 77, 8 + i * 18)); // armor accessory slots
		}
		for (int i = 0; i < 4; i++) {
			this.addSlot(new Slot(inventory, n++, 98 + i * 18, 62)); // accessory slots
		}
		this.addSlot(new Slot(inventory, 43, 77, 62)); // accessory slots
	}
}

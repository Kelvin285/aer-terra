package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen.CreativeScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

@Mixin(CreativeScreenHandler.class)
public abstract class CreativeScreenHandlerMixin extends ScreenHandler {

	public CreativeScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	public void Constructor(PlayerEntity playerEntity, CallbackInfo info) {
		PlayerInventory playerInventory = playerEntity.getInventory();
		/*
		this.addSlot(new Slot(playerInventory, 44, 152, 15)); // armor accessory slot
		this.addSlot(new Slot(playerInventory, 45, 152 + 18, 15)); // armor accessory slot
		this.addSlot(new Slot(playerInventory, 46, 152, 15 + 18)); // armor accessory slot
		
		this.addSlot(new Slot(playerInventory, 47, 173, 36)); // accessory slots
		this.addSlot(new Slot(playerInventory, 48, 173, 36 + 18)); // accessory slots
		this.addSlot(new Slot(playerInventory, 49, 173, 36 + 18 * 2)); // accessory slots
		this.addSlot(new Slot(playerInventory, 50, 173, 36 + 18 * 3)); // accessory slots
		*/
	}
}

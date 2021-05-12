package kelvin.trewrite.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory, Nameable {

	@Shadow
	private DefaultedList<ItemStack> main;
	@Shadow
	private DefaultedList<ItemStack> armor;
	@Shadow
	private DefaultedList<ItemStack> offHand;
	@Shadow
	private List<DefaultedList<ItemStack>> combinedInventory;
	@Shadow
	private int selectedSlot;
	@Shadow
	private PlayerEntity player;
	@Shadow
	private int changeCount;
	
	private DefaultedList<ItemStack> accessory;
	
	@Inject(at = @At("RETURN"), method = "<init>")
	public void constructor(PlayerEntity player, CallbackInfo info) {
		this.main = DefaultedList.ofSize(36, ItemStack.EMPTY);
		this.armor = DefaultedList.ofSize(7, ItemStack.EMPTY);
		this.offHand = DefaultedList.ofSize(1, ItemStack.EMPTY);
		this.accessory = DefaultedList.ofSize(7, ItemStack.EMPTY);
		this.combinedInventory = ImmutableList.of(this.main, this.armor, this.offHand, this.accessory);
		this.player = player;
	}
	
	@Inject(at = @At("HEAD"), method = "serialize", cancellable = true)
	public void serialize(ListTag tag, CallbackInfoReturnable<ListTag> info) {
		int k;
		CompoundTag compoundTag3;
		for (k = 0; k < this.accessory.size(); ++k) {
			if (!((ItemStack) this.accessory.get(k)).isEmpty()) {
				compoundTag3 = new CompoundTag();
				compoundTag3.putByte("Slot", (byte) (k + 200));
				((ItemStack) this.accessory.get(k)).writeNbt(compoundTag3);
				tag.add(compoundTag3);
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "deserialize", cancellable = true)
	public void deserialize(ListTag tag, CallbackInfo info) {
		this.accessory.clear();

		for (int i = 0; i < tag.size(); ++i) {
			CompoundTag compoundTag = tag.getCompound(i);
			int j = compoundTag.getByte("Slot") & 255;
			ItemStack itemStack = ItemStack.fromNbt(compoundTag);
			if (!itemStack.isEmpty()) {
				if (j >= 200 && j < this.accessory.size() + 200) {
					this.accessory.set(j - 200, itemStack);
				}
			}
		}

	}
	
}

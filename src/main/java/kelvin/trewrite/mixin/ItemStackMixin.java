package kelvin.trewrite.mixin;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	public int level = 1;
	public int xp = 0;
	
	public int AddXp(int xp) { // returns the number of levels an item increased by
		this.xp += xp;
		int up = 0;
		while (this.xp >= level * 10) {
			this.xp -= level * 10;
			level++;
			up++;
		}
		if (xp < 0) xp = 0;
		return up;
	}
	
	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V")
	private void Constructor(CompoundTag tag, CallbackInfo info) {
		level = tag.getInt("Level");
		xp = tag.getInt("Xp");
		if (level == 0) level = 1;
	}
	
	@Inject(at = @At("HEAD"), method = "writeNbt", cancellable = true)
	public void writeNbt(CompoundTag tag, CallbackInfoReturnable<CompoundTag> info) {
		tag.putInt("Level", level);
		tag.putInt("Xp", xp);
	}
	
	@Shadow
	public Item getItem() {
		return null;
	}
	
	@Environment(EnvType.CLIENT)
	@Inject(at = @At("RETURN"), method = "getTooltip", cancellable = true)
	public void getTooltip(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> info) {
		if (this.getItem() instanceof ToolItem || this.getItem() instanceof RangedWeaponItem || this.getItem() instanceof TridentItem) {
			List<Text> list = info.getReturnValue();
			//list.add((new TranslatableText("LV " + level)));
			//list.add((new TranslatableText("XP " + xp + "/" + (level * 10))));
			//info.getReturnValue().add(new Text());
		}
	}
}

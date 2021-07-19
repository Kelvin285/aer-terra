package kelvin.trewrite.mixin;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.trewrite.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.biome.Biome;

@Mixin(Biome.class)
public class BiomeMixin {
	
	private float sky_r = 0;
	private float sky_g = 0;
	private float sky_b = 0;
	
	private int ticks = 0;
	
	@Inject(at = @At("HEAD"), method = "getFogColor", cancellable = true)
	public void getFogColor(CallbackInfoReturnable<Integer> info) {
		double r = sky_r * 1.2f;
		double g = sky_g * 1.2f;
		double b = sky_b * 1.2f;
		if (r > 255) r = 255;
		if (g > 255) g = 255;
		if (b > 255) b = 255;
		info.setReturnValue(new Color((int)r, (int)g, (int)b).getRGB());
	}
	
	@Inject(at = @At("RETURN"), method = "getSkyColor", cancellable = true)
	public void getSkyColor(CallbackInfoReturnable<Integer> info) {
		ticks++;
		if (ticks > 100) {
			int color = info.getReturnValue();
			if (color < 0) color = 0;
			if (color > 0xffffff) color = 0xffffff;
			Color c = new Color(color);
			float r = c.getRed();
			float g = c.getGreen();
			float b = c.getBlue();
			
			int corruption = 0;
			int crimson = 0;
			
			MinecraftClient client = MinecraftClient.getInstance();
			
			if (client == null) return;
			
			Entity entity = client.getCameraEntity();
			if (entity != null) {
				BlockPos.Mutable pos = new BlockPos.Mutable(entity.getX(), entity.getY(), entity.getZ());
				int X = pos.getX();
				int Y = pos.getY();
				int Z = pos.getZ();
				if (client.world != null)
				for (int x = -25; x < 25; x++) {
					for (int y = -25; y < 25; y++) {
						for (int z = -25; z < 25; z++) {
							pos.set(X + x, Y + y, Z + z);
							if (!client.world.isChunkLoaded(pos)) continue;
							Block block = client.world.getBlockState(pos).getBlock();
							
							if (block == BlockRegistry.EBON_GRASS || block == BlockRegistry.EBON_STONE || block == BlockRegistry.EBON_SAND
									|| block == BlockRegistry.EBON_SANDSTONE) {
								corruption++;
							}
							else if (block == BlockRegistry.CRIMSON_GRASS || block == BlockRegistry.CRIMSON_STONE || block == BlockRegistry.CRIMSON_SAND ||
									block == BlockRegistry.CRIMSON_SANDSTONE) {
								crimson++;
							}
						}
					}
				}
			}
			
			if (corruption > 50) {
				r *= 0.5f;
				g *= 0;
				b *= 0.5f;
			}
			if (crimson > 50) {
				g *= 0;
				b *= 0;
			}
			
			sky_r = r;
			sky_g = g;
			sky_b = b;
			if (sky_r > 255) sky_r = 255;
			if (sky_r < 0) sky_r = 0;
			if (sky_g > 255) sky_g = 255;
			if (sky_g < 0) sky_g = 0;
			if (sky_b > 255) sky_b = 255;
			if (sky_b < 0) sky_b = 0;
			ticks = 0;
		}
		info.setReturnValue(new Color((int)sky_r, (int)sky_g, (int)sky_b).getRGB());
		//return this.effects.getSkyColor();
	}
}

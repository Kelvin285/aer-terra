package kelvin.aer_terra.main.resources;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.item.ItemStack;

public class ItemHelper {
	public static int GetLevel(ItemStack stack) {
		try {
			return (int)stack.getClass().getDeclaredField("level").get(stack);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int GetExperience(ItemStack stack) {
		try {
			return (int)stack.getClass().getDeclaredField("xp").get(stack);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int AddExperience(ItemStack stack, int xp) {
		try {
			return (int)stack.getClass().getDeclaredMethod("AddXp", int.class).invoke(stack, xp);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

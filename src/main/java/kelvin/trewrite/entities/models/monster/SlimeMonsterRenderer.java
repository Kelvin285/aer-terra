package kelvin.trewrite.entities.models.monster;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.SlimeEntityRenderer;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Identifier;

public class SlimeMonsterRenderer extends SlimeEntityRenderer {

	private Identifier texture;
	
	public SlimeMonsterRenderer(Context context, String texture) {
		super(context);
		this.texture = new Identifier(texture);
	}
	
	public Identifier getTexture(SlimeEntity entity) {
		return texture;
	}
	
}

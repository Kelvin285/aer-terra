package kelvin.trewrite.entities.models.trees;

import kelvin.trewrite.entities.models.CustomModel;
import kelvin.trewrite.entities.trees.BasicTreeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BasicTreeRenderer extends LivingEntityRenderer<BasicTreeEntity, BasicTreeModel> {

	private static final Identifier TEXTURE = new Identifier("trewrite:textures/entities/trees/tree.png");
	
	public BasicTreeRenderer(Context context) {
		super(context, new BasicTreeModel(128, 170), 0.3f);
	}

	@Override
	public Identifier getTexture(BasicTreeEntity entity) {
		return TEXTURE;
	}
}
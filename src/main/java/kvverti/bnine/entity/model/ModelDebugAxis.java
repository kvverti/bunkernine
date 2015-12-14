package kvverti.bnine.entity.model;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class ModelDebugAxis extends ModelBase {

	private int texWidth = 64;
	private int texHeight = 32;

	private ModelRenderer x;
	private ModelRenderer y;
	private ModelRenderer z;

	public ModelDebugAxis() {

		x = new ModelRenderer(this, 0, 17);
		x.addBox(0.0f, 0.0f, 0.0f, 16, 1, 1);
		x.setTextureSize(texWidth, texHeight);

		y = new ModelRenderer(this, 0, 0);
		y.addBox(0.0f, 0.0f, 0.0f, 1, 16, 1);
		y.setTextureSize(texWidth, texHeight);

		z = new ModelRenderer(this, 4, 0);
		z.addBox(0.0f, 0.0f, 0.0f, 1, 1, 16);
		z.setTextureSize(texWidth, texHeight);
	}

	@Override
	public void render(Entity entity, float time, float distance, float p4, float yaw, float pitch, float p7) {

		x.render(p7);
		y.render(p7);
		z.render(p7);
	}
}
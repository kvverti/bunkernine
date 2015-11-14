package kvverti.bnine.entity.model.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import kvverti.bnine.entity.model.ModelDebugAxis;
import kvverti.bnine.init.Meta;

@SideOnly(Side.CLIENT)
public class RenderDebugAxis extends Render {

	private static final ResourceLocation AXIS = new ResourceLocation(Meta.ID + ":textures/entity/axis.png");
	private ModelBase model = new ModelDebugAxis();

	public RenderDebugAxis(RenderManager manager) {

		super(manager);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float rotation, float partialTicks) {

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		bindEntityTexture(entity);
		model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, rotation, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return AXIS;
	}
}
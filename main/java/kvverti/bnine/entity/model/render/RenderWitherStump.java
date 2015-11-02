package kvverti.bnine.entity.model.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import kvverti.bnine.entity.model.ModelWitherStump;
import kvverti.bnine.entity.EntityWitherStump;
import kvverti.bnine.init.Meta;

@SideOnly(Side.CLIENT)
public class RenderWitherStump extends RenderLiving {

	private static final ResourceLocation stumpTexture = new ResourceLocation(Meta.ID + ":textures/entity/witherStump.png");

	public RenderWitherStump(RenderManager manager, ModelBase model, float shadowSize) {

		super(manager, model, shadowSize);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float partialTicks) {

		GlStateManager.translate(0.0f, 0.2578125f, 0.0f);

		preRenderCallback((EntityWitherStump) entity, partialTicks);
	}

	public void preRenderCallback(EntityWitherStump entity, float partialTicks) {

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return stumpTexture;
	}
}
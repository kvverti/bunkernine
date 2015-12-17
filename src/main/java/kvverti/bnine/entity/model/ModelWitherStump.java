package kvverti.bnine.entity.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import kvverti.bnine.entity.EntityWitherStump;

@SideOnly(Side.CLIENT)
public class ModelWitherStump extends ModelNine {

	private final ModelRenderer base;
	private final ModelRenderer node;

	private final float[] spinAnim = {
		  0.0f,   30.0f,   60.0f,  90.0f,  120.0f, 150.0f, 180.0f,
			-150.0f, -120.0f, -90.0f, -60.0f,  -30.0f,  -0.0f
	};

	public ModelWitherStump() {

		super(64, 32);

		base = new ModelRenderer(this, 0, 8);
		base.addBox(-4.0f, 16.0f, -4.0f, 8, 4, 8);
		base.setTextureSize(texWidth, texHeight);

		node = new ModelRenderer(this, 0, 0);
		node.addBox(-2.0f, 14.0f, -2.0f, 4, 4, 4);
		node.setTextureSize(texWidth, texHeight);
	}

	@Override
	public void setRotationAngles(float time, float distance, float roll, float yaw, float pitch, float scale, Entity entity) {

		int spinIndex = getRandomCounter(entity) % 400;
		node.rotateAngleY = spinIndex < spinAnim.length ? toRadians(spinAnim[spinIndex]) : 0.0f;
	}

	@Override
	public void render(Entity entity, float time, float distance, float roll, float yaw, float pitch, float scale) {

		render((EntityWitherStump) entity, time, distance, roll, yaw, pitch, scale);
	}

	public void render(EntityWitherStump entity, float time, float distance, float roll, float yaw, float pitch, float scale) {

		setRotationAngles(time, distance, roll, yaw, pitch, scale, entity);
		base.render(scale);
		node.render(scale);
	}
}
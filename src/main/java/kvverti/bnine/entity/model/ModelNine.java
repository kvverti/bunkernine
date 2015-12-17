package kvverti.bnine.entity.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public abstract class ModelNine extends ModelBase {

	protected final int texWidth;
	protected final int texHeight;

	protected ModelNine(int width, int height) {

		texWidth = width;
		texHeight = height;
	}

	protected int getRandomCounter(Entity entity) {

		return MathHelper.abs_int(entity.ticksExisted + entity.getUniqueID().hashCode());
	}

	protected float toRadians(float degrees) {

		return (degrees * (float) Math.PI) / 180.0f;
	}

	protected void testRotY(ModelRenderer model) {

		model.rotateAngleY += toRadians(0.5f);
	}

	protected void testRotX(ModelRenderer model) {

		model.rotateAngleX += toRadians(0.5f);
	}

	protected void testRotZ(ModelRenderer model) {

		model.rotateAngleZ += toRadians(0.5f);
	}
}
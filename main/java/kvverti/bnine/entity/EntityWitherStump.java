package kvverti.bnine.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import kvverti.bnine.util.ClassID;

@ClassID("WitherStump")
public class EntityWitherStump extends EntityLiving {

	public EntityWitherStump(World world) {

		super(world);
		setSize(0.5f, 0.5f);
	}

	@Override
	protected boolean canDespawn() {

		return false;
	}

	@Override
	protected void applyEntityAttributes() {

		super.applyEntityAttributes();
	}
}
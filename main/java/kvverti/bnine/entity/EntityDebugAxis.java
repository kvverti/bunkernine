package kvverti.bnine.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import kvverti.bnine.util.ClassID;

@ClassID("DebugAxis")
public class EntityDebugAxis extends Entity {

	public EntityDebugAxis(World world) {

		super(world);
		setSize(1.0f, 1.0f);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) { }

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) { }

	@Override
	public void entityInit() { }

	@Override
	public boolean canBeCollidedWith() {

		return !this.isDead;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		setDead();
		return false;
	}
}
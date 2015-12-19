package kvverti.bnine.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.AxisAlignedBB;

public class BlockNineRope extends BlockNine {

	public static final IProperty UP = PropertyBool.create("up");
	public static final IProperty DOWN = PropertyBool.create("down");

	public BlockNineRope(String id, Material material) {

		super(id, material);
		setIsOpaqueCube(false);
		setIsFullCube(false);
		setLightOpacity(0);
		setDefaultState(blockState.getBaseState().withProperty(UP, false).withProperty(DOWN, false));
	}

	@Override
	public BlockState createBlockState() {

		return new BlockState(this, UP, DOWN);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {

		boolean up = world.getBlockState(pos.up()).getBlock() == this;
		boolean down = world.getBlockState(pos.down()).getBlock() == this;

		state = state.withProperty(UP, up).withProperty(DOWN, down);
		return state;
	}

	@Override
	public boolean isLadder(IBlockAccess world, BlockPos pos, EntityLivingBase entity) {

		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) {

		Block above = world.getBlockState(pos.up()).getBlock();
		if(above != this && !above.isOpaqueCube()) {

			dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {

		float y = world.getBlockState(pos.down()).getBlock() == this ? 0.0f : 0.5f;
		setBlockBounds(0.4375f, y, 0.4375f, 0.5625f, 1.0f, 0.5625f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {

		setBlockBoundsBasedOnState(world, pos);
		return super.getCollisionBoundingBox(world, pos, state);
	}
}
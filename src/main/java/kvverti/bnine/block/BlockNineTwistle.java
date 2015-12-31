package kvverti.bnine.block;

import java.util.Random;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNineTwistle extends BlockNine implements IPlantable {

	public static final IProperty AGE = PropertyInteger.create("age", 0, 15);

	public BlockNineTwistle(String id) {

		super(id, Material.plants);
		useNeighborBrightness = true;
		setTickRandomly(true);
		setBlockLayer("cutout_mipped");
		setLightOpacity(0);
		setIsOpaqueCube(false);
		setIsFullCube(false);
		setBlockBounds(0.21875f, 0.0f, 0.21875f, 0.78125f, 1.0f, 0.78125f);
		setLightLevel(0.4375f);
		setDefaultState(
			blockState.getBaseState()
			.withProperty(AGE, 0)
			.withProperty(BlockNineRope.UP, false)
			.withProperty(BlockNineRope.DOWN, false)
		);
	}

	@Override
	public BlockState createBlockState() {

		return new BlockState(this, AGE, BlockNineRope.DOWN, BlockNineRope.UP);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		return (Integer) state.getValue(AGE);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {

		return state.withProperty(BlockNineRope.UP, world.getBlockState(pos.up()).getBlock() == this)
			.withProperty(BlockNineRope.DOWN, world.getBlockState(pos.down()).getBlock() == this);
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {

		//how many blocks are below this one? Max height is five blocks.
		for(int i = 1; world.getBlockState(pos.down(i)).getBlock() == this; i++) {

			if(i >= 4) return;
		}

		if(world.isAirBlock(pos.up())) {

			int age = (int) world.getBlockState(pos).getValue(AGE);
			if(age == 15) {

				world.setBlockState(pos.up(), getDefaultState().withProperty(BlockNineRope.DOWN, true));
				world.setBlockState(pos, state.withProperty(AGE, 0));

			} else world.setBlockState(pos, state.withProperty(AGE, age + 1));
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) {

		Block below = world.getBlockState(pos.down()).getBlock();
		if(below != this && !below.canSustainPlant(world, pos, EnumFacing.UP, this)) {

			dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {

		return EnumPlantType.Plains;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {

		return getDefaultState();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {

		return null;
	}
}
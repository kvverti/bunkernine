package kvverti.bnine.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import kvverti.bnine.util.Logger;

public class BlockNineSilicon extends BlockNine {

	public static final IProperty POWER = PropertyInteger.create("power", 0, 4);

	public BlockNineSilicon(String id) {

		super(id, Material.iron, "pickaxe", 0);
		setTickRandomly(true);
		setDefaultState(blockState.getBaseState().withProperty(POWER, 0));
	}

	@Override
	public BlockState createBlockState() {

		return new BlockState(this, POWER);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		if(meta > 4) {

			Logger.warn("Received invalid meta value %d, perhaps this is an old save?", meta);
			meta = 4;
		}
		return getDefaultState().withProperty(POWER, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		return (Integer) state.getValue(POWER);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {

		return (Integer) state.getValue(POWER);
	}

	public void setPower(World world, BlockPos pos, IBlockState state) {

		int lightLevel = world.getLightFor(EnumSkyBlock.SKY, pos.up());
		lightLevel = Math.max(lightLevel, world.getLightFor(EnumSkyBlock.SKY, pos.down()));
		lightLevel = Math.max(lightLevel, world.getLightFor(EnumSkyBlock.SKY, pos.north()));
		lightLevel = Math.max(lightLevel, world.getLightFor(EnumSkyBlock.SKY, pos.south()));
		lightLevel = Math.max(lightLevel, world.getLightFor(EnumSkyBlock.SKY, pos.west()));
		lightLevel = Math.max(lightLevel, world.getLightFor(EnumSkyBlock.SKY, pos.east()));

		lightLevel -= world.getSkylightSubtracted();
		int power = Math.max(0, lightLevel - 11);

		if(((Integer) state.getValue(POWER)).intValue() != power) {

			world.setBlockState(pos, getDefaultState().withProperty(POWER, power));
		}
	}

	@Override
	public boolean canProvidePower() {

		return true;
	}

	@Override
	public int tickRate(World world) {

		return 1;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		setPower(world, pos, state);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) {

		if(neighbor == this) {

			world.scheduleUpdate(pos, this, tickRate(world));
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {

		setPower(world, pos, state);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {

		return true;
	}
}
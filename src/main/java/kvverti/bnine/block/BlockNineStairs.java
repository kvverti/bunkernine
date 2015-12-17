package kvverti.bnine.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import kvverti.bnine.util.StringID;

public class BlockNineStairs extends BlockStairs implements StringID {

	private final String strID;
	private final Block baseBlock;

	public BlockNineStairs(String id, IBlockState state) {

		super(state);
		baseBlock = state.getBlock();
		strID = id;
		setCreativeTab(NineBlocks.tabNineBlocks);
		useNeighborBrightness = true;
	}

	@Override
	public String id() {

		return strID;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return baseBlock.isFlammable(world, pos, face);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return baseBlock.getFireSpreadSpeed(world, pos, face);
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return baseBlock.getFlammability(world, pos, face);
	}
}
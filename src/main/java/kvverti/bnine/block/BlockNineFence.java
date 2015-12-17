package kvverti.bnine.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import kvverti.bnine.util.StringID;

public class BlockNineFence extends BlockFence implements StringID {

	private final String strID;

	public BlockNineFence(String id, Material material) {

		super(material);
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

		return true;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return 5;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return 20;
	}
}
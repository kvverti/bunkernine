package kvverti.bnine.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import kvverti.bnine.util.StringID;

public class BlockNineLog extends BlockLog implements StringID {

	private final String stringID;

	public BlockNineLog(String id) {

		super();
		setCreativeTab(NineBlocks.tabNineBlocks);
		stringID = id;
		setHarvestLevel("axe", 1);
		setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
	}

	@Override
	public String id() {

		return stringID;
	}

	@Override
	public BlockState createBlockState() {

		return new BlockState(this, new IProperty[] { LOG_AXIS });
	}	

	@Override
	public IBlockState getStateFromMeta(int meta) {

		IBlockState state = getDefaultState();

		switch(meta & 12) {

			case 0: state = state.withProperty(LOG_AXIS, EnumAxis.Y);
				break;
			case 4: state = state.withProperty(LOG_AXIS, EnumAxis.X);
				break;
			case 8: state = state.withProperty(LOG_AXIS, EnumAxis.Z);
				break;
			default: state = state.withProperty(LOG_AXIS, EnumAxis.NONE);
		}

		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		int i = 0;

		switch(((EnumAxis) state.getValue(LOG_AXIS)).ordinal()) {

			case 0: i |= 4;
				break;
			case 2: i |= 8;
				break;
			case 3: i |= 12;
		}

		return i;
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

		return 5;
	}
}
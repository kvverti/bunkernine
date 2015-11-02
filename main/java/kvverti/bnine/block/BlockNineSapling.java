package kvverti.bnine.block;

import java.util.Random;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.event.terraingen.TerrainGen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.BlockPos;

import kvverti.bnine.util.StringID;
import kvverti.bnine.world.gen.NineTree;

public class BlockNineSapling extends BlockSapling implements StringID {

	private final String strID;
	private final Block logBlock;
	private final Block leafBlock;

	public BlockNineSapling(String id, Block log, Block leaves) {

		super();
		strID = id;
		logBlock = log;
		leafBlock = leaves;
		setCreativeTab(NineBlocks.tabNineBlocks);
		setDefaultState(blockState.getBaseState().withProperty(STAGE, 0));
	}

	@Override
	public String id() {

		return strID;
	}

	@Override
	public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {

		if(!TerrainGen.saplingGrowTree(world, rand, pos)) return;

		NineTree treeGen = new NineTree(logBlock, leafBlock, 4, true);

		world.setBlockState(pos, Blocks.air.getDefaultState(), 4);
		if(!treeGen.generate(world, rand, pos)) {

			world.setBlockState(pos, state, 4);
		}
	}

	@Override
	public boolean isTypeAt(World world, BlockPos pos, net.minecraft.block.BlockPlanks.EnumType type) {

		return world.getBlockState(pos).getBlock() == this;
	}

	@Override
	public int damageDropped(IBlockState state) {

		return 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		list.add(new ItemStack(item, 1, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return getDefaultState().withProperty(STAGE, (meta & 8) >> 3);
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		return ((Integer) state.getValue(STAGE)).intValue() << 3;
	}
}
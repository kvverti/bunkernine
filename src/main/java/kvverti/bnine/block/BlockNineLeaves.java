package kvverti.bnine.block;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import kvverti.bnine.client.Resources;
import kvverti.bnine.util.StringID;

public class BlockNineLeaves extends BlockLeaves implements StringID {

	private final String strID;

	public BlockNineLeaves(String id) {

		super();
		strID = id;
		setCreativeTab(NineBlocks.tabNineBlocks);
		setDefaultState(blockState.getBaseState().withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, true));
	}

	@Override
	public String id() {

		return strID;
	}

	@Override
	public BlockState createBlockState() {

		return new BlockState(this, DECAYABLE, CHECK_DECAY);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		int meta = 0;

		if((Boolean) state.getValue(DECAYABLE)) meta |= 4;
		if((Boolean) state.getValue(CHECK_DECAY)) meta |= 8;

		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {

		return Resources.INSTANCE.getColorBlock(id());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state) {

		return getBlockColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderpass) {

		return getBlockColor();
	}

	@Override
	public net.minecraft.block.BlockPlanks.EnumType getWoodType(int meta) {

		return null;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {

		return Item.getItemFromBlock(NineBlocks.saplingMazewood);
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {

		return Arrays.asList(new ItemStack(this, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {

		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube() {

		return false;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return true;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return 30;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return 60;
	}
}
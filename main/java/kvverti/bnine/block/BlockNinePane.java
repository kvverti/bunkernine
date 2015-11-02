package kvverti.bnine.block;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import kvverti.bnine.util.StringID;
import kvverti.bnine.item.NineItems;

public class BlockNinePane extends BlockPane implements StringID {

	private final String strID;

	public BlockNinePane(String id, Material material) {

		super(material, false);
		strID = id;
		setCreativeTab(NineBlocks.tabNineBlocks);
		useNeighborBrightness = true;
	}

	@Override
	public String id() {

		return strID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {

		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {

		return NineItems.glassShard;
	}

	@Override
	public int quantityDropped(Random rand) {

		return rand.nextInt(2);
	}

	@Override
	public boolean canPaneConnectTo(IBlockAccess world, BlockPos pos, EnumFacing side) {

		return super.canPaneConnectTo(world, pos, side) || world.getBlockState(pos.offset(side)).getBlock() == NineBlocks.cloudedGlass;
	}
}
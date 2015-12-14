package kvverti.bnine.block;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.minecraft.block.material.*;

public class BlockNineDoubleSlab extends BlockNineSlab {

	private final Block halfSlab;

	public BlockNineDoubleSlab(String id, Material material, Block halfSlab) {

		super(id, material);
		this.halfSlab = halfSlab;
	}

	public BlockNineDoubleSlab(String id, Material material, String tool, int tier, Block halfSlab) {

		super(id, material, tool, tier);
		this.halfSlab = halfSlab;
	}

	@Override
	public boolean isDouble() {

		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {

		return Item.getItemFromBlock(halfSlab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos) {

		return Item.getItemFromBlock(halfSlab);
	}
}
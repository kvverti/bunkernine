package kvverti.bnine.item;

import net.minecraft.item.ItemSlab;
import net.minecraft.block.Block;

import kvverti.bnine.block.BlockNineHalfSlab;
import kvverti.bnine.block.BlockNineDoubleSlab;

public class ItemNineSlab extends ItemSlab {

	public ItemNineSlab(Block block, BlockNineHalfSlab halfSlab, BlockNineDoubleSlab doubleSlab) {

		super(block, halfSlab, doubleSlab);
	}
}
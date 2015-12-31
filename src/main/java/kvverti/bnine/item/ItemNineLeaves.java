package kvverti.bnine.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;

public class ItemNineLeaves extends ItemBlock {

	public ItemNineLeaves(Block block) {

		super(block);
	}

	@Override
	public int getMetadata(int damage) {

		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderpass) {

		return block.getBlockColor();
	}
}
package kvverti.bnine.item;

import net.minecraftforge.common.IPlantable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import kvverti.bnine.block.NineBlocks;

public class ItemNineTwistle extends ItemBlock {

	public ItemNineTwistle(Block block) {

		super(block);
		setCreativeTab(NineBlocks.tabNineBlocks);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float x, float y, float z) {

		if(!player.canPlayerEdit(pos, side, stack)) {

			return false;
		}
		Block below = world.getBlockState(pos.offset(side).down()).getBlock();
		if(below != block && !below.canSustainPlant(world, pos, EnumFacing.UP, (IPlantable) block)) {

			return false;
		}

		if(!world.isRemote) {

			world.setBlockState(pos.offset(side), block.getDefaultState());
			world.playSoundEffect(
				pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
				block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0f) / 2.0f,
				block.stepSound.getFrequency() * 0.8f
			);
			stack.stackSize--;
		}
		return true;
	}
}
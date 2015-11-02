package kvverti.bnine.item;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import kvverti.bnine.block.BlockNineFluorescentLight;
import kvverti.bnine.block.NineBlocks;
import kvverti.bnine.block.entity.TileFluorescentLight;

public class ItemNineFluorescentLight extends ItemBlock {

	public ItemNineFluorescentLight(Block block) {

		super(block);
		setCreativeTab(NineBlocks.tabNineBlocks);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {

		for(int i = 0; i <= NineLightColor.MAX_ORDINAL; i++) {

			if(!NineLightColor.isUsedValue(i)) continue;
			subItems.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

		if(!world.getBlockState(pos).getBlock().isOpaqueCube()) {

			return false;

		} else if(!player.canPlayerEdit(pos, side, stack)) {

			return false;

		} else if(world.isRemote) {

			return true;

		} else {

			pos = pos.offset(side);
			world.setBlockState(pos, block.getDefaultState().withProperty(BlockNineFluorescentLight.FACING, side));
			world.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0f) / 2.0f, block.stepSound.getFrequency() * 0.8f);
			stack.stackSize--;

			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TileFluorescentLight) {

				((TileFluorescentLight) tile).setValuesFromItem(stack);
			}
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {

		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10)) {

			NBTTagCompound tag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

			if(tag.hasKey(TileFluorescentLight.COLOR, 8)) {

				NineLightColor col = NineLightColor.byName(tag.getString(TileFluorescentLight.COLOR));

				if(col != NineLightColor.NULL) return col.getClientColor();
			}
			if(tag.hasKey(TileFluorescentLight.CUSTOM_COLOR, 3)) {

				return tag.getInteger(TileFluorescentLight.CUSTOM_COLOR);
			}
		}
		return NineLightColor.byMetadata(stack.getMetadata()).getClientColor();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		NineLightColor color = NineLightColor.byMetadata(stack.getMetadata());
		return "tile.fluorescentLight." + color;
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List toolclip, boolean advanced) {

		NBTTagCompound tag = stack.getSubCompound("BlockEntityTag", false);

		if(advanced) {

			if(tag != null) {

				if(tag.hasKey(TileFluorescentLight.COLOR)) {

					NineLightColor c = NineLightColor.byName(tag.getString(TileFluorescentLight.COLOR));

					if(c != NineLightColor.NULL) {

						toolclip.add(1, "Color: " + Integer.toHexString(c.getClientColor()).toUpperCase());
						return;
					}
				}
				if(tag.hasKey(TileFluorescentLight.CUSTOM_COLOR)) {

					toolclip.add(1, "Color: " + Integer.toHexString(tag.getInteger(TileFluorescentLight.CUSTOM_COLOR)).toUpperCase());
					return;
				}

			}
			toolclip.add(1, "Color: " + Integer.toHexString(NineLightColor.byMetadata(stack.getMetadata()).getClientColor()).toUpperCase());
		}
	}
}
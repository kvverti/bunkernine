package kvverti.bnine.block.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import kvverti.bnine.block.BlockNineFluorescentLight;
import kvverti.bnine.block.NineBlocks;
import kvverti.bnine.item.NineLightColor;
import kvverti.bnine.util.ClassID;

@ClassID("fluorescentLight")
public class TileFluorescentLight extends TileEntity implements IUpdatePlayerListBox {

	public static final String POWER = "Power";
	public static final String COLOR = "Color";
	public static final String CUSTOM_COLOR = "CustomColor";

	private byte power = 0;
	private int color = 0xffffff;
	private NineLightColor defaultColor = NineLightColor.NULL;

	public void setPower(int pow) {

		if(pow < 0) pow = 0;
		if(pow > 15) pow = 15;

		power = (byte) pow;
	}

	public byte getPower() {

		return power;
	}

	public void setColor(int col) {

		if(col < 0x0) col = 0x0;
		if(col > 0xffffff) col = 0xffffff;

		color = col;
	}

	public int getColor() {

		return color;
	}

	public void setDefaultColor(NineLightColor col) {

		if(col == null) col = NineLightColor.NULL;
		defaultColor = col;
	}

	public NineLightColor getDefaultColor() {

		return defaultColor;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {

		super.writeToNBT(tag);
		tag.setByte(POWER, power);
		tag.setString(COLOR, defaultColor.getName());
		tag.setInteger(CUSTOM_COLOR, color);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		super.readFromNBT(tag);
		setPower(tag.getByte(POWER));
		setDefaultColor(NineLightColor.byName(tag.getString(COLOR)));
		setColor(tag.getInteger(CUSTOM_COLOR));
	}

	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);

		return new S35PacketUpdateTileEntity(pos, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {

		readFromNBT(packet.getNbtCompound());
		worldObj.markBlockForUpdate(pos);
	}

	@Override
	public void update() {

		if(!worldObj.isRemote) {

			IBlockState state = worldObj.getBlockState(pos);
			BlockNineFluorescentLight block = (BlockNineFluorescentLight) state.getBlock();
			byte lightPower = block.getLightPowered(worldObj, pos);

			int i = worldObj.getRedstonePower(pos.down(), EnumFacing.DOWN);
			i = Math.max(i, worldObj.getRedstonePower(pos.up(), EnumFacing.UP));
			i = Math.max(i, worldObj.getRedstonePower(pos.north(), EnumFacing.NORTH));
			i = Math.max(i, worldObj.getRedstonePower(pos.south(), EnumFacing.SOUTH));
			i = Math.max(i, worldObj.getRedstonePower(pos.east(), EnumFacing.EAST));
			i = Math.max(i, worldObj.getRedstonePower(pos.west(), EnumFacing.WEST));

			setPower(Math.max(i, lightPower - 1));

			NBTTagCompound tag = new NBTTagCompound();
			writeToNBT(tag);
			if(getPower() == 0 && block.isLit()) {

				worldObj.setBlockState(pos, block.getUnlitState(state));
				worldObj.getTileEntity(pos).readFromNBT(tag);

			} else if(getPower() != 0 && !block.isLit()) {

				worldObj.setBlockState(pos, block.getLitState(state));
				worldObj.getTileEntity(pos).readFromNBT(tag);
			}
		}
	}

	public void setValuesFromItem(ItemStack stack) {

		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10)) {

			NBTTagCompound tag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

			if(tag.hasKey(COLOR, 8)) {

				setDefaultColor(NineLightColor.byName(tag.getString(COLOR)));

			}
			if(tag.hasKey(CUSTOM_COLOR, 3)) {

				setColor(tag.getInteger(CUSTOM_COLOR));
			}

		} else setDefaultColor(NineLightColor.byMetadata(stack.getMetadata()));
	}
}
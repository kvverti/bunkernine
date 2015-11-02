package kvverti.bnine.block;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import kvverti.bnine.util.StringID;

public class BlockNine extends Block implements StringID {

	private final String stringID;
	private boolean isFullCube = true;
	private boolean isOpaqueCube = true;
	private boolean dropRandom = false;
	private boolean flammable = false;
	private int dropQuantity = 1;
	private int maxBound = 1;
	private int dropOffset = 0;
	private int fireSpreadSpeed = 0;
	private int flammability = 0;
	private Item dropItem;
	private String renderPass;

	public BlockNine(String id, Material material) {

		super(material);
		setLightOpacity(255);
		stringID = id;
		setCreativeTab(NineBlocks.tabNineBlocks);
		
	}

	public BlockNine(String id, Material material, String tool, int tier) {

		this(id, material);
		setHarvestLevel(tool, tier);
	}

	@Override
	public String id() {

		return stringID;
	}

	@Override
	public boolean isFullCube() {

		return isFullCube;
	}

	public BlockNine setIsFullCube(boolean fullCube) {

		isFullCube = fullCube;
		return this;
	}

	@Override
	public boolean isOpaqueCube() {

		return isOpaqueCube;
	}

	public BlockNine setIsOpaqueCube(boolean opaque) {

		isOpaqueCube = opaque;
		return this;
	}

	@Override
	public int quantityDropped(Random random) {

		if(dropRandom) return random.nextInt(maxBound) + dropOffset;
		else return dropQuantity;
	}

	public BlockNine setQuantityDropped(int quantity) {

		dropRandom = false;
		dropQuantity = quantity;
		return this;
	}

	public BlockNine setRandomQuantityDropped(int min, int max) {

		if(min < 0 || min > max) throw new IllegalArgumentException(min + ", " + max);

		dropRandom = true;
		dropOffset = min;
		maxBound = max - min;

		return this;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {

		return dropItem != null ? dropItem : Item.getItemFromBlock(this);
	}

	public BlockNine setItemDropped(Item drop) {

		dropItem = drop;
		return this;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return face == EnumFacing.UP ? true : flammable;
	}

	public BlockNine setIsFlammable(boolean flam) {

		flammable = flam;
		return this;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return fireSpreadSpeed;
	}

	public BlockNine setFireSpreadSpeed(int speed) {

		fireSpreadSpeed = speed >= 0 ? speed : 0;
		return this;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return flammability;
	}

	public BlockNine setFlammability(int flam) {

		flammability = flam < 0 ? 0 : (flam > 300 ? 300 : flam);
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {

		if("cutout_mipped".equals(renderPass)) return EnumWorldBlockLayer.CUTOUT_MIPPED;
		if("cutout".equals(renderPass)) return EnumWorldBlockLayer.CUTOUT;
		if("translucent".equals(renderPass)) return EnumWorldBlockLayer.TRANSLUCENT;
		return EnumWorldBlockLayer.SOLID;
	}

	public BlockNine setBlockLayer(String layer) {

		renderPass = layer;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side) {

		return !isOpaqueCube() ? world.getBlockState(pos).getBlock() != this && super.shouldSideBeRendered(world, pos, side) : super.shouldSideBeRendered(world, pos, side);
	}
}
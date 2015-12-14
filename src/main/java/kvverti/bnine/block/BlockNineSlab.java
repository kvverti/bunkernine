package kvverti.bnine.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.material.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import kvverti.bnine.util.StringID;

public abstract class BlockNineSlab extends BlockSlab implements StringID {

	private boolean flammable = false;
	private int flammability = 0;
	private int fireSpreadSpeed = 0;

	public static enum Type implements IStringSerializable {

		DEFAULT ("default");

		private final String name;

		private Type(String s) {

			name = s;
		}

		public String getName() {

			return name;
		}
	}

	private static final PropertyEnum VARIANT = PropertyEnum.create("variant", Type.class);

	private final String stringID;

	public BlockNineSlab(String id, Material material) {

		super(material);
		stringID = id;

		IBlockState state = blockState.getBaseState().withProperty(VARIANT, Type.DEFAULT);

		if(!isDouble()) {

			setCreativeTab(NineBlocks.tabNineBlocks);
			state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
		}

		setDefaultState(state);
		useNeighborBrightness = !isDouble();
	}

	public BlockNineSlab(String id, Material material, String tool, int tier) {

		this(id, material);
		setHarvestLevel(tool, tier);
	}

	@Override
	public String id() {

		return stringID;
	}

	@Override
	public String getUnlocalizedName(int meta) {

		return getUnlocalizedName();
	}

	@Override
	public BlockState createBlockState() {

		return isDouble() ? new BlockState(this, new IProperty[] { VARIANT }) : new BlockState(this, new IProperty[] { VARIANT, HALF });
	}

	@Override
	public abstract boolean isDouble();

	/* Double slabs must define #getItemDropped(IBlockState, Random, int) and @client-side #getItem(World, BlockPos) */

	@Override
	public IProperty getVariantProperty() {

		return VARIANT;
	}

	@Override
	public Object getVariant(ItemStack stack) {

		return Type.DEFAULT;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		IBlockState state = getDefaultState().withProperty(VARIANT, Type.DEFAULT);

		if(!isDouble()) {

			state = (meta & 8) == 0 ? state.withProperty(HALF, EnumBlockHalf.BOTTOM) : state.withProperty(HALF, EnumBlockHalf.TOP);
		}

		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		int meta = 0;

		if(!isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) meta |= 8;

		return meta;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return face == EnumFacing.UP ? true : flammable;
	}

	public BlockNineSlab setIsFlammable(boolean flam) {

		flammable = flam;
		return this;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return fireSpreadSpeed;
	}

	public BlockNineSlab setFireSpreadSpeed(int speed) {

		fireSpreadSpeed = speed >= 0 ? speed : 0;
		return this;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return flammability;
	}

	public BlockNineSlab setFlammability(int flam) {

		flammability = flam < 0 ? 0 : (flam > 300 ? 300 : flam);
		return this;
	}
}
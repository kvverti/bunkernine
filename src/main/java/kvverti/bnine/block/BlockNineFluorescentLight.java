package kvverti.bnine.block;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import kvverti.bnine.block.entity.TileFluorescentLight;
import kvverti.bnine.item.NineItems;
import kvverti.bnine.item.NineLightColor;

public class BlockNineFluorescentLight extends BlockNine implements ITileEntityProvider {

	public static final IProperty FACING = PropertyDirection.create("facing");
	public static final IProperty NORTH = PropertyBool.create("top");
	public static final IProperty SOUTH = PropertyBool.create("bottom");
	public static final IProperty WEST = PropertyBool.create("left");
	public static final IProperty EAST = PropertyBool.create("right");

	private final boolean isLit;

	public BlockNineFluorescentLight(String id, boolean lit) {

		super(id, Material.circuits);

		IBlockState state = blockState.getBaseState()
			.withProperty(FACING, EnumFacing.DOWN)
			.withProperty(NORTH, false)
			.withProperty(SOUTH, false)
			.withProperty(WEST, false)
			.withProperty(EAST, false);

		if(isLit = lit) {

			setLightLevel(1.0f);
		}
		setIsOpaqueCube(false);
		setIsFullCube(false);
		setLightOpacity(0);
		setDefaultState(state);
	}

	public boolean isLit() {

		return isLit;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {

		return Item.getItemFromBlock(NineBlocks.fluorescentLight);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, BlockPos pos) {

		return Item.getItemFromBlock(NineBlocks.fluorescentLight);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {

		if(world.getTileEntity(pos) instanceof TileFluorescentLight) {

			TileFluorescentLight te = (TileFluorescentLight) world.getTileEntity(pos);
			NineLightColor c = te.getDefaultColor();

			int color = c != NineLightColor.NULL ? c.getClientColor() : te.getColor();
			if(!isLit) {

				int r = (color & 0xff0000) / 3;
				int g = (color & 0x00ff00) / 3;
				int b = (color & 0x0000ff) / 3;

				color = (r & 0xff0000) | (g & 0x00ff00) | (b & 0x0000ff);
			}
			return color;
		}
		return 0xffffff;
	}

	@Override
	public BlockState createBlockState() {

		return new BlockState(this, FACING, NORTH, SOUTH, WEST, EAST);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {

		return null;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {

		EnumFacing top;
		EnumFacing bottom;
		EnumFacing left;
		EnumFacing right;

		switch((EnumFacing) state.getValue(FACING)) {

			case UP:
				top = EnumFacing.NORTH;
				bottom = EnumFacing.SOUTH;
				left = EnumFacing.WEST;
				right = EnumFacing.EAST;
				break;

			case DOWN:
				top = EnumFacing.SOUTH;
				bottom = EnumFacing.NORTH;
				left = EnumFacing.WEST;
				right = EnumFacing.EAST;
				break;

			case NORTH:
				top = EnumFacing.UP;
				bottom = EnumFacing.DOWN;
				left = EnumFacing.EAST;
				right = EnumFacing.WEST;
				break;

			case SOUTH:
				top = EnumFacing.UP;
				bottom = EnumFacing.DOWN;
				left = EnumFacing.WEST;
				right = EnumFacing.EAST;
				break;

			case WEST:
				top = EnumFacing.UP;
				bottom = EnumFacing.DOWN;
				left = EnumFacing.NORTH;
				right = EnumFacing.SOUTH;
				break;

			case EAST:
				top = EnumFacing.UP;
				bottom = EnumFacing.DOWN;
				left = EnumFacing.SOUTH;
				right = EnumFacing.NORTH;
				break;

			default: throw new NullPointerException();
		}

		state = state.withProperty(NORTH, canConnectTo(world, pos, top))
			.withProperty(SOUTH, canConnectTo(world, pos, bottom))
			.withProperty(WEST, canConnectTo(world, pos, left))
			.withProperty(EAST, canConnectTo(world, pos, right));

		return state;
	}

	protected boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir) {

		IBlockState offset = world.getBlockState(pos.offset(dir));

		return offset.getBlock() instanceof BlockNineFluorescentLight
			&& world.getBlockState(pos).getValue(FACING) == offset.getValue(FACING);
	}

	public byte getLightPowered(World world, BlockPos pos) {

		byte level = checkPower(world, pos, EnumFacing.DOWN);

		level = (byte) Math.max(level, checkPower(world, pos, EnumFacing.UP));
		level = (byte) Math.max(level, checkPower(world, pos, EnumFacing.NORTH));
		level = (byte) Math.max(level, checkPower(world, pos, EnumFacing.SOUTH));
		level = (byte) Math.max(level, checkPower(world, pos, EnumFacing.WEST));
		level = (byte) Math.max(level, checkPower(world, pos, EnumFacing.EAST));

		return level;
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) {

		if(
		!world.getBlockState(pos.offset(((EnumFacing) state.getValue(FACING)).getOpposite()))
		.getBlock().isOpaqueCube()
		) {
			dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {

		boolean up = canConnectTo(world, pos, EnumFacing.UP);
		boolean down = canConnectTo(world, pos, EnumFacing.DOWN);
		boolean north = canConnectTo(world, pos, EnumFacing.NORTH);
		boolean south = canConnectTo(world, pos, EnumFacing.SOUTH);
		boolean west = canConnectTo(world, pos, EnumFacing.WEST);
		boolean east = canConnectTo(world, pos, EnumFacing.EAST);

		float minX = west ? 0.0f : 0.4375f;
		float minY = down ? 0.0f : 0.4375f;
		float minZ = north ? 0.0f : 0.4375f;
		float maxX = east ? 1.0f : 0.5625f;
		float maxY = up ? 1.0f : 0.5625f;
		float maxZ = south ? 1.0f : 0.5625f;

		switch((EnumFacing) world.getBlockState(pos).getValue(FACING)) {

			case DOWN:
				minY = 0.875f;
				maxY = 1.0f;
				if(!north && !south && !east && !west) {

					minX = 0.0f;
					maxX = 1.0f;
				}
				break;

			case UP:
				minY = 0.0f;
				maxY = 0.125f;
				if(!north && !south && !east && !west) {

					minX = 0.0f;
					maxX = 1.0f;
				}
				break;

			case NORTH:
				if(!up && !down && !east && !west) {

					minX = 0.0f;
					maxX = 1.0f;
				}
				minZ = 0.875f;
				maxZ = 1.0f;
				break;

			case SOUTH:
				if(!up && !down && !east && !west) {

					minX = 0.0f;
					maxX = 1.0f;
				}
				minZ = 0.0f;
				maxZ = 0.125f;
				break;

			case WEST:
				if(!up && !down && !north && !south) {

					minZ = 0.0f;
					maxZ = 1.0f;
				}
				minX = 0.875f;
				maxX = 1.0f;
				break;

			case EAST:
				if(!up && !down && !north && !south) {

					minZ = 0.0f;
					maxZ = 1.0f;
				}
				minX = 0.0f;
				maxX = 0.125f;
				break;

			default: throw new NullPointerException();
		}

		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {

		if(!world.isRemote) {

			if(world.isBlockPowered(pos) && !isLit) {

				world.setBlockState(pos, getLitState(world.getBlockState(pos)), 2);
			}
		}
	}

	public IBlockState getUnlitState(IBlockState state) {

		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		Boolean top = (Boolean) state.getValue(NORTH);
		Boolean bottom = (Boolean) state.getValue(SOUTH);
		Boolean left = (Boolean) state.getValue(WEST);
		Boolean right = (Boolean) state.getValue(EAST);

		return NineBlocks.fluorescentLight.getDefaultState()
			.withProperty(FACING, facing)
			.withProperty(NORTH, top)
			.withProperty(SOUTH, bottom)
			.withProperty(WEST, left)
			.withProperty(EAST, right);
	}

	public IBlockState getLitState(IBlockState state) {

		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		Boolean top = (Boolean) state.getValue(NORTH);
		Boolean bottom = (Boolean) state.getValue(SOUTH);
		Boolean left = (Boolean) state.getValue(WEST);
		Boolean right = (Boolean) state.getValue(EAST);

		return NineBlocks.litFluorescentLight.getDefaultState()
			.withProperty(FACING, facing)
			.withProperty(NORTH, top)
			.withProperty(SOUTH, bottom)
			.withProperty(WEST, left)
			.withProperty(EAST, right);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileFluorescentLight();
	}

	public byte checkPower(World world, BlockPos pos, EnumFacing side) {

		TileEntity tile = world.getTileEntity(pos.offset(side));
		if(canConnectTo(world, pos, side)) {

			return ((TileFluorescentLight) tile).getPower();

		} else return (byte) 0;
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {

		if(world.rand.nextFloat() > chance) return;
		if(world.getTileEntity(pos) instanceof TileFluorescentLight) {

			TileFluorescentLight te = (TileFluorescentLight) world.getTileEntity(pos);
			spawnAsEntity(world, pos, getDropStack(world, state, te));

		} else super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile) {

		if(tile instanceof TileFluorescentLight) {

			spawnAsEntity(world, pos, getDropStack(world, state, (TileFluorescentLight) tile));

		} else super.harvestBlock(world, player, pos, state, null);
	}

	private ItemStack getDropStack(World world, IBlockState state, TileFluorescentLight te) {

		NineLightColor color = te.getDefaultColor();
		ItemStack stack = new ItemStack(
			getItemDropped(state, world.rand, 0), 1,
			color != NineLightColor.NULL ? color.getMetadata() : 0
		);

		if(color == NineLightColor.NULL) {

			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger(TileFluorescentLight.CUSTOM_COLOR, te.getColor());
			stack.setTagInfo("BlockEntityTag", tag);
		}
		return stack;
	}
}
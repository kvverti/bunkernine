package kvverti.bnine.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import kvverti.bnine.block.entity.TileFluorescentLight;
import kvverti.bnine.init.Registry;
import kvverti.bnine.item.*;

public final class NineBlocks {

	public static CreativeTabs tabNineBlocks = new CreativeTabs("nineBlocks") {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {

			return Item.getItemFromBlock(cityStone);
		}
	};

	public static Block cityStone;
	public static Block logMazewood;
	public static Block planksMazewood;
	public static Block slabMazewood;
	public static Block doubleSlabMazewood;
	public static Block stairsMazewood;
	public static Block fenceMazewood;
	public static Block fenceGateMazewood;
	public static Block cloudedGlass;
	public static Block cloudedGlassPane;
	public static Block fluorescentLight;
	public static Block litFluorescentLight;
	public static Block rope;
	public static Block oreSilicon;
	public static Block blockSilicon;
	public static Block saplingMazewood;
	public static Block leavesMazewood;

	static {

		cityStone = new BlockNine("city_stone", Material.rock, "pickaxe", 0)
			.setHardness(1.5f)
			.setResistance(3.0f)
			.setUnlocalizedName("cityStone");

		logMazewood = new BlockNineLog("mazewood_log")
			.setUnlocalizedName("logMazewood");

		planksMazewood = new BlockNine("mazewood_planks", Material.wood, "axe", 0)
			.setIsFlammable(true)
			.setFireSpreadSpeed(5)
			.setFlammability(20)
			.setHardness(2.0f)
			.setResistance(5.0f)
			.setStepSound(Block.soundTypeWood)
			.setUnlocalizedName("planksMazewood");

		slabMazewood = new BlockNineHalfSlab("mazewood_slab", Material.wood, "axe", 0)
			.setIsFlammable(true)
			.setFireSpreadSpeed(5)
			.setFlammability(20)
			.setHardness(2.0f)
			.setResistance(5.0f)
			.setStepSound(Block.soundTypeWood)
			.setUnlocalizedName("slabMazewood");

		doubleSlabMazewood = new BlockNineDoubleSlab("double_mazewood_slab", Material.wood, "axe", 0, slabMazewood)
			.setIsFlammable(true)
			.setFireSpreadSpeed(5)
			.setFlammability(20)
			.setHardness(2.0f)
			.setResistance(5.0f)
			.setStepSound(Block.soundTypeWood);

		stairsMazewood = new BlockNineStairs("mazewood_stairs", planksMazewood.getDefaultState())
			.setUnlocalizedName("stairsMazewood");

		fenceMazewood = new BlockNineFence("mazewood_fence", Material.wood)
			.setHardness(2.0f)
			.setResistance(5.0f)
			.setStepSound(Block.soundTypeWood)
			.setUnlocalizedName("fenceMazewood");

		fenceGateMazewood = new BlockNineFenceGate("mazewood_fence_gate")
			.setHardness(2.0f)
			.setResistance(5.0f)
			.setStepSound(Block.soundTypeWood)
			.setUnlocalizedName("fenceGateMazewood");

		cloudedGlass = new BlockNine("clouded_glass", Material.glass)
			.setIsOpaqueCube(false)
			.setIsFullCube(false)
			.setBlockLayer("translucent")
			.setItemDropped(NineItems.glassShard)
			.setRandomQuantityDropped(0, 5)
			.setLightOpacity(0)
			.setHardness(0.3f)
			.setStepSound(Block.soundTypeGlass)
			.setUnlocalizedName("cloudedGlass");

		cloudedGlassPane = new BlockNinePane("clouded_glass_pane", Material.glass)
			.setHardness(0.3f)
			.setStepSound(Block.soundTypeGlass)
			.setUnlocalizedName("cloudedGlassPane");

		fluorescentLight = new BlockNineFluorescentLight("fluorescent_light", false)
			.setHardness(0.3f)
			.setStepSound(Block.soundTypeGlass)
			.setUnlocalizedName("fluorescentLight");

		litFluorescentLight = new BlockNineFluorescentLight("lit_fluorescent_light", true)
			.setHardness(0.3f)
			.setStepSound(Block.soundTypeGlass);

		rope = new BlockNineRope("rope", Material.circuits)
			.setHardness(0.0f)
			.setStepSound(Block.soundTypeLadder)
			.setUnlocalizedName("rope");

		oreSilicon = new BlockNine("silicon_ore", Material.rock, "pickaxe", 1)
			.setHardness(3.0f)
			.setResistance(5.0f)
			.setStepSound(Block.soundTypeStone)
			.setUnlocalizedName("oreSilicon");

		blockSilicon = new BlockNineSilicon("silicon_block")
			.setHardness(5.0f)
			.setResistance(10.0f)
			.setStepSound(Block.soundTypeMetal)
			.setUnlocalizedName("blockSilicon");

		leavesMazewood = new BlockNineLeaves("mazewood_leaves")
			.setUnlocalizedName("leavesMazewood");

		saplingMazewood = new BlockNineSapling("mazewood_sapling", logMazewood, leavesMazewood)
			.setHardness(0.0f)
			.setStepSound(Block.soundTypeGrass)
			.setUnlocalizedName("saplingMazewood");
	}

	public static void register() {

		Registry.registerBlock(cityStone);
		Registry.registerBlock(logMazewood);
		Registry.registerBlock(planksMazewood);
		Registry.registerBlock(slabMazewood, ItemNineSlab.class, slabMazewood, doubleSlabMazewood);
		Registry.registerBlock(doubleSlabMazewood, null);
		Registry.registerBlock(stairsMazewood);
		Registry.registerBlock(fenceMazewood);
		Registry.registerBlock(fenceGateMazewood);
		Registry.registerBlock(cloudedGlass);
		Registry.registerBlock(cloudedGlassPane);
		Registry.registerBlock(fluorescentLight, ItemNineFluorescentLight.class);
		Registry.registerBlock(litFluorescentLight, null);
		Registry.registerBlock(rope, ItemNineRope.class);
		Registry.registerBlock(oreSilicon);
		Registry.registerBlock(blockSilicon);
		Registry.registerBlock(saplingMazewood);
		Registry.registerBlock(leavesMazewood, ItemNineLeaves.class);
	}

	public static void doSpecialStateMapping() {

		Registry.ignoreStates(fenceGateMazewood, BlockNineFenceGate.POWERED);
		Registry.ignoreStates(blockSilicon, BlockNineSilicon.POWER);
		Registry.ignoreStates(saplingMazewood, BlockNineSapling.TYPE);
		Registry.ignoreStates(leavesMazewood, BlockNineLeaves.DECAYABLE, BlockNineLeaves.CHECK_DECAY);
	}

	public static void registerRenderers() {

		Registry.registerItemRenderer(cityStone);
		Registry.registerItemRenderer(logMazewood);
		Registry.registerItemRenderer(planksMazewood);
		Registry.registerItemRenderer(slabMazewood);
		Registry.registerItemRenderer(stairsMazewood);
		Registry.registerItemRenderer(fenceMazewood);
		Registry.registerItemRenderer(fenceGateMazewood);
		Registry.registerItemRenderer(cloudedGlass);
		Registry.registerItemRenderer(cloudedGlassPane);
		Registry.registerItemRenderer(rope);
		Registry.registerItemRenderer(oreSilicon);
		Registry.registerItemRenderer(blockSilicon);
		Registry.registerItemRenderer(saplingMazewood);
		Registry.registerItemRenderer(leavesMazewood);
	}

	public static void registerTileEntities() {

		Registry.registerTileEntity(TileFluorescentLight.class);
	}
}
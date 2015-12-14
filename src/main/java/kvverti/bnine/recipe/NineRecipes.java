package kvverti.bnine.recipe;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import kvverti.bnine.block.NineBlocks;
import kvverti.bnine.item.NineItems;

public class NineRecipes {

	public static void register() {

		//Mazewood planks
		GameRegistry.addShapelessRecipe(
			new ItemStack(NineBlocks.planksMazewood, 4),
			new Object[] { NineBlocks.logMazewood }
		);

		//Mazewood slabs
		GameRegistry.addShapedRecipe(
			new ItemStack(NineBlocks.slabMazewood, 6),
			new Object[] { "###", '#', NineBlocks.planksMazewood }
		);

		//Mazewood stairs
		GameRegistry.addShapedRecipe(
			new ItemStack(NineBlocks.stairsMazewood, 4),
			new Object[] { "#  ", "## ", "###", '#', NineBlocks.planksMazewood }
		);

		//Charcoal from mazewood
		GameRegistry.addSmelting(
			NineBlocks.logMazewood,
			new ItemStack(Items.coal, 1, 1),
			0.15f
		);

		//Mazewood fence
		GameRegistry.addShapedRecipe(
			new ItemStack(NineBlocks.fenceMazewood, 3),
			new Object[] { "#|#", "#|#", '#', NineBlocks.planksMazewood, '|', Items.stick }
		);

		//Mazewood fence gate
		GameRegistry.addShapedRecipe(
			new ItemStack(NineBlocks.fenceGateMazewood),
			new Object[] { "|#|", "|#|", '#', NineBlocks.planksMazewood, '|', Items.stick }
		);

		//Clouded glass
		GameRegistry.addShapedRecipe(
			new ItemStack(NineBlocks.cloudedGlass),
			new Object[] { "##", "##", '#', NineItems.glassShard }
		);

		//Clouded glass pane
		GameRegistry.addShapedRecipe(
			new ItemStack(NineBlocks.cloudedGlassPane, 16),
			new Object[] { "###", "###", '#', NineBlocks.cloudedGlass }
		);

		//Rope
		GameRegistry.addShapedRecipe(
			new ItemStack(NineBlocks.rope),
			new Object[] { "#", "#", "#", '#', Items.string }
		);

		//Silicon smelting
		GameRegistry.addSmelting(
			NineBlocks.oreSilicon,
			new ItemStack(NineItems.silicon),
			0.15f
		);

		//Glass shard smelting
		GameRegistry.addSmelting(
			NineItems.silicon,
			new ItemStack(NineItems.glassShard),
			0.15f
		);

		//Block of silicon
		GameRegistry.addShapedRecipe(
			new ItemStack(NineBlocks.blockSilicon),
			new Object[] { "###", "###", "###", '#', NineItems.silicon }
		);

		//Reverse
		GameRegistry.addShapelessRecipe(
			new ItemStack(NineItems.silicon, 9),
			new Object[] { NineBlocks.blockSilicon }
		);
	}

	public static void registerOres() {

		OreDictionary.registerOre("logWood", NineBlocks.logMazewood);
		OreDictionary.registerOre("plankWood", NineBlocks.planksMazewood);
		OreDictionary.registerOre("slabWood", NineBlocks.slabMazewood);
		OreDictionary.registerOre("stairWood", NineBlocks.stairsMazewood);
		OreDictionary.registerOre("blockGlass", NineBlocks.cloudedGlass);
		OreDictionary.registerOre("paneGlass", NineBlocks.cloudedGlassPane);
		OreDictionary.registerOre("shardGlass", NineItems.glassShard);
		OreDictionary.registerOre("oreSilicon", NineBlocks.oreSilicon);
		OreDictionary.registerOre("blockSilicon", NineBlocks.blockSilicon);
		OreDictionary.registerOre("ingotSilicon", NineItems.silicon);
		OreDictionary.registerOre("treeLeaves", NineBlocks.leavesMazewood);
		OreDictionary.registerOre("treeSapling", NineBlocks.saplingMazewood);
	}
}
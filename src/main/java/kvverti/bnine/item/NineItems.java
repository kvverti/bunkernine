package kvverti.bnine.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;

import kvverti.bnine.block.NineBlocks;
import kvverti.bnine.init.Registry;

public final class NineItems {

	public static CreativeTabs tabNineItems = new CreativeTabs("nineItems") {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {

			return NineItems.glassShard;
		}
	};

	public static Item glassShard;
	public static Item silicon;
	public static Item spawnEgg;

	static {

		glassShard = new ItemNine("glass_shard")
			.setUnlocalizedName("glassShard");

		silicon = new ItemNine("silicon")
			.setUnlocalizedName("silicon");

		spawnEgg = new ItemNineSpawnEgg("spawn_egg")
			.setUnlocalizedName("nineSpawnEgg");
	}

	public static void register() {

		Registry.registerItem(glassShard);
		Registry.registerItem(silicon);
		Registry.registerItem(spawnEgg);
	}

	public static void registerRenderers() {

		Registry.registerItemRenderer(glassShard);
		Registry.registerItemRenderer(silicon);

		for(int i = 0; i < ItemNineSpawnEgg.ENTITY_TYPES.size(); i++) {

			Registry.registerItemRenderer(spawnEgg, i);
		}
	}
}
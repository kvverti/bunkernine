package kvverti.bnine.proxy;

import kvverti.bnine.block.NineBlocks;
import kvverti.bnine.entity.NineEntities;
import kvverti.bnine.item.NineItems;
import kvverti.bnine.recipe.NineRecipes;

public abstract class Proxy {

	public void preInit() {

		NineBlocks.register();
		NineBlocks.registerTileEntities();
		NineItems.register();
		NineEntities.register();
		NineEntities.registerEggs();
	}

	public void init() {

		NineRecipes.register();
		NineRecipes.registerOres();
	}

	public void postInit() { }
}
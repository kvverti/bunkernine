package kvverti.bnine.proxy;

import kvverti.bnine.block.NineBlocks;
import kvverti.bnine.entity.NineEntities;
import kvverti.bnine.item.NineItems;
import kvverti.bnine.recipe.NineRecipes;
import kvverti.bnine.util.LightColor;
import kvverti.bnine.util.Logger;

public abstract class Proxy {

	protected Proxy() { }

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

	public void postInit() {

		LightColor.close();
		Logger.info("Registered light colors:");
		for(LightColor c : LightColor.values()) {

			Logger.info("\t%s", c);
		}
	}
}
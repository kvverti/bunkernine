package kvverti.bnine.proxy;

import kvverti.bnine.block.NineBlocks;
import kvverti.bnine.entity.NineEntities;
import kvverti.bnine.item.NineItems;
import kvverti.bnine.init.Registry;
import kvverti.bnine.client.Resources;

public class ClientProxy extends Proxy {

	@Override
	public void preInit() {

		super.preInit();
		NineBlocks.doSpecialStateMapping();
	}

	@Override
	public void init() {

		super.init();
		Registry.registerReloadListener(Resources.INSTANCE);
		NineBlocks.registerRenderers();
		NineItems.registerRenderers();
		NineEntities.registerRenderers();
	}
}
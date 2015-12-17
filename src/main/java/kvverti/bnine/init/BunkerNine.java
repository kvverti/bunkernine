package kvverti.bnine.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import kvverti.bnine.proxy.Proxy;

import static kvverti.bnine.init.Meta.*;

@Mod(modid = ID, name = NAME, version = VERSION)
public final class BunkerNine {

	@Instance(ID)
	public static BunkerNine instance;

	@SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
	public static Proxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {

		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {

		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {

		proxy.postInit();
	}
}
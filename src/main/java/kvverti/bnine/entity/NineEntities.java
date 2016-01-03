package kvverti.bnine.entity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.entity.RenderManager;

import kvverti.bnine.entity.model.*;
import kvverti.bnine.entity.model.render.*;
import kvverti.bnine.init.Registry;
import kvverti.bnine.item.ItemNineSpawnEgg;

public final class NineEntities {

	private NineEntities() { }

	public static void register() {

		Registry.registerEntity(EntityDebugAxis.class);
		Registry.registerEntity(EntityWitherStump.class);
	}

	public static void registerEggs() {

		ItemNineSpawnEgg.addEntity(EntityWitherStump.class);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenderers() {

		RenderManager manager = Registry.getRenderManager();

		Registry.registerEntityRenderer(EntityDebugAxis.class, new RenderDebugAxis(manager));
		Registry.registerEntityRenderer(EntityWitherStump.class, new RenderWitherStump(manager, new ModelWitherStump(), 0.0f));
	}
}
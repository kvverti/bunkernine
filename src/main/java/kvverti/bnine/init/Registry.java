package kvverti.bnine.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.ModelLoader;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import kvverti.bnine.util.*;

public class Registry {

	public static int entID = 0;

	public static void registerBlock(Block block) {

		GameRegistry.registerBlock(block, ((StringID) block).id());
	}

	public static void registerBlock(Block block, Class<? extends ItemBlock> clazz) {

		GameRegistry.registerBlock(block, clazz, ((StringID) block).id());
	}

	public static void registerBlock(Block block, Class<? extends ItemBlock> clazz, Object... args) {

		GameRegistry.registerBlock(block, clazz, ((StringID) block).id(), args);
	}

	@SideOnly(Side.CLIENT)
	public static void ignoreStates(Block block, IProperty... ignoreStates) {

		ModelLoader.setCustomStateMapper(block, new StateMap.Builder().addPropertiesToIgnore(ignoreStates).build());
	}

	public static void registerItem(Item item) {

		GameRegistry.registerItem(item, ((StringID) item).id());
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemRenderer(Block block) {

		registerItemRenderer(block, 0);
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemRenderer(Block block, int meta) {

		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Meta.ID + ":" + ((StringID) block).id(), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemRenderer(Item item) {

		registerItemRenderer(item, 0);
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemRenderer(Item item, int meta) {

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Meta.ID + ":" + ((StringID) item).id(), "inventory"));
	}

	public static void registerTileEntity(Class<? extends TileEntity> clazz) {

		GameRegistry.registerTileEntity(clazz, clazz.getAnnotation(ClassID.class).value());
	}

	@SideOnly(Side.CLIENT)
	public static void registerReloadListener(IResourceManagerReloadListener listener) {

		IResourceManager manager = Minecraft.getMinecraft().getResourceManager();

    		((IReloadableResourceManager) manager).registerReloadListener(listener);
	}

	@SideOnly(Side.CLIENT)
	public static RenderManager getRenderManager() {

		return Minecraft.getMinecraft().getRenderManager();
	}

	public static void registerEntity(Class<? extends Entity> clazz) {

		EntityRegistry.registerModEntity(clazz, clazz.getAnnotation(ClassID.class).value(), entID++, BunkerNine.instance, 64, 3, false);
	}

	@SideOnly(Side.CLIENT)
	public static void registerEntityRenderer(Class<? extends Entity> clazz, Render renderer) {

		RenderingRegistry.registerEntityRenderingHandler(clazz, renderer);
	}
}
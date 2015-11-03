package kvverti.bnine.client;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.google.gson.*;

import kvverti.bnine.init.Meta;
import kvverti.bnine.item.NineLightColor;
import kvverti.bnine.util.Logger;

@SideOnly(Side.CLIENT)
public class Resources implements IResourceManagerReloadListener {

	private static final String[] BLOCK_KEYS = { "mazewood_leaves" };
	private static final String[] EGG_KEYS = { "witherstump" };
	private static final ResourceLocation COLORS = new ResourceLocation(Meta.ID + ":colors.json");

	private Map<NineLightColor, String> colorsFluorescent = new EnumMap<NineLightColor, String>(NineLightColor.class);
	private Map<String, String> colorsBlock = new HashMap<String, String>(16);
	private Map<String, String> colorsEggPrimary = new HashMap<String, String>(16);
	private Map<String, String> colorsEggSecondary = new HashMap<String, String>(16);

	public static final Resources INSTANCE = new Resources();

	private Resources() {

		super();
	}

	public String getColorFluorescent(NineLightColor color) {

		return colorsFluorescent.get(color);
	}

	public String getColorFoliage(String id) {

		return colorsBlock.get(id);
	}

	public String getColorEgg(String entity, int pass) {

		if(pass == 0) return colorsEggPrimary.get(entity);
		if(pass == 1) return colorsEggSecondary.get(entity);

		return "ffffff";
	}

	@Override
	public void onResourceManagerReload(IResourceManager manager) {

		Logger.info("Reloading resources...");

		reloadColorsResource(manager);

		Logger.info("Reload complete");
	}

	@SuppressWarnings("unchecked")
	private void reloadColorsResource(IResourceManager manager) {

		InputStream in = null;
		BufferedReader reader = null;
		List<IResource> allColors = null;

		try {
			allColors = manager.getAllResources(COLORS);

		} catch(IOException e) { return; }

		for(IResource resource : allColors) {

			try {
				in = resource.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in));
				JsonObject json = new Gson().fromJson(reader, JsonElement.class).getAsJsonObject();
				JsonElement colElem;

				JsonObject colorObject = json.getAsJsonObject("fluorescent");
				if(colorObject != null) {

					for(NineLightColor c : NineLightColor.values()) {

						colElem = colorObject.get(c.getName());
						if(colElem != null && colElem.isJsonPrimitive()) {

							colorsFluorescent.put(c, colElem.getAsString());
						}
					}
				}

				colorObject = json.getAsJsonObject("block");
				if(colorObject != null) {

					for(String s : BLOCK_KEYS) {

						colElem = colorObject.get(s);
						if(colElem != null && colElem.isJsonPrimitive()) {

							colorsBlock.put(s, colElem.getAsString());
						}
					}
				}

				colorObject = json.getAsJsonObject("egg");
				if(colorObject != null) {

					for(String s : EGG_KEYS) {

						colElem = colorObject.get(s);
						if(colElem != null && colElem.isJsonArray()) {

							colorsEggPrimary.put(s, ((JsonArray) colElem).get(0).getAsString());
							colorsEggSecondary.put(s, ((JsonArray) colElem).get(1).getAsString());
						}
					}
				}

			} catch(RuntimeException e) {

				Logger.error("Caught exception reading resource file (%s); aborting this iteration", e);

			} finally {

				try {
					if(reader != null) reader.close();
					if(in != null) in.close();

				} catch(IOException ex) { }
			}
		}
	}
}
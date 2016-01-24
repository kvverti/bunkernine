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
import com.google.common.base.Function;

import kvverti.bnine.init.Meta;
import kvverti.bnine.util.LightColor;
import kvverti.bnine.util.Logger;
import kvverti.bnine.util.StringID;

@SideOnly(Side.CLIENT)
public final class Resources implements IResourceManagerReloadListener {

	//block keys are the internal ID of the block, in lowercase
	private static final String[] BLOCK_KEYS = { "mazewood_leaves" };
	//egg keys are the internal name of the entity, in lowercase
	private static final String[] EGG_KEYS = { "witherstump" };
	private static final ResourceLocation COLORS = new ResourceLocation(Meta.ID + ":colors.json");

	private final Map<LightColor, String> colorsFluor = new HashMap<>(16);
	private final Map<String, String> colorsBlock = new HashMap<>(16);
	private final Map<String, String[]> colorsEgg = new HashMap<>(16);

	public static final Resources INSTANCE = new Resources();

	private Resources() { }

	private int parseInt(String value, int radix, int _default) {

		try {
			return Integer.parseInt(value, radix);

		} catch(NumberFormatException e) {

			return _default;
		}
	}

	public int getColorFluorescent(LightColor color) {

		return parseInt(colorsFluor.get(color), 16, 0xffffff);
	}

	public int getColorBlock(String id) {

		return parseInt(colorsBlock.get(id), 16, 0xffffff);
	}

	public int getColorEgg(String entity, int pass) {

		String[] s = colorsEgg.get(entity);
		return parseInt(pass < s.length ? s[pass] : s[0], 16, 0xffffff);
	}

	@Override
	public void onResourceManagerReload(IResourceManager manager) {

		Logger.info("Reloading resources...");

		reloadColorsResource(manager);

		Logger.info("Reload complete");
	}

	@SuppressWarnings("unchecked")
	private void reloadColorsResource(IResourceManager manager) {

		List<IResource> allColors = null;

		try {
			allColors = manager.getAllResources(COLORS);

		} catch(IOException e) { return; }

		clearMaps();
		for(IResource resource : allColors) {

			try(
			InputStream in = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in))
			) {
				JsonObject json = new Gson().fromJson(reader, JsonElement.class).getAsJsonObject();

				fillMap(json.getAsJsonObject("fluorescent"), LightColor.valuesArray(), StringID_id, colorsFluor);
				fillMap(json.getAsJsonObject("block"), BLOCK_KEYS, Object_toString, colorsBlock);
				fillMapWithArray(json.getAsJsonObject("egg"), EGG_KEYS, Object_toString, colorsEgg);

			} catch(JsonParseException e) {

				Logger.error(e, "Caught exception reading resource file:");

			  //thrown by close()
			} catch(IOException e) {

				Logger.warn("IO-error occured closing file");
			}
		}
	}

	private void clearMaps() {

		colorsFluor.clear();
		colorsBlock.clear();
		colorsEgg.clear();
	}

	private <T> void fillMap(JsonObject out, T[] keys, Function<? super T, String> func, Map<T, String> in) {

		if(out != null) {

			JsonElement elem;
			for(T t : keys) {

				elem = out.get(func.apply(t));
				if(elem != null && elem.isJsonPrimitive()) {

					in.put(t, elem.getAsString());
				}
			}
		}
	}

	private <T> void fillMapWithArray(JsonObject out, T[] keys, Function<? super T, String> func, Map<T, String[]> in) {

		if(out != null) {

			JsonElement elem;
			String[] array;
			int length;
			for(T t : keys) {

				elem = out.get(func.apply(t));
				if(elem != null && elem.isJsonArray()) {

					length = elem.getAsJsonArray().size();
					array = new String[length > 0 ? length : 1];
					for(int i = 0; i < length; i++) {

						array[i] = elem.getAsJsonArray().get(i).getAsString();
					}
					in.put(t, array);
				}
			}
		}
	}

	//StringID::id
	private static final Function<StringID, String> StringID_id = new Function<StringID, String>() {

		@Override
		public String apply(StringID obj) {

			return obj.id();
		}
	};

	//Object::toString
	private static final Function<Object, String> Object_toString = new Function<Object, String>() {

		@Override
		public String apply(Object obj) {

			return obj.toString();
		}
	};
}
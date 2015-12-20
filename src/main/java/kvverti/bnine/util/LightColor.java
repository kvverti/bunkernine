package kvverti.bnine.util;

import java.util.List;
import java.util.ArrayList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import kvverti.bnine.client.Resources;

//Under construction!!!
public final class LightColor implements StringID {

	/*
	 * The "null" color. This LightColor has the name "null"
	 * and the meta value -1.
	 */
	public static final LightColor NULL = new LightColor(-1, "null");
	private static final List<LightColor> values = new ArrayList<>(24);
	private static boolean closed;
	private static int nextFreeMeta;

	static {

		register(0, "white");
		register(1, "orange");
		register(2, "magenta");
		register(3, "lightBlue");
		register(4, "yellow");
		register(5, "lime");
		register(6, "pink");
		register(9, "cyan");
		register(10, "purple");
		register(11, "blue");
		register(12, "brown");
		register(13, "green");
		register(14, "red");
		register(16, "peach");
	}

	private final int index;
	private final String name;

	private LightColor(int i, String s) {

		index = i;
		name = s;
	}

	/*
	 * Returns the String name of this LightColor.
	 */
	@Override
	public String id() {

		return name;
	}

	@Override
	public String toString() {

		return String.format("Light color %s (%d)", name, index);
	}

	/*
	 * Returns the int representation of this LightColor.
	 * This is generally an item damage value.
	 */
	public int metadata() {

		return index;
	}

	/*
	 * Returns the hex color associated with this LightColor.
	 * This value may change when resources are reloaded.
	 */
	/*@SideOnly(Side.CLIENT)
	public int getClientColor() {

		return Resources.INSTANCE.getColorFluorescent(this);
	}*/

	/*
	 * Returns an array containing all LightColor instances.
	 * This array does not contain NULL.
	 */
	public static LightColor[] values() {

		return values.toArray(new LightColor[0]);
	}

	/*
	 * Returns the LightColor with the given metadata.
	 * If no such color exists, NULL is returned.
	 */
	public static LightColor byMetadata(int meta) {

		for(LightColor c : values) {

			if(c.index == meta) return c;
		}
		return NULL;
	}

	/*
	 * Returns the LightColor with the given name.
	 * If no such color exists, NULL is returned.
	 */
	public static LightColor byName(String name) {

		for(LightColor c : values) {

			if(c.name.equals(name)) return c;
		}
		return NULL;
	}

	/*
	 * Returns whether the given int is used as a metadata value.
	 * Note that this will also return true for reserved meta values.
	 */
	public static boolean isMetaUsed(int i) {

		return LightColor.byMetadata(i) != NULL || i == 7 || i == 8 || i == 15;
	}

	/*
	 * Returns whether the given String is used as a name.
	 * Note that this will also return true for reserved names.
	 */
	public static boolean isNameUsed(String s) {

		return LightColor.byName(s) != NULL || "null".equals(s);
	}

	/*
	 * This method is called automatically in the post-init phase of mod loading.
	 * After closure, no new LightColor objects may be created. Users should
	 * not call this method.
	 */
	public static void close() {

		closed = true;
	}

	/*
	 * Returns a LightColor with the specified name. If the name
	 * is not present as a color, a new color is created with the
	 * specified name, otherwise the color with the matching
	 * name is returned. The meta value is used as the metadata
	 * value of the color if possible, otherwise the value returned
	 * by nextFreeMeta() is used.
	 * Parameters:
	 *	meta - the metadata value to use, if free.
	 *	name - the name of the color to create and/or return.
	 * Returns: A LightColor with the specified name.
	 * Throws:
	 * 	IllegalStateException - if the class is closed.
	 * 	NullPointerException - if name is null.
	 * 	IllegalArgumentException - If meta is negative.
	 */
	public static LightColor register(int meta, String name) {

		if(closed) throw new IllegalStateException("Class LightColor is closed");
		if(name == null || name.equals("")) throw new NullPointerException(name);
		if(meta < 0) throw new IllegalArgumentException("Meta must be positive");

		LightColor color = !isNameUsed(name) ?
			new LightColor(isMetaUsed(meta) ? nextFreeMeta() : meta, name)
			: byName(name);

		values.add(color);
		return color;
	}

	public static int nextFreeMeta() {

		while(isMetaUsed(nextFreeMeta)) {

			nextFreeMeta++;
		}
		return nextFreeMeta;
	}
}
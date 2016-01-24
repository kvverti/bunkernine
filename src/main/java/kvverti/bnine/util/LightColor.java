package kvverti.bnine.util;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import kvverti.bnine.block.NineBlocks;
import kvverti.bnine.client.Resources;
import kvverti.bnine.init.Registry;

/**
 * Instances of the LightColor class define colors used by Bunker 9's fluorescent lights.
 * Instances of this class are immutable. The only way to create a LightColor is through
 * the static {@link #register} method, which creates and adds a LightColor to a
 * universal registry returned by the {@link #values()} and {@link #valuesArray} methods.
 */
public final class LightColor implements StringID, Comparable<LightColor> {

	/**
	 * The "null" color. This LightColor has the name "null"
	 * and the meta value -1. This value should be used in place
	 * of {@code null} for indicating an undefined or default color.
	 */
	public static final LightColor NULL = new LightColor(-1, "null");
	private static final Set<LightColor> values = new HashSet<>();
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

	@Override
	public String id() {

		return name;
	}

	/**
	 * Returns a String describing this LightColor. The returned String
	 * is the sequence {@code LightColor} followed by a space, the name,
	 * a space, and the metadata value in parentheses. For example, the
	 * LightColor white will return {@code LightColor white (0)}.
	 *
	 * @return A String describing this LightColor.
	 * @see #id
	 */
	@Override
	public String toString() {

		return String.format("LightColor %s (%d)", name, index);
	}

	/**
	 * Returns the item damage value associated with this LightColor.
	 *
	 * @return The item damage vaue associated with this LightColor
	 */
	public int metadata() {

		return index;
	}

	/**
	 * Returns the RGB hex color associated with this LightColor.
	 * This value may change when resources are reloaded. Color
	 * values are specified in the file {@code bunkernine:colors.json}
	 * under the compound {@code fluorescent}. This color's String ID
	 * is used as the key and a String of hexidecimal digits representing
	 * RGB color is the value.
	 *
	 * @return The RGB hex color associated with this LightColor
	 * @see Resources#getColorFluorescent(LightColor)
	 */
	@SideOnly(Side.CLIENT)
	public int getClientColor() {

		return Resources.INSTANCE.getColorFluorescent(this);
	}

	/**
	 * Compares this LightColor to the specified LightColor. This is
	 * done by comparing the two instances' metadata values. In a normal
	 * session, no two distinct LightColors will be equal.
	 *
	 * @param color The color to be compared
	 * @return A positive, zero, or negative integer if this color is
	 *	greater than, equal to, or less than the specified color,
	 *	respectively.
	 */
	@Override
	public int compareTo(LightColor color) {

		return this.metadata() - color.metadata();
	}

	/**
	 * Compares this LightColor to the specified Object for equality.
	 * Returns true if the object is also a LightColor and
	 * {@code this.compareTo(o) == 0}.
	 *
	 * @param o The Object to compare with this for equality
	 * @return Whether the specified Object is equal to this
	 * @see #compareTo
	 */
	@Override
	public boolean equals(Object o) {

		return o instanceof LightColor && this.compareTo((LightColor) o) == 0;
	}

	/**
	 * Returns this object's hash code. The hash code is the value
	 * returned by {@link #metadata}.
	 *
	 * @return The hash code
	 */
	@Override
	public int hashCode() {

		return metadata();
	}

	/**
	 * Returns a List containing all LightColor instances.
	 * This List does not contain {@link #NULL} and can be modified.
	 * Changes to this List are not backed internally.
	 *
	 * @return A List containing all registered LightColors
	 * @see #valuesArray
	 */
	public static List<LightColor> values() {

		return new ArrayList<>(values);
	}

	/**
	 * Convenience method returning an array of all LightColor instances.
	 *
	 * @return An array containing all registered LightColors
	 * @see #values()
	 */
	public static LightColor[] valuesArray() {

		return values.toArray(new LightColor[values.size()]);
	}

	/**
	 * Returns the LightColor with the given metadata.
	 * If no such color exists, {@link #NULL} is returned.
	 *
	 * @param meta The metadata value of the LightColor
	 * @return A LightColor with the specified value.
	 */
	public static LightColor byMetadata(int meta) {

		for(LightColor c : values) {

			if(c.index == meta) return c;
		}
		return NULL;
	}

	/**
	 * Returns the LightColor with the given name.
	 * If no such color exists, {@link #NULL} is returned.
	 *
	 * @param name The String ID of the LightColor
	 * @return A LightColor with the specified name.
	 */
	public static LightColor byName(String name) {

		for(LightColor c : values) {

			if(c.name.equals(name)) return c;
		}
		return NULL;
	}

	/**
	 * Returns whether the given value is associated with any LightColor.
	 * More specifically, this returns true for any value {@code v} such that,
	 * for one {@code c} in {@link #values()}, {@code c.metadata()} returns {@code v}.
	 * Note that this will also return true for reserved metadata values.
	 *
	 * @param i The meta value to test against existing LightColors
	 * @return Whether the specified value is associated with a LightColor
	 * @see #nextFreeMeta()
	 */
	public static boolean isMetaUsed(int i) {

		return LightColor.byMetadata(i) != NULL || i == 7 || i == 8 || i == 15 || i == -1;
	}

	/**
	 * Returns whether the given String is used as a name for any LightColor.
	 * More specifically, this returns true for any String {@code s} such that,
	 * for one {@code c} in {@link #values()}, {@code c.id()} returns {@code s}.
	 * Note that this will also return true for reserved names.
	 *
	 * @param s The name to test against existing LightColors
	 * @return Whether the specified name is a LightColor ID
	 */
	public static boolean isNameUsed(String s) {

		return LightColor.byName(s) != NULL || "null".equals(s);
	}

	/**
	 * Disables new LightColor creation. This method is called automatically
	 * in the post-init phase of mod loading. This method also registers item
	 * renderers for each registered LightColor. Users should not invoke this
	 * method.
	 */
	public static void close() {

		closed = true;
		for(LightColor c : values) {

			Registry.registerItemRenderer(NineBlocks.fluorescentLight, c.metadata());
		}
	}

	/**
	 * Returns a LightColor with the specified name. If the name
	 * is not present as a color, a new color is created with the
	 * specified name, otherwise the color with the matching
	 * name is returned. The meta value is used as the metadata
	 * value of the color if possible, otherwise the value returned
	 * by {@link #nextFreeMeta()} is used.
	 *
	 * @param meta The item metadata value to associate with the color
	 * @param name The String name of the color to register
	 * @return A LightColor with the specified name
	 * @throws IllegalStateException If the class is closed
	 * @throws NullPointerException If name is null
	 * @throws IndexOutOfBoundsException If meta is negative
	 */
	public static LightColor register(int meta, String name) {

		if(closed) throw new IllegalStateException("Class LightColor is closed");
		if(name == null || name.equals("")) throw new NullPointerException(name);
		if(meta < 0) throw new IndexOutOfBoundsException(Integer.toString(meta));

		LightColor color = !isNameUsed(name) ?
			new LightColor(isMetaUsed(meta) ? nextFreeMeta() : meta, name)
			: byName(name);

		values.add(color);
		return color;
	}

	/**
	 * Returns the smallest meta value not already used by any existing
	 * LightColor. Assuming there are no gaps in the registered colors,
	 * users can use this method to ensure consecutive numeric IDs for
	 * their colors:
	 * <pre>
	 * {@code
	 *	String[] names = ...
	 *	int metaValue = LightColor.nextFreeMeta();
	 *
	 *	for(String name : names) {
	 *
	 *		LightColor.register(metaValue++, name);
	 *	}
	 * }
	 * </pre>
	 *
	 * @return The smallest item metadata value not associated with an
	 *	existing LightColor
	 * @see #isMetaUsed
	 */
	public static int nextFreeMeta() {

		while(isMetaUsed(nextFreeMeta)) {

			nextFreeMeta++;
		}
		return nextFreeMeta;
	}
}
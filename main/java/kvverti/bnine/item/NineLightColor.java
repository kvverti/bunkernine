package kvverti.bnine.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.util.IStringSerializable;

import kvverti.bnine.client.Resources;

public enum NineLightColor implements IStringSerializable {

	WHITE		( 0, "white"	),
	ORANGE		( 1, "orange"	),
	MAGENTA		( 2, "magenta"	),
	LIGHT_BLUE	( 3, "lightBlue"),
	YELLOW		( 4, "yellow"	),
	LIME		( 5, "lime"	),
	PINK		( 6, "pink"	),
	CYAN		( 9, "cyan"	),
	PURPLE		(10, "purple"	),
	BLUE		(11, "blue"	),
	BROWN		(12, "brown"	),
	GREEN		(13, "green"	),
	RED		(14, "red"	),
	PEACH		(16, "peach"	),
	NULL		(-1, "null"	);

	public static final int MAX_ORDINAL = 16;

	private final int index;
	private final String name;

	private NineLightColor(int i, String s) {

		index = i;
		name = s;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public String toString() {

		return name;
	}

	public int getMetadata() {

		return index;
	}

	@SideOnly(Side.CLIENT)
	public int getClientColor() {

		return Resources.INSTANCE.getColorFluorescent(this);
	}

	public static NineLightColor byMetadata(int meta) {

		for(NineLightColor c : values()) {

			if(c.index == meta) return c;
		}

		return NULL;
	}

	public static NineLightColor byName(String name) {

		for(NineLightColor c : values()) {

			if(c.name.equals(name)) return c;
		}

		return NULL;
	}

	public static boolean isUsedValue(int i) {

		if(i < 0 || i > MAX_ORDINAL) return false;
		if(i == 7 || i == 8 || i == 15) return false;
		return true;
	}
}
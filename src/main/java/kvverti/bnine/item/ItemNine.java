package kvverti.bnine.item;

import net.minecraft.item.Item;

import kvverti.bnine.util.StringID;

public class ItemNine extends Item implements StringID {

	private final String stringID;

	public ItemNine(String id) {

		super();
		stringID = id;
		setCreativeTab(NineItems.tabNineItems);
	}

	@Override
	public String id() {

		return stringID;
	}
}
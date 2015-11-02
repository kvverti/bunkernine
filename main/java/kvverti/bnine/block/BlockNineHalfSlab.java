package kvverti.bnine.block;

import net.minecraft.block.material.*;

public class BlockNineHalfSlab extends BlockNineSlab {

	public BlockNineHalfSlab(String id, Material material) {

		super(id, material);
	}

	public BlockNineHalfSlab(String id, Material material, String tool, int tier) {

		super(id, material, tool, tier);
	}

	@Override
	public boolean isDouble() {

		return false;
	}
}
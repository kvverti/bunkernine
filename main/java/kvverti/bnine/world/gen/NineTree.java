package kvverti.bnine.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import kvverti.bnine.block.NineBlocks;

/*
 * WorldGenerator functions
 * void #func_175903_a(World, BlockPos, IBlockState) set block state in world
 * void #func_175905_a(World, BlockPos, Block, int) set block state from meta in world
 * void #func_175906_a(World, BlockPos, Block) set block with default state (meta 0) in world
 *
 * AbstractTree functions
 * void #func_175921_a(World, BlockPos) set block at pos to dirt
 * boolean #isReplaceable(World, BlockPos) is block at pos replaceable
 */

public class NineTree extends WorldGenAbstractTree {

	private final Block logBlock;
	private final Block leavesBlock;
	private final int minHeight;

	public NineTree(Block log, Block leaves, int minHeight, boolean notifyBlocks) {

		super(notifyBlocks);
		logBlock = log;
		leavesBlock = leaves;
		this.minHeight = minHeight;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		int height = rand.nextInt(4) + minHeight;
		BlockPos leafOrigin = pos.add(0, height - 1, 0);

		if(!verify(world, pos, leafOrigin, height)) return false;

		for(int x = -2; x <= 2; x++) {

			for(int y = -2; y <= 1; y++) {

				for(int z = -2; z <= 2; z++) {

					switch(y) {

						case -2:
						case -1:
							if(MathHelper.abs_int(x) != 2 || MathHelper.abs_int(z) != 2 || rand.nextInt(4) < 2) func_175903_a(world, leafOrigin.add(x, y, z), leavesBlock.getDefaultState());
							break;

						case 0:
							if(MathHelper.abs_int(x) < 2 && MathHelper.abs_int(z) < 2) {

								if(MathHelper.abs_int(x) != MathHelper.abs_int(z) || rand.nextInt(4) < 2) func_175903_a(world, leafOrigin.add(x, y, z), leavesBlock.getDefaultState());
							}
							break;

						case 1:

							if(MathHelper.abs_int(x) < 2 && MathHelper.abs_int(z) < 2) {

								if(MathHelper.abs_int(x) != MathHelper.abs_int(z) || (x == 0 && z == 0)) func_175903_a(world, leafOrigin.add(x, y, z), leavesBlock.getDefaultState());
							}
							break;
					}
				}
			}
		}

		for(int i = 0; i < height; i++) {

			func_175903_a(world, pos.add(0, i, 0), logBlock.getDefaultState());
		}
		return true;
	}

	@Override
	protected boolean func_150523_a(Block block) {

		return super.func_150523_a(block) || block == NineBlocks.logMazewood;
	}

	public boolean verify(World world, BlockPos origin, BlockPos leafOrigin, int trunkHeight) {

		for(int i = 0; i < trunkHeight; i++) {

			if(world.getBlockState(origin.add(0, i, 0)).getBlock() != Blocks.air) {

				return false;
			}
		}

		for(int x = -2; x <= 2; x++) {

			for(int y = -2; y <= 1; y++) {

				for(int z = -2; z <= 2; z++) {

					if(!isReplaceable(world, leafOrigin.add(x, y, z))) {

						return false;
					}
				}
			}
		}
		return true;
	}
}
package kvverti.bnine.item;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.StatCollector;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import kvverti.bnine.client.Resources;
import kvverti.bnine.init.Meta;
import kvverti.bnine.util.ClassID;
import kvverti.bnine.util.StringID;

public class ItemNineSpawnEgg extends ItemNine {

	static final List<Class<? extends Entity>> ENTITY_TYPES = new ArrayList<>(16);
	static final List<String> ENTITY_NAMES = new ArrayList<>(16);

	public static void addEntity(Class<? extends Entity> clazz) {

		if(!ENTITY_TYPES.contains(clazz)) {

			ENTITY_TYPES.add(clazz);
			ENTITY_NAMES.add(clazz.getAnnotation(ClassID.class).value());
		}
	}

	public ItemNineSpawnEgg(String id) {

		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {

		for(int i = 0; i < ENTITY_TYPES.size(); i++) {

			subItems.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderpass) {

		String entityName = ENTITY_NAMES.get(stack.getMetadata()).toLowerCase();
		return Resources.INSTANCE.getColorEgg(entityName, renderpass);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		String eggBaseName = getUnlocalizedName() + ".name";
		String entityName = stack.getMetadata() < ENTITY_TYPES.size() ?
			StatCollector.translateToLocal(
				"entity." + Meta.ID + "."
				+ ENTITY_NAMES.get(stack.getMetadata()) + ".name"
			) : "Nothing";

		return StatCollector.translateToLocalFormatted(eggBaseName, entityName);
	}

	public Entity spawnCreature(World world, int entityIndex, double x, double y, double z) {

		Class<? extends Entity> entityClass = entityIndex < ENTITY_TYPES.size() ? ENTITY_TYPES.get(entityIndex) : null;
		if(entityClass == null) return null;

		Entity entity = null;
		try {
			entity = entityClass.getConstructor(World.class).newInstance(world);

		} catch(Exception e) { return null; }

		entity.setLocationAndAngles(x, y, z, 0.0f, 0.0f);
		world.spawnEntityInWorld(entity);

		if(entity instanceof EntityLiving) ((EntityLiving) entity).playLivingSound();
		return entity;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float x, float y, float z) {

		if(!player.canPlayerEdit(pos.offset(side), side, stack)) {

			return false;

		} else {

			if(world.isRemote) return true;

			Block block = world.getBlockState(pos).getBlock();
			double vertOffset = 0.0;

			if(side == EnumFacing.UP && (block instanceof BlockFence || block instanceof BlockFenceGate)) {

				vertOffset = 0.5;
			}

			pos = pos.offset(side);

			Entity e = spawnCreature(world, stack.getMetadata(), pos.getX() + 0.5, pos.getY() + vertOffset, pos.getZ() + 0.5);

			if(e != null) {

				if(stack.hasDisplayName()) e.setCustomNameTag(stack.getDisplayName());
				stack.stackSize--;
			}
			return true;
		}
	}
}
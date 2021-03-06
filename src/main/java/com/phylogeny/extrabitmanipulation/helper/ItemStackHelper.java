package com.phylogeny.extrabitmanipulation.helper;

import mod.chiselsandbits.api.ItemType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.phylogeny.extrabitmanipulation.api.ChiselsAndBitsAPIAccess;
import com.phylogeny.extrabitmanipulation.item.ItemBitToolBase;
import com.phylogeny.extrabitmanipulation.item.ItemBitWrench;
import com.phylogeny.extrabitmanipulation.item.ItemChiseledArmor;
import com.phylogeny.extrabitmanipulation.item.ItemModelingTool;
import com.phylogeny.extrabitmanipulation.item.ItemSculptingTool;
import com.phylogeny.extrabitmanipulation.reference.NBTKeys;

public class ItemStackHelper
{
	
	public static void saveStackToNBT(NBTTagCompound nbt, ItemStack stack, String key)
	{
		NBTTagCompound nbt2 = new NBTTagCompound();
		stack.writeToNBT(nbt2);
		nbt.setTag(key, nbt2);
	}
	
	public static ItemStack loadStackFromNBT(NBTTagCompound nbt, String key)
	{
		ItemStack stack = ItemStack.EMPTY;
		if (nbt.hasKey(key))
			stack = new ItemStack((NBTTagCompound) nbt.getTag(key));
		
		return stack;
	}
	
	@SuppressWarnings("null")
	public static boolean hasKey(ItemStack stack, String key)
	{
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(key);
	}
	
	public static NBTTagCompound getNBT(ItemStack stack)
	{
		return stack.getTagCompound();
	}
	
	public static NBTTagCompound getNBTOrNew(ItemStack stack)
	{
		return stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
	}
	
	public static boolean isModelingToolStack(ItemStack stack)
	{
		return isModelingToolItem(stack.getItem());
	}
	
	public static boolean isModelingToolItem(Item item)
	{
		return item != null && item instanceof ItemModelingTool;
	}
	
	public static boolean isSculptingToolStack(ItemStack stack)
	{
		return isSculptingToolItem(stack.getItem());
	}
	
	public static boolean isSculptingToolItem(Item item)
	{
		return item != null && item instanceof ItemSculptingTool;
	}
	
	public static boolean isBitToolStack(ItemStack stack)
	{
		return isBitToolItem(stack.getItem());
	}
	
	public static boolean isBitToolItem(Item item)
	{
		return item != null && item instanceof ItemBitToolBase;
	}
	
	public static boolean isBitWrenchStack(ItemStack stack)
	{
		return isBitWrenchItem(stack.getItem());
	}
	
	public static boolean isBitWrenchItem(Item item)
	{
		return item != null && item instanceof ItemBitWrench;
	}
	
	public static boolean isChiseledArmorStack(ItemStack stack)
	{
		return isChiseledArmorItem(stack.getItem());
	}
	
	public static boolean isChiseledArmorItem(Item item)
	{
		return item != null && item instanceof ItemChiseledArmor;
	}
	
	public static boolean isDesignStack(ItemStack stack)
	{
		return isDesignItemType(ChiselsAndBitsAPIAccess.apiInstance.getItemType(stack));
	}
	
	public static boolean isDesignItemType(ItemType itemType)
	{
		return itemType == ItemType.MIRROR_DESIGN || itemType == ItemType.NEGATIVE_DESIGN || itemType == ItemType.POSITIVE_DESIGN;
	}
	
	public static NBTTagCompound getArmorData(NBTTagCompound armorNbt)
	{
		return armorNbt.getCompoundTag(NBTKeys.ARMOR_DATA);
	}
	
}
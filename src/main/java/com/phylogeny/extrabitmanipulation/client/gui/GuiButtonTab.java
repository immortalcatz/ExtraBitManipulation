package com.phylogeny.extrabitmanipulation.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.phylogeny.extrabitmanipulation.client.ClientHelper;
import com.phylogeny.extrabitmanipulation.client.render.RenderState;

public class GuiButtonTab extends GuiButtonBase
{
	private ItemStack iconStack = ItemStack.EMPTY;
	private float u, v;
	private int uWidth, vHeight, uOriginTab, vOriginTab, textureSize;
	private ResourceLocation texture;
	private IBakedModel[] iconModels;
	
	public GuiButtonTab(int buttonId, int x, int y, int width, int height, String hoverText, float u, float v, int uWidth,
			int vHeight, int uOriginTab, int vOriginTab, int textureSize, ResourceLocation texture, IBakedModel... iconModels)
	{
		this(buttonId, x, y, width, height, hoverText, ItemStack.EMPTY, u, v, uWidth, vHeight, uOriginTab, vOriginTab, textureSize, texture);
		this.iconModels = iconModels;
	}
	
	public GuiButtonTab(int buttonId, int x, int y, int width, int height, String hoverText, ItemStack iconStack, float u,
			float v, int uWidth, int vHeight, int uOriginTab, int vOriginTab, int textureSize, ResourceLocation texture)
	{
		super(buttonId, x, y, width, height, "", hoverText);
		this.u = u;
		this.v = v;
		this.uWidth = uWidth;
		this.vHeight = vHeight;
		this.iconStack = iconStack;
		this.uOriginTab = uOriginTab;
		this.vOriginTab = vOriginTab;
		this.textureSize = textureSize;
		this.texture = texture;
		setSilent(true);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		if (!visible)
			return;
		
		super.drawButton(mc, mouseX, mouseY, partialTicks);
		ClientHelper.bindTexture(texture);
		float color = enabled ? 1.0F : 0.5F;
		GlStateManager.color(color, color, color);
		int width = 29;
		int height = 26;
		int x = this.x;
		int u = uOriginTab;
		if (selected && enabled)
		{
			x -= 2;
			u += 25;
		}
		else
		{
			width -= 5;
		}
		drawScaledCustomSizeModalRect(x, y, u, vOriginTab, width, height, width, height, textureSize, textureSize);
		if (uWidth > 0 && vHeight > 0)
		{
			ClientHelper.bindTexture(texture);
			drawScaledCustomSizeModalRect(this.x + 4 + getOffsetX(), y + 4, this.u, v, uWidth, vHeight, 19, 18, textureSize, textureSize);
		}
	}

	private int getOffsetX()
	{
		return selected ? -2 : 0;
	}
	
	public ItemStack getIconStack()
	{
		return iconStack;
	}
	
	public void setIconStack(ItemStack iconStack)
	{
		this.iconStack = iconStack;
	}
	
	public void renderIconStack()
	{
		if (iconStack.isEmpty() && iconModels == null)
			return;
		
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5, 0, 0);
		int x = this.x + 5 + getOffsetX();
		int y = this.y + 5;
		float alphaMultiplier;
		if (enabled)
		{
			alphaMultiplier = 1.0F;
		}
		else
		{
			alphaMultiplier = 0.5F;
			GlStateManager.translate(0.5, 0, 0);
		}
		GlStateManager.translate(0, 0, -200);
		GlStateManager.enableDepth();
		if (iconModels == null)
		{
			RenderState.renderStateModelIntoGUI(null, ClientHelper.getRenderItem().getItemModelWithOverrides(iconStack, null, ClientHelper.getPlayer()),
					iconStack, alphaMultiplier, true, false, x - 6, y - 2, 0, 0, 1);
		}
		else
		{
			for (IBakedModel model : iconModels)
				RenderState.renderStateModelIntoGUI(null, model, iconStack, alphaMultiplier, true, false, x - 6, y - 2, 0, 0, 1);
		}
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
	}
	
}
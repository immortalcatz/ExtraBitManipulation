package com.phylogeny.extrabitmanipulation.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class GuiButtonTextured extends GuiButtonBase
{
	private ResourceLocation selectedTexture, deselectedTexture;
	private String selectedHoverText;
	
	public GuiButtonTextured(int buttonId, int x, int y, int widthIn, int heightIn, String hoverText,
			ResourceLocation selectedTexture, ResourceLocation deselectedTexture, SoundEvent boxCheck, SoundEvent boxUncheck)
	{
		this(buttonId, x, y, widthIn, heightIn, hoverText, hoverText, selectedTexture, deselectedTexture, boxCheck, boxUncheck);
	}
	
	public GuiButtonTextured(int buttonId, int x, int y, int widthIn, int heightIn, String hoverText, String selectedHoverText,
			ResourceLocation selectedTexture, ResourceLocation deselectedTexture, SoundEvent boxCheck, SoundEvent boxUncheck)
	{
		super(buttonId, x, y, widthIn, heightIn, "", hoverText, boxCheck, boxUncheck);
		this.selectedHoverText = selectedHoverText;
		this.selectedTexture = selectedTexture;
		this.deselectedTexture = deselectedTexture;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		super.drawButton(mc, mouseX, mouseY, partialTicks);
		if (!visible)
			return;
		
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
		GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(selected ? selectedTexture : deselectedTexture);
		Tessellator t = Tessellator.getInstance();
		BufferBuilder vb = t.getBuffer();
		int offset = 0;
		if (hovered)
			offset = 1;
		
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		double y = this.y - 0.5;
		vb.pos(x - offset, y - offset, 0).tex(0, 0).endVertex();
		vb.pos(x - offset, y + height + offset, 0).tex(0, 1).endVertex();
		vb.pos(x + width + offset, y + height + offset, 0).tex(1, 1).endVertex();
		vb.pos(x + width + offset, y - offset, 0).tex(1, 0).endVertex();
		t.draw();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	
	public String getSelectedHoverText()
	{
		return selectedHoverText;
	}
	
}
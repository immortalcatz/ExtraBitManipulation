package com.phylogeny.extrabitmanipulation.shape;

import net.minecraft.util.AxisAlignedBB;

public class AsymmetricalShape extends Shape
{
	protected float a, b, c, aInset, bInset, cInset;
	
	public void init(float centerX, float centerY, float centerZ, float a, float b, float c, float wallThickness, boolean isSolid)
	{
		init(centerX, centerY, centerZ, wallThickness, isSolid);
		this.a = a; 
		this.b = b;
		this.c = c;
		aInset = reduceLength(a);
		bInset = reduceLength(b);
		cInset = reduceLength(c);
	}
	
	@Override
	protected AxisAlignedBB getBoundingBox()
	{
		return new AxisAlignedBB(centerX - a, centerY - b, centerZ - c,
				centerX + a, centerY + b, centerZ + c);
	}
	
	protected boolean isPointInEllipse(float dv1, float dv2, float s1, float s2)
	{
		float v1 = dv1 / s1;
		float v2 = dv2 / s2;
		return v1 * v1 + v2 * v2 <= 1;
	}
	
}
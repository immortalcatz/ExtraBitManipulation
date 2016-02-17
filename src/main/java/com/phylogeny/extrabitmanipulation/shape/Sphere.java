package com.phylogeny.extrabitmanipulation.shape;

import net.minecraft.util.BlockPos;

public class Sphere extends SymmetricalShape
{
	
	@Override
	public void init(float centerX, float centerY, float centerZ, float radius, float wallThickness, boolean isSolid)
	{
		super.init(centerX, centerY, centerZ, radius, wallThickness, isSolid);
	}
	
	@Override
	public boolean isPointInsideShape(BlockPos pos, int i, int j, int k)
	{
		float dx = getBitPosDiffX(pos, i, centerX);
		float dy = getBitPosDiffY(pos, j, centerY);
		float dz = getBitPosDiffZ(pos, k, centerZ);
		double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
		return isSolid ? dist <= semiDiameter : dist <= semiDiameter && dist > semiDiameterInset;
	}
	
}
package com.phylogeny.extrabitmanipulation.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketBlockInteraction implements IMessage
{
	private BlockPos pos;
	private EnumFacing side;
	private Vec3 hit;
	
	public PacketBlockInteraction() {}
	
	public PacketBlockInteraction(BlockPos pos, EnumFacing side, Vec3 hit)
	{
		this.pos = pos;
		this.side = side;
		this.hit = hit;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(pos.getX());
		buffer.writeInt(pos.getY());
		buffer.writeInt(pos.getZ());
		buffer.writeInt(side.ordinal());
		buffer.writeDouble(hit.xCoord);
		buffer.writeDouble(hit.yCoord);
		buffer.writeDouble(hit.zCoord);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		pos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
		side = EnumFacing.getFront(buffer.readInt());
		hit = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}
	
	public BlockPos getPos()
	{
		return pos;
	}
	
	public EnumFacing getSide()
	{
		return side;
	}
	
	public Vec3 getHit()
	{
		return hit;
	}
	
}
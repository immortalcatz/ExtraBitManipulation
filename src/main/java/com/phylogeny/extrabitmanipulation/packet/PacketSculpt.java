package com.phylogeny.extrabitmanipulation.packet;

import com.phylogeny.extrabitmanipulation.item.ItemSculptingTool;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSculpt extends PacketBlockInteraction implements IMessage
{
	private Vec3 drawnStartPoint;
	
	public PacketSculpt() {}
	
	public PacketSculpt(BlockPos pos, EnumFacing side, Vec3 hit, Vec3 drawnStartPoint)
	{
		super(pos, side, hit);
		this.drawnStartPoint = drawnStartPoint;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		boolean notNull = drawnStartPoint != null;
		buffer.writeBoolean(notNull);
		if (notNull)
		{
			buffer.writeDouble(drawnStartPoint.xCoord);
			buffer.writeDouble(drawnStartPoint.yCoord);
			buffer.writeDouble(drawnStartPoint.zCoord);
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		if (buffer.readBoolean())
			drawnStartPoint = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}
	
	public static class Handler implements IMessageHandler<PacketSculpt, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSculpt message, final MessageContext ctx)
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					EntityPlayer player = ctx.getServerHandler().playerEntity;
					ItemStack stack = player.getCurrentEquippedItem();
					if (stack != null && stack.getItem() instanceof ItemSculptingTool && (!player.isSneaking() || message.drawnStartPoint != null))
						((ItemSculptingTool) stack.getItem()).sculptBlocks(stack, player, player.worldObj,
								message.getPos(), message.getSide(), message.getHit(), message.drawnStartPoint);
				}
			});
			return null;
		}
		
	}
	
}
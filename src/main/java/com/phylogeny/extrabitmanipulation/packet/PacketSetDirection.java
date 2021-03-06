package com.phylogeny.extrabitmanipulation.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.phylogeny.extrabitmanipulation.helper.BitToolSettingsHelper;

public class PacketSetDirection implements IMessage
{
	private int direction;
	
	public PacketSetDirection() {}
	
	public PacketSetDirection(int direction)
	{
		this.direction = direction;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(direction);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		direction = buffer.readInt();
	}
	
	public static class Handler implements IMessageHandler<PacketSetDirection, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetDirection message, final MessageContext ctx)
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					EntityPlayer player = ctx.getServerHandler().player;
					BitToolSettingsHelper.setDirection(player, player.getHeldItemMainhand(), message.direction, null);
				}
			});
			return null;
		}
		
	}
	
}
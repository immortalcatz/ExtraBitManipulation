package com.phylogeny.extrabitmanipulation.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.phylogeny.extrabitmanipulation.helper.BitToolSettingsHelper;

public class PacketSetSemiDiameter implements IMessage
{
	private int semiDiameter;
	
	public PacketSetSemiDiameter() {}
	
	public PacketSetSemiDiameter(int semiDiameter)
	{
		this.semiDiameter = semiDiameter;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(semiDiameter);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		semiDiameter = buffer.readInt();
	}
	
	public static class Handler implements IMessageHandler<PacketSetSemiDiameter, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetSemiDiameter message, final MessageContext ctx)
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					EntityPlayer player = ctx.getServerHandler().player;
					BitToolSettingsHelper.setSemiDiameter(player, player.getHeldItemMainhand(), message.semiDiameter, null);
				}
			});
			return null;
		}
		
	}
	
}
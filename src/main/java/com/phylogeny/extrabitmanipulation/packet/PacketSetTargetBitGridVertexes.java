package com.phylogeny.extrabitmanipulation.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.phylogeny.extrabitmanipulation.helper.BitToolSettingsHelper;

public class PacketSetTargetBitGridVertexes implements IMessage
{
	private boolean targetBitGridVertexes;
	
	public PacketSetTargetBitGridVertexes() {}
	
	public PacketSetTargetBitGridVertexes(boolean targetBitGridVertexes)
	{
		this.targetBitGridVertexes = targetBitGridVertexes;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeBoolean(targetBitGridVertexes);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		targetBitGridVertexes = buffer.readBoolean();
	}
	
	public static class Handler implements IMessageHandler<PacketSetTargetBitGridVertexes, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetTargetBitGridVertexes message, final MessageContext ctx)
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					EntityPlayer player = ctx.getServerHandler().player;
					BitToolSettingsHelper.setBitGridTargeted(player, player.getHeldItemMainhand(), message.targetBitGridVertexes, null);
				}
			});
			return null;
		}
		
	}
	
}
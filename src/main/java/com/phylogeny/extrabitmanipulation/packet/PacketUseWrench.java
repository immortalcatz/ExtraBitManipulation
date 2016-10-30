package com.phylogeny.extrabitmanipulation.packet;

import com.phylogeny.extrabitmanipulation.helper.ItemStackHelper;
import com.phylogeny.extrabitmanipulation.item.ItemBitWrench;

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

public class PacketUseWrench extends PacketBlockInteraction implements IMessage
{
	private boolean bitRequirement;
	
	public PacketUseWrench() {}
	
	public PacketUseWrench(BlockPos pos, EnumFacing side, boolean bitRequirement)
	{
		super(pos, side, new Vec3(0, 0, 0));
		this.bitRequirement = bitRequirement;
	}
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		buffer.writeBoolean(bitRequirement);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		bitRequirement = buffer.readBoolean();
	}
	
	public static class Handler implements IMessageHandler<PacketUseWrench, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUseWrench message, final MessageContext ctx)
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					EntityPlayer player = ctx.getServerHandler().playerEntity;
					ItemStack stack = player.getCurrentEquippedItem();
					if (ItemStackHelper.isBitWrenchStack(stack))
						((ItemBitWrench) stack.getItem()).useWrench(stack, player, player.worldObj,
								message.getPos(), message.getSide(), message.bitRequirement);
				}
			});
			return null;
		}
		
	}
	
}
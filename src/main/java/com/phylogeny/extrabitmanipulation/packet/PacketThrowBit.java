package com.phylogeny.extrabitmanipulation.packet;

import com.phylogeny.extrabitmanipulation.entity.EntityBit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketThrowBit implements IMessage
{
	
	public PacketThrowBit() {}
	
	@Override
	public void toBytes(ByteBuf buffer) {}
	
	@Override
	public void fromBytes(ByteBuf buffer) {}
	
	public static class Handler implements IMessageHandler<PacketThrowBit, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketThrowBit message, final MessageContext ctx)
		{
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.world;
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					EntityPlayer player = ctx.getServerHandler().playerEntity;
					ItemStack stack = player.getHeldItemMainhand();
					EntityBit entityBit = new EntityBit(player.world, player, stack);
					entityBit.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
					player.world.spawnEntity(entityBit);
					player.world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ,
							SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (player.world.rand.nextFloat() * 0.4F + 0.8F));
					if (!player.capabilities.isCreativeMode)
					{
						stack.shrink(1);
						player.setHeldItem(EnumHand.MAIN_HAND, stack);
					}
				}
			});
			return null;
		}
		
	}
	
}
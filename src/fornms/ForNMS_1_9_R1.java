package fornms;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class ForNMS_1_9_R1 implements ForNMSImpl
{
	public final void sendTitle(Player player , String text , int inTick , int midTick , int outTick)
	{
		PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
		playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TIMES , null , inTick , midTick, outTick));
		playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE , ChatSerializer.a("{\"text\": \"" + text + "\"}")));
	}
	
	public final void sendSubtitle(Player player , String text , int inTick , int midTick , int outTick)
	{
		PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
		playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TIMES , null , inTick , midTick, outTick));
		playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE , ChatSerializer.a("{\"text\": \"" + text + "\"}")));
	}
	
	public final void sendActionbar(Player player , String text)
	{
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{\"text\": \"" + text + "\"}"), (byte) 2));
	}
}
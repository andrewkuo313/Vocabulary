package nmsobject;

import org.bukkit.entity.Player;

public interface INMSObject 
{
	public void sendTitle(Player player, String text , int inTick , int midTick , int outTick);
	public void sendSubtitle(Player player , String text , int inTick , int midTick , int outTick);
	public void sendActionbar(Player player , String text);
}

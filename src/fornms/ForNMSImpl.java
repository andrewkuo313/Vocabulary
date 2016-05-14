package fornms;

import org.bukkit.entity.Player;

public interface ForNMSImpl 
{
	public void sendTitle(Player player, String text , int inTick , int midTick , int outTick);
	public void sendSubtitle(Player player , String text , int inTick , int midTick , int outTick);
	public void sendActionbar(Player player , String text);
}

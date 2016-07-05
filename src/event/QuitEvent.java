package event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import com.gmail.andrewchouhs.Main;

public class QuitEvent implements Listener
{
	private Main main;
	
	public QuitEvent(Main main)
	{
		this.main = main;
	}
	
	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent e)
	{
		main.storage.playing.remove(e.getPlayer());
	}
}

package event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.gmail.andrewchouhs.Conv;
import com.gmail.andrewchouhs.Main;
import com.gmail.andrewchouhs.PlayerInfo;
import com.gmail.andrewchouhs.Storage;

public class ChatEvent implements Listener
{
	private Main main;
	
	public ChatEvent(Main main)
	{
		this.main = main;
	}

	@EventHandler
	private final void onChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();
		Storage storage = main.storage;
		Conv conv = main.conv;
		if(storage.playing.containsKey(player))
		{
			e.setCancelled(storage.isCancelledChat());
			PlayerInfo playerInfo = storage.playing.get(player);
			if(!(playerInfo.locked))
			{
				//no
				conv.printDBWord("SELECT * FROM " + playerInfo.getTable() + " WHERE UID = " + playerInfo.uid, playerInfo, e.getMessage());
				playerInfo.locked = true;
				Bukkit.getScheduler().runTaskLater(main, new Runnable()
				{
					@Override
					public void run()
					{
						if(storage.playing.get(player) == playerInfo)
						{
							playerInfo.locked = false;
							conv.generateWord(playerInfo);
						}
					}
				}
				, storage.getDelayTime());
			}	
		}
	}
}

package com.gmail.andrewchouhs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener
{
	@EventHandler
	private final void onChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();
		if(Storage.isPlaying(player))
		{
			e.setCancelled(Storage.isCancelledChat());
			PlayerInfo playerInfo = Storage.getPlayerInfo(player);
			if(!(playerInfo.isLocked()))
			{
				Conv.printDBWord("SELECT * FROM " + playerInfo.getTable() + " WHERE UID = " + playerInfo.getUID(), playerInfo, e.getMessage());
				playerInfo.setLocked(true);
				Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable()
				{
					@Override
					public void run()
					{
						if(Storage.getPlayerInfo(player) == playerInfo)
						{
							playerInfo.setLocked(false);
							Conv.generateWord(playerInfo);
						}
					}
				}
				, Storage.getDelayTime());
			}	
		}
	}
}

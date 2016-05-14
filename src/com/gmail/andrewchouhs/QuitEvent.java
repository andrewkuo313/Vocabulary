package com.gmail.andrewchouhs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener
{
	@EventHandler
	private final void onPlayerQuit(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		Storage.removePlaying(player);
		Conv.getTitleObject().sendTitle(player, "", 0, 0, 0);
		Conv.getTitleObject().sendSubtitle(player, "", 0, 0, 0);
	}
}

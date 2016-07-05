package com.gmail.andrewchouhs;

import org.bukkit.plugin.java.JavaPlugin;
import com.gmail.andrewchouhs.event.ChatEvent;
import com.gmail.andrewchouhs.event.Commands;
import com.gmail.andrewchouhs.event.QuitEvent;

public class Main extends JavaPlugin
{
	public final Storage storage = new Storage(this);
	public final Conv conv = new Conv(this);
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new ChatEvent(this) , this);
		getServer().getPluginManager().registerEvents(new QuitEvent(this), this);
		getCommand("voc").setExecutor(new Commands(this));
		getLogger().info(storage.fullName + " 開啟！");
	}
	
	public void onDisable()
	{
		getLogger().info(storage.fullName + " 關閉！");
	}
}

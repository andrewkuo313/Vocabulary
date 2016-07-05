package com.gmail.andrewchouhs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import event.ChatEvent;
import event.Commands;
import event.QuitEvent;

public class Main extends JavaPlugin
{
	public final Storage storage = new Storage(this);
	public final Conv conv = new Conv(this);
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new ChatEvent(this) , this);
		getServer().getPluginManager().registerEvents(new QuitEvent(this), this);
		getCommand("voc").setExecutor(new Commands(this));
		storage.loadAllConfig(Bukkit.getConsoleSender());
		getLogger().info(getFullName() + " 開啟！");
	}
	
	public void onDisable()
	{
		getLogger().info(getFullName() + " 關閉！");
	}

	public String getFullName()
	{
		return "Vocabulary v" + getDescription().getVersion();
	}
}

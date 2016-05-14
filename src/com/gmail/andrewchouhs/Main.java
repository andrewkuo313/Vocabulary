package com.gmail.andrewchouhs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fornms.ForNMSImpl;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin
{
	private static Main main;
	
	public static final Main getMain()
	{
		return main;
	}
	
	public final void onEnable()
	{
		main = this;
		getServer().getPluginManager().registerEvents(new ChatEvent() , this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getCommand("voc").setExecutor(new Commands());
		loadAllConfig();
		getLogger().info("Vocabulary v" + getDescription().getVersion() + " 開啟！");
	}
	
	public final void onDisable()
	{
		getLogger().info("Vocabulary v" + getDescription().getVersion() + " 關閉！");
	}
	
	private final void loadAllConfig()
	{
		YamlConfiguration vY = new YamlConfiguration();
		loadConfig("VocabularyConfig.yml" , vY);
		Storage.setCancelledChat(vY.getBoolean("Config.CancelledChat"));
		Storage.setConnInfo(vY.getString("Database.ConnectionInfo"));
		Storage.setUserName(vY.getString("Database.UserName"));
		Storage.setPassword(vY.getString("Database.Password"));
		Storage.setDelayTime(vY.getInt("Config.DelayTime"));
		Storage.setActionbarConfig(new TextConfig(
				vY.getBoolean("Config.Actionbar.Use") 
				, vY.getString("Config.Actionbar.TopicText") 
				, vY.getString("Config.Actionbar.CorrectText")
				, vY.getString("Config.Actionbar.IncorrectText")));
		Storage.setDialogConfig(new TextConfig(
				vY.getBoolean("Config.Dialog.Use") 
				, vY.getString("Config.Dialog.TopicText") 
				, vY.getString("Config.Dialog.CorrectText")
				, vY.getString("Config.Dialog.IncorrectText")));
		Storage.setTitleConfig(new TextConfig(
				vY.getBoolean("Config.Title.Use") 
				, vY.getString("Config.Title.TopicText") 
				, vY.getString("Config.Title.CorrectText")
				, vY.getString("Config.Title.IncorrectText")));
		Storage.setSubtitleConfig(new TextConfig(
				vY.getBoolean("Config.Subtitle.Use") 
				, vY.getString("Config.Subtitle.TopicText") 
				, vY.getString("Config.Subtitle.CorrectText")
				, vY.getString("Config.Subtitle.IncorrectText")));
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3].replace("v", "");
		try
		{
			if(Storage.getTitleConfig().isUsed() || Storage.getActionbarConfig().isUsed() || Storage.getSubtitleConfig().isUsed())
			{
				Conv.setTitleObject((ForNMSImpl)(Class.forName("fornms.ForNMS_" + version).newInstance()));
				Bukkit.getConsoleSender().sendMessage("§aVocabulary v" + getDescription().getVersion() + " : 此伺服器版本可使用Title與Actionbar功能！");
			}
			else
				Conv.setTitleObject(null);
		}
		catch(Exception e)
		{
			Conv.setTitleObject(null);
			Bukkit.getConsoleSender().sendMessage("§cVocabulary v" + getDescription().getVersion() + " : 此伺服器版本不可使用Title與Actionbar功能！");
		}
	}
	
	public final void loadAllConfig(CommandSender sender)
	{
		loadAllConfig();
		sender.sendMessage("§aVocabulary v" + getDescription().getVersion() + " 重讀成功！");
	}
	
	//close
	private final void loadConfig(String path , YamlConfiguration yc)
	{
		File file = new File(getDataFolder(), path);
        if (!(file.exists()))
        	saveResource(path, false);
        Reader reader = null;
        try 
        {
        	reader = new InputStreamReader(new FileInputStream(file) , "UTF-8");
        	try 
        	{
				yc.load(reader);
				yc.loadFromString(ChatColor.translateAlternateColorCodes('&', yc.saveToString()));
			} 
        	catch (InvalidConfigurationException e) 
        	{
        		e.printStackTrace();
			}
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
        try 
        {
			reader.close();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
	}
}

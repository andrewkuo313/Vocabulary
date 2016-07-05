package com.gmail.andrewchouhs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import com.gmail.andrewchouhs.nmsobject.INMSObject;
import net.md_5.bungee.api.ChatColor;

public class Storage 
{
	private String connInfo;
	private boolean cancelledChat;
	private String userName;
	private String password;
	private TextConfig titleConfig;
	private TextConfig subtitleConfig;
	private TextConfig actionbarConfig;
	private TextConfig dialogConfig;
	private int delayTime;
	public final HashMap<Player , PlayerInfo> playing = new HashMap<Player , PlayerInfo>();
	private Main main;
	public final INMSObject nmsObject;
	public final String fullName;
	
	public Storage(Main main)
	{
		this.main = main;
		
		fullName = "Vocabulary v" + main.getDescription().getVersion();

		loadAllConfig(Bukkit.getConsoleSender());
		
		INMSObject nmsObjectReference;
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3].replace("v", "");
		try
		{
			nmsObjectReference = (INMSObject)(Class.forName("com.gmail.andrewchouhs.nmsobject.NMSObjectImpl_" + version).newInstance());
			Bukkit.getConsoleSender().sendMessage("§a" + fullName + " : 此伺服器版本可使用Title與Actionbar功能！");
		}
		catch(Exception e)
		{
			nmsObjectReference = null;
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§c" + fullName + " : 此伺服器版本不可使用Title與Actionbar功能！");
		}
		nmsObject = nmsObjectReference;
	}
	
	public void loadAllConfig(CommandSender sender)
	{
		YamlConfiguration vY = new YamlConfiguration();
		loadConfig("VocabularyConfig.yml" , vY);
		cancelledChat = vY.getBoolean("Config.CancelledChat");
		connInfo = vY.getString("Database.ConnectionInfo");
		userName = vY.getString("Database.UserName");
		password = vY.getString("Database.Password");
		delayTime = vY.getInt("Config.DelayTime");
		actionbarConfig = textConfigFromYaml(vY , "ActionBar");
		dialogConfig = textConfigFromYaml(vY , "Dialog");
		titleConfig = textConfigFromYaml(vY , "Title");
		subtitleConfig = textConfigFromYaml(vY , "Subtitle");
		sender.sendMessage("§a" + fullName + " 讀取設定檔成功！");
	}

	private TextConfig textConfigFromYaml(YamlConfiguration yaml , String node)
	{
		return new TextConfig(
				yaml.getBoolean("Config."+ node +".Use") 
				, yaml.getString("Config."+ node +".TopicText") 
				, yaml.getString("Config."+ node +".CorrectText")
				, yaml.getString("Config."+ node +".IncorrectText"));
	}
	
	private void loadConfig(String path , YamlConfiguration yc)
	{
		File file = new File(main.getDataFolder(), path);
        if (!(file.exists()))
        	main.saveResource(path, false);
        try(InputStreamReader reader = new InputStreamReader(new FileInputStream(file) , "UTF-8"))
        {
			yc.load(reader);
			yc.loadFromString(ChatColor.translateAlternateColorCodes('&', yc.saveToString()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getConnInfo()
	{
		return connInfo;
	}
	
	public boolean isCancelledChat()
	{
		return cancelledChat;
	}

	public String getUserName()
	{
		return userName;
	}

	public String getPassword() 
	{
		return password;
	}

	public TextConfig getTitleConfig() 
	{
		return titleConfig;
	}

	public TextConfig getSubtitleConfig() 
	{
		return subtitleConfig;
	}

	public TextConfig getActionbarConfig() 
	{
		return actionbarConfig;
	}

	public TextConfig getDialogConfig() 
	{
		return dialogConfig;
	}

	public int getDelayTime() 
	{
		return delayTime;
	}
}

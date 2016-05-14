package com.gmail.andrewchouhs;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Storage 
{
	private static String connInfo;
	private static boolean cancelledChat;
	private static String userName;
	private static String password;
	private static TextConfig titleConfig;
	private static TextConfig subtitleConfig;
	private static TextConfig actionbarConfig;
	private static TextConfig dialogConfig;
	private static int delayTime;
	private static HashMap<Player , PlayerInfo> playing = new HashMap<Player , PlayerInfo>();
	
	private Storage()
	{
		
	}
	
	public static final void setCancelledChat(boolean b)
	{
		cancelledChat = b;
	}
	
	public static final void setConnInfo(String s)
	{
		connInfo = s;
	}
	
	public static final String getConnInfo()
	{
		return connInfo;
	}
	
	public static final boolean isCancelledChat()
	{
		return cancelledChat;
	}

	public static final String getUserName()
	{
		return userName;
	}

	public static final void setUserName(String s) 
	{
		userName = s;
	}

	public static final String getPassword() 
	{
		return password;
	}

	public static final void setPassword(String s) 
	{
		password = s;
	}

	public static final boolean isPlaying(Player player) 
	{
		return playing.containsKey(player);
	}

	public static final void addPlaying(Player player , PlayerInfo playerInfo)
	{
		playing.put(player , playerInfo);
	}
	
	public static final void removePlaying(Player player) 
	{
		playing.remove(player);
	}
	
	public static final PlayerInfo getPlayerInfo(Player player)
	{
		return playing.get(player);
	}

	public static final TextConfig getTitleConfig() 
	{
		return titleConfig;
	}

	public static final void setTitleConfig(TextConfig titleConfig) 
	{
		Storage.titleConfig = titleConfig;
	}

	public static final TextConfig getSubtitleConfig() 
	{
		return subtitleConfig;
	}

	public static final void setSubtitleConfig(TextConfig subtitleConfig) 
	{
		Storage.subtitleConfig = subtitleConfig;
	}

	public static final TextConfig getActionbarConfig() 
	{
		return actionbarConfig;
	}

	public static final void setActionbarConfig(TextConfig actionbarConfig) 
	{
		Storage.actionbarConfig = actionbarConfig;
	}

	public static final TextConfig getDialogConfig() 
	{
		return dialogConfig;
	}

	public static final void setDialogConfig(TextConfig dialogConfig)
{
		Storage.dialogConfig = dialogConfig;
	}

	public static int getDelayTime() 
	{
		return delayTime;
	}

	public static void setDelayTime(int delayTime) 
	{
		Storage.delayTime = delayTime;
	}
}

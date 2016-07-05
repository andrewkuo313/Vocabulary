package com.gmail.andrewchouhs;

import org.bukkit.entity.Player;

public class PlayerInfo
{
	public int smallRange;
	public int bigRange;
	private String table;
	public String uid;
	public Player player;
	public boolean locked;
	
	public String getTable() 
	{
		return table;
	}
	
	public void setTable(int i) 
	{
		this.table = "english" + i;
	}
}

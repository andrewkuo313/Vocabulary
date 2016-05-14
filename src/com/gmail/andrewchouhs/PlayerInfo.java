package com.gmail.andrewchouhs;

import org.bukkit.entity.Player;

public class PlayerInfo
{
	private int smallRange;
	private int bigRange;
	private String table;
	private String uid;
	private Player player;
	private boolean locked;
	
	public final int getSmallRange() 
	{
		return smallRange;
	}
	
	public final void setSmallRange(int smallRange)
	{
		this.smallRange = smallRange;
	}
	
	public final int getBigRange() 
	{
		return bigRange;
	}
	
	public final void setBigRange(int bigRange) 
	{
		this.bigRange = bigRange;
	}
	
	public final String getTable() 
	{
		return table;
	}
	
	public final void setTable(int i) 
	{
		this.table = "english" + i;
	}
	
	public final String getUID() 
	{
		return uid;
	}
	
	public final void setUID(String uid)
	{
		this.uid = uid;
	}
	
	public final Player getPlayer()
	{
		return player;
	}
	
	public final void setPlayer(Player player)
	{
		this.player = player;
	}

	public final boolean isLocked() 
	{
		return locked;
	}

	public final void setLocked(boolean locked) 
	{
		this.locked = locked;
	}
}

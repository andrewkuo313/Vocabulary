package com.gmail.andrewchouhs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fornms.ForNMSImpl;

public class Conv 
{
	private static ForNMSImpl titleObject;
	
	public static final void setTitleObject(ForNMSImpl titleImpl)
	{
		titleObject = titleImpl;
	}
	
	public static final ForNMSImpl getTitleObject()
	{
		return titleObject;
	}
	
	private static final void printMessage(Player player , AnswerStatus as , String englishWord , String chineseWord , String partOfSpeech , String alphabetNumber , String table)
	{
		String englishWordPara = englishWord;
		if(as == AnswerStatus.Generate)
			englishWordPara = englishWord.replaceAll("[a-zA-Z]", " _").replaceFirst(" ", "");
		//%level% %english% %chinese% %pos% %wordcount%
		if(titleObject != null)
		{
			if(Storage.getTitleConfig().isUsed())
				titleObject.sendTitle(player, Storage.getTitleConfig().getText(as).replace("%level%", table).replace("%english%", englishWordPara)
						.replace("%chinese%", chineseWord).replace("%pos%", partOfSpeech)
						.replace("%wordcount%", alphabetNumber)
						, 5, 400, 5);
			if(Storage.getSubtitleConfig().isUsed())
				titleObject.sendSubtitle(player, Storage.getSubtitleConfig().getText(as).replace("%level%", table).replace("%english%", englishWordPara)
						.replace("%chinese%", chineseWord).replace("%pos%", partOfSpeech)
						.replace("%wordcount%", alphabetNumber)
						, 5, 400, 5);
			if(Storage.getActionbarConfig().isUsed())
				titleObject.sendActionbar(player, Storage.getActionbarConfig().getText(as).replace("%level%", table).replace("%english%", englishWordPara)
						.replace("%chinese%", chineseWord).replace("%pos%", partOfSpeech)
						.replace("%wordcount%", alphabetNumber)
						);
		}
		if(Storage.getDialogConfig().isUsed())
			player.sendMessage(Storage.getDialogConfig().getText(as).replace("%level%", table).replace("%english%", englishWordPara)
						.replace("%chinese%", chineseWord).replace("%pos%", partOfSpeech)
						.replace("%wordcount%", alphabetNumber));
	}
	
	public static final void generateWord(PlayerInfo playerInfo)
	{
		int table = (int)(((playerInfo.getBigRange() - playerInfo.getSmallRange() + 1) * Math.random()) + playerInfo.getSmallRange());
		playerInfo.setTable(table);
		String query = "SELECT * FROM " + playerInfo.getTable() + " ORDER BY RAND() LIMIT 1";
		printDBWord(query , playerInfo , null);
	}
	
	public static final void printDBWord(String query , PlayerInfo playerInfo , String answer)
	{
		try(Connection conn = DriverManager.getConnection(Storage.getConnInfo() , Storage.getUserName() , Storage.getPassword());
				Statement statement = conn.createStatement();ResultSet rs = statement.executeQuery(query);)
		{
			rs.next();
			String uid = Integer.toString(rs.getInt("UID"));
			String englishWord = rs.getString("EnglishWord");
			String chineseWord = rs.getString("ChineseWord");
			String partOfSpeech = rs.getString("PartOfSpeech");
			String alphabetNumber = Integer.toString(rs.getInt("AlphabetNumber"));
			String table = playerInfo.getTable().replace("english", "");
			AnswerStatus as;
			if(answer == null)
			{
				playerInfo.setUID(uid);
				as = AnswerStatus.Generate;
			}
			else
			{
				if(answer.equals(rs.getString("EnglishWord")))
					as = AnswerStatus.Correct;
				else
					as = AnswerStatus.Incorrect;
 			}
			printMessage(playerInfo.getPlayer() , as, englishWord, chineseWord, partOfSpeech, alphabetNumber , table);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§c資料庫載入失敗！");
		}
	}
}

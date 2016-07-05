package com.gmail.andrewchouhs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import nmsobject.INMSObject;

public class Conv 
{
	private Main main;
	
	public Conv(Main main)
	{
		this.main = main;
	}
	
	private void printMessage(Player player , AnswerStatus as , String englishWord , String chineseWord , String partOfSpeech , String alphabetNumber , String table)
	{
		Storage storage = main.storage;
		INMSObject nmsObject = storage.nmsObject;
		String englishWordPara = englishWord;
		if(as == AnswerStatus.Generate)
			englishWordPara = englishWord.replaceAll("[a-zA-Z]", " _").replaceFirst(" ", "");
		//%level% %english% %chinese% %pos% %wordcount%
		if(nmsObject != null)
		{
			if(storage.getTitleConfig().isUsed())
				nmsObject.sendTitle(player, storage.getTitleConfig().getText(as).replace("%level%", table).replace("%english%", englishWordPara)
						.replace("%chinese%", chineseWord).replace("%pos%", partOfSpeech)
						.replace("%wordcount%", alphabetNumber)
						, 5, 400, 5);
			if(storage.getSubtitleConfig().isUsed())
				nmsObject.sendSubtitle(player, storage.getSubtitleConfig().getText(as).replace("%level%", table).replace("%english%", englishWordPara)
						.replace("%chinese%", chineseWord).replace("%pos%", partOfSpeech)
						.replace("%wordcount%", alphabetNumber)
						, 5, 400, 5);
			if(storage.getActionbarConfig().isUsed())
				nmsObject.sendActionbar(player, storage.getActionbarConfig().getText(as).replace("%level%", table).replace("%english%", englishWordPara)
						.replace("%chinese%", chineseWord).replace("%pos%", partOfSpeech)
						.replace("%wordcount%", alphabetNumber)
						);
		}
		if(storage.getDialogConfig().isUsed())
			player.sendMessage(storage.getDialogConfig().getText(as).replace("%level%", table).replace("%english%", englishWordPara)
						.replace("%chinese%", chineseWord).replace("%pos%", partOfSpeech)
						.replace("%wordcount%", alphabetNumber));
	}
	
	public void generateWord(PlayerInfo playerInfo)
	{
		int table = (int)(((playerInfo.bigRange - playerInfo.smallRange + 1) * Math.random()) + playerInfo.smallRange);
		playerInfo.setTable(table);
		String query = "SELECT * FROM " + playerInfo.getTable() + " ORDER BY RAND() LIMIT 1";
		printDBWord(query , playerInfo , null);
	}
	
	public void printDBWord(String query , PlayerInfo playerInfo , String answer)
	{
		Storage storage = main.storage;
		try(Connection conn = DriverManager.getConnection(storage.getConnInfo() , storage.getUserName() , storage.getPassword());
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
				playerInfo.uid = uid;
				as = AnswerStatus.Generate;
			}
			else
			{
				if(answer.equals(rs.getString("EnglishWord")))
					as = AnswerStatus.Correct;
				else
					as = AnswerStatus.Incorrect;
 			}
			printMessage(playerInfo.player , as, englishWord, chineseWord, partOfSpeech, alphabetNumber , table);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§c資料庫載入失敗！");
		}
	}
}

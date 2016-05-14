package com.gmail.andrewchouhs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor
{
	@Override
	public final boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(args.length >= 1)
		{
			switch(args[0])
			{
				case "r":
				case "reload":
					if(sender.hasPermission("vocabulary.reload"))
						Main.getMain().loadAllConfig(sender);
					else
						sender.sendMessage("§c您沒有權限！");
					return false;
				case "p":
				case "play":
					if(sender instanceof Player)
					{
						Player player = (Player) sender;
						if(player.hasPermission("vocabulary.play"))
						{
							if(Storage.isPlaying(player))
							{
								Storage.removePlaying(player);
								player.sendMessage("§6挑戰已關閉！");
								Conv.getTitleObject().sendTitle(player, "", 0, 0, 0);
								Conv.getTitleObject().sendSubtitle(player, "", 0, 0, 0);
								return false;
							}
							else
							{
								int smallRange = 1;
								int bigRange = 6;
								switch(args.length)
								{
									case 1:
										break;
									default:
									case 3:
										bigRange = Integer.valueOf(args[2]);
									case 2:
										smallRange = Integer.valueOf(args[1]);
										break;
								}
								if((smallRange > 6) ||(smallRange < 1) ||(bigRange > 6) ||(bigRange < 1))
								{
									sender.sendMessage("§cLevel只能是1到6的整數！");
									return false;
								}
								if(smallRange > bigRange)
								{
									sender.sendMessage("§c你的最小Level超過最大Level了！");
									return false;
								}
								else
								{
									PlayerInfo playerInfo = new PlayerInfo();
									playerInfo.setSmallRange(smallRange);
									playerInfo.setBigRange(bigRange);
									playerInfo.setPlayer(player);
									playerInfo.setLocked(false);
									Storage.addPlaying(player, playerInfo);
									Conv.generateWord(playerInfo);
									return false;
								}
							}
						}
						else
						{
							player.sendMessage("§c您沒有權限！");
							return false;
						}
					}
					else
					{
						sender.sendMessage("§c非玩家不能玩！");
						return false;
					}	
			}
		}
		sender.sendMessage("§e=-=-=-=-=-=- §d§lVocabulary §e-=-=-=-=-=-=\n"
				+ "§6/vo , /voc , /voca , /vocabulary 都是同個指令！\n"
				+ "§6/vo reload 可以重讀插件設定！\n"
				+ "§6/vo play <最小範圍> <最大範圍> 可以開始/結束遊玩！Level有1到6級可選擇！"
				);
		return false;
	}

}

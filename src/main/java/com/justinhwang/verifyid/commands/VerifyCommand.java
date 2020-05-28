package com.justinhwang.verifyid.commands;

import com.justinhwang.verifyid.VerifyID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class VerifyCommand implements CommandExecutor {
    VerifyID plugin;

    public VerifyCommand(VerifyID plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player && strings.length != 0) {
            Player p = (Player) commandSender;
            String id = strings[0];
            int num = -1;

            boolean isNumber = false;
            try {
                num = Integer.parseInt(id);
                if(num > 0) {
                    isNumber = true;
                }
            } catch(NumberFormatException e) {
                isNumber = false;
            }

            if(isNumber == true) {
                int posZero = num/1000; //always an int
                int posOne = num/100 - (10 * (num/1000));
                int posTwo = num/10 - (10 * (num/100));
                int posThree = num - (10 * (num/10));

                if(posZero == 5) {
                    int classOf = -1;
                    classOf = (posOne * 10 + posTwo) + 4;
                    if(classOf >= 12 && classOf <= 25 && posThree <= 3) {
                        commandSender.sendMessage(ChatColor.GREEN + "Verified! Class of 20" + classOf);
                        String prefix = "[20" + classOf + "]";

                        //((Player) commandSender).setDisplayName(ChatColor.RED + "[20" + classOf + "] " + ChatColor.WHITE + commandSender.getName());
                        //((Player) commandSender).setPlayerListName(ChatColor.RED + "[20" + classOf + "] " + ChatColor.WHITE + commandSender.getName());

                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String perm = "lp user " + commandSender.getName() + " parent add student";
                        String removePrefix = "lp user " + commandSender.getName() + " meta removePrefix 1";
                        String addPrefix = "lp user " + commandSender.getName() + " meta addprefix 1 \"&2" + prefix + "\"";
                        Bukkit.dispatchCommand(console, perm);
                        Bukkit.dispatchCommand(console, removePrefix);
                        Bukkit.dispatchCommand(console, addPrefix);

                        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                String playerPrefix = plugin.getChat().getPlayerPrefix(p.getPlayer()) + " ";
                                p.setDisplayName(playerPrefix + ChatColor.GREEN + p.getName());
                                p.setPlayerListName(ChatColor.DARK_GREEN + playerPrefix.substring(2) + ChatColor.GREEN + p.getName());
                            }
                        }, 1);

                    } else {
                        sendError(commandSender, false);
                    }
                } else {
                    sendError(commandSender, false);
                }
            } else {
                sendError(commandSender, true);
            }


        } else {
            sendError(commandSender, true);
        }

        return true;
    }

    private void sendError(CommandSender sender, boolean wrongUsage) {
        if(wrongUsage == true) {
            sender.sendMessage(ChatColor.RED + "Usage: /verify <first 4 digits of FUHSD student #>");
            sender.sendMessage(ChatColor.RED + "Ex: \"/verify 7392\"");
        } else {
            sender.sendMessage(ChatColor.RED + "Sorry, that ID is not recognized. If you think this is a mistake, email jhwang241@student.fuhsd.org");
        }
    }
}

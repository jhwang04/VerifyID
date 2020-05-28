package com.justinhwang.verifyid.events;

import com.justinhwang.verifyid.VerifyID;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class VerifyIDJoin implements Listener {
    private VerifyID plugin;

    public VerifyIDJoin(VerifyID plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String prefix = plugin.getChat().getPlayerPrefix(event.getPlayer()) + " ";
        event.getPlayer().setDisplayName(prefix + ChatColor.GREEN + event.getPlayer().getName());
        event.getPlayer().setPlayerListName(ChatColor.DARK_GREEN + prefix.substring(2) + ChatColor.GREEN + event.getPlayer().getName());
    }
}

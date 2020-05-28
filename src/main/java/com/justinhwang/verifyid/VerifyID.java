package com.justinhwang.verifyid;

import com.justinhwang.verifyid.commands.VerifyCommand;
import com.justinhwang.verifyid.events.VerifyIDJoin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VerifyID extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Chat chat = null;

    @Override
    public void onEnable() {
        getLogger().info("VerifyID has been enabled");

        getCommand("verify").setExecutor(new VerifyCommand(this));

        getServer().getPluginManager().registerEvents(new VerifyIDJoin(this), this);

        setupChat();
    }


    public static Chat getChat() {
        return chat;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
}
package com.justinhwang.verifyid;

import com.justinhwang.verifyid.commands.VerifyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class VerifyID extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("VerifyID has been enabled");

        getCommand("verify").setExecutor(new VerifyCommand(this));
    }
}

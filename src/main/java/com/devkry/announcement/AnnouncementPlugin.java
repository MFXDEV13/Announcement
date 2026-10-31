package com.devkry.announcement;

import com.devkry.announcement.command.AnnounceCommand;
import com.devkry.announcement.manager.AnnouncementManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnnouncementPlugin extends JavaPlugin {

    private AnnouncementManager announcementManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        startAnnouncementManager();
        registerAnnounceCommand();
    }

    @Override
    public void onDisable() {
        if (announcementManager == null) {
            return;
        }
        announcementManager.stop();
        announcementManager = null;
    }

    private void startAnnouncementManager() {
        announcementManager = new AnnouncementManager(this);
        announcementManager.start();
    }

    private void registerAnnounceCommand() {
        PluginCommand command = getCommand("announce");
        if (command == null) {
            getLogger().severe("Command 'announce' tidak terdaftar di plugin.yml.");
            return;
        }

        AnnounceCommand executor = new AnnounceCommand(announcementManager);
        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }
}

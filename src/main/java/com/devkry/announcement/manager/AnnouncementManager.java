package com.devkry.announcement.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public final class AnnouncementManager {

    private static final long TICKS_PER_SECOND = 20L;
    private static final int DEFAULT_INTERVAL_SECONDS = 300;
    private static final int MINIMUM_INTERVAL_SECONDS = 1;

    private final JavaPlugin plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final List<Component> messages = new ArrayList<>();

    private Component prefix = Component.empty();
    private int intervalSeconds;
    private BukkitTask broadcastTask;
    private int nextMessageIndex;

    public AnnouncementManager(JavaPlugin plugin) {
        this.plugin = plugin;
        readConfig();
    }

    private void readConfig() {
        FileConfiguration config = plugin.getConfig();

        prefix = miniMessage.deserialize(config.getString("prefix", ""));
        intervalSeconds = Math.max(
                MINIMUM_INTERVAL_SECONDS,
                config.getInt("interval-seconds", DEFAULT_INTERVAL_SECONDS)
        );

        messages.clear();
        messages.addAll(readMessages(config));
    }

    private List<Component> readMessages(FileConfiguration config) {
        List<Component> result = new ArrayList<>();

        for (String rawMessage : config.getStringList("messages")) {
            if (!rawMessage.isBlank()) {
                result.add(miniMessage.deserialize(rawMessage));
            }
        }

        return result;
    }

    public void start() {
        stop();

        if (messages.isEmpty()) {
            plugin.getLogger().warning("Daftar 'messages' kosong, broadcast otomatis dinonaktifkan.");
            return;
        }

        long intervalTicks = intervalSeconds * TICKS_PER_SECOND;
        broadcastTask = plugin.getServer().getScheduler()
                .runTaskTimer(plugin, this::broadcastNextMessage, intervalTicks, intervalTicks);
    }

    public void stop() {
        if (broadcastTask == null) {
            return;
        }

        broadcastTask.cancel();
        broadcastTask = null;
    }

    private void broadcastNextMessage() {
        if (messages.isEmpty()) {
            return;
        }

        broadcast(messages.get(nextMessageIndex));
        nextMessageIndex = (nextMessageIndex + 1) % messages.size();
    }

    public void broadcast(Component message) {
        plugin.getServer().broadcast(prefix.append(message));
    }
}

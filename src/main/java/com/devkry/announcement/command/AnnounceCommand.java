package com.devkry.announcement.command;

import com.devkry.announcement.manager.AnnouncementManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public final class AnnounceCommand implements CommandExecutor, TabCompleter {

    private static final String PERMISSION = "announcement.use";

    private final AnnouncementManager announcementManager;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public AnnounceCommand(AnnouncementManager announcementManager) {
        this.announcementManager = announcementManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(Component.text("Kamu tidak punya izin.", NamedTextColor.RED));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Component.text("Usage: /" + label + " <pesan>", NamedTextColor.RED));
            return true;
        }

        String rawMessage = String.join(" ", args);
        announcementManager.broadcast(miniMessage.deserialize(rawMessage));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }
}

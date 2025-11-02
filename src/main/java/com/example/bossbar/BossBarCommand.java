package com.example.bossbar;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class BossBarCommand implements BasicCommand {
    private final BossBarPlugin plugin;

    public BossBarCommand(BossBarPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();
        
        if (args.length == 0) {
            sender.sendMessage(Component.text("Usage: /mbossbar reload", NamedTextColor.RED));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("bossbar.admin")) {
                sender.sendMessage(Component.text("You don't have permission to use this command!", NamedTextColor.RED));
                return;
            }

            plugin.getBossBarManager().loadBossBars();
            sender.sendMessage(Component.text("BossBar configuration reloaded successfully!", NamedTextColor.GREEN));
            return;
        }

        sender.sendMessage(Component.text("Usage: /mbossbar reload", NamedTextColor.RED));
    }
}
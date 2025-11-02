package com.example.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;

public class BossBarManager {
    private final BossBarPlugin plugin;
    private final Map<String, BossBar> bossBars = new HashMap<>();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public BossBarManager(BossBarPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadBossBars() {
        removeAllBossBars();
        bossBars.clear();

        File configFile = new File(plugin.getDataFolder(), "settings.toml");
        if (!configFile.exists()) {
            plugin.saveResource("settings.toml", false);
        }

        try {
            String content = Files.readString(configFile.toPath());
            com.electronwill.nightconfig.core.Config tomlConfig = com.electronwill.nightconfig.toml.TomlFormat.instance().createParser().parse(content);

            if (tomlConfig.contains("bossbars")) {
                com.electronwill.nightconfig.core.Config bossBarsConfig = tomlConfig.get("bossbars");
                for (Map.Entry<String, Object> entry : bossBarsConfig.valueMap().entrySet()) {
                    String bossBarName = entry.getKey();
                    if (entry.getValue() instanceof com.electronwill.nightconfig.core.Config bossBarData) {
                        createBossBar(bossBarName, bossBarData);
                    }
                }
            }

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load boss bars from settings.toml", e);
        }
    }

    private void createBossBar(String name, com.electronwill.nightconfig.core.Config data) {
        try {
            String title = data.getOrElse("title", "Boss Bar");
            String colorStr = data.getOrElse("color", "RED");
            String overlayStr = data.getOrElse("overlay", "PROGRESS");
            double progress = data.getOrElse("progress", 1.0);
            boolean visible = data.getOrElse("visible", true);
            
            Component titleComponent = miniMessage.deserialize(title);
            
            BossBar.Color color = parseColor(colorStr);
            BossBar.Overlay overlay = BossBar.Overlay.valueOf(overlayStr);
            
            BossBar bossBar = BossBar.bossBar(titleComponent, (float) progress, color, overlay);
            bossBars.put(name, bossBar);
            
            if (visible) {
                showBossBarToPlayers(bossBar, data);
            }
            
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to create boss bar: " + name, e);
        }
    }

    private BossBar.Color parseColor(String colorStr) {
        // Handle standard Minecraft boss bar colors
        try {
            return BossBar.Color.valueOf(colorStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid color: " + colorStr + ", using default color RED");
            return BossBar.Color.RED;
        }
    }

    private void showBossBarToPlayers(BossBar bossBar, com.electronwill.nightconfig.core.Config data) {
        List<String> playerNames = data.getOrElse("players", new ArrayList<String>());
        
        if (playerNames.isEmpty()) {
            // Show to all players
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                player.showBossBar(bossBar);
            }
        } else {
            // Show to specific players
            for (String playerName : playerNames) {
                Player player = plugin.getServer().getPlayer(playerName);
                if (player != null && player.isOnline()) {
                    player.showBossBar(bossBar);
                }
            }
        }
    }

    public void removeAllBossBars() {
        for (Map.Entry<String, BossBar> entry : bossBars.entrySet()) {
            BossBar bossBar = entry.getValue();
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                player.hideBossBar(bossBar);
            }
        }
    }

    public Collection<BossBar> getBossBars() {
        return bossBars.values();
    }
}
package com.example.bossbar;

import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final BossBarManager bossBarManager;

    public PlayerListener(BossBarManager bossBarManager) {
        this.bossBarManager = bossBarManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Show all boss bars to the player when they join
        for (BossBar bossBar : bossBarManager.getBossBars()) {
            player.showBossBar(bossBar);
        }
    }
}
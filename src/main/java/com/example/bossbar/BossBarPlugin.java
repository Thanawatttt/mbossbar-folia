package com.example.bossbar;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.example.bossbar.PlayerListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class BossBarPlugin extends JavaPlugin {

    private static BossBarPlugin instance;
    private BossBarManager bossBarManager;
    
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        bossBarManager = new BossBarManager(this);
        bossBarManager.loadBossBars();
        
        // Register the player listener
        getServer().getPluginManager().registerEvents(new PlayerListener(bossBarManager), this);
        
        registerCommands();
        
        getLogger().info("BossBar plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        if (bossBarManager != null) {
            bossBarManager.removeAllBossBars();
        }
        getLogger().info("BossBar plugin has been disabled!");
    }
    
    public static BossBarPlugin getInstance() {
        return instance;
    }
    
    public BossBarManager getBossBarManager() {
        return bossBarManager;
    }
    
    private void registerCommands() {
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("mbossbar", "Manage boss bars", new BossBarCommand(this));
        });
    }
    
    // Override to handle TOML config
    @Override
    public void saveDefaultConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        
        File configFile = new File(getDataFolder(), "settings.toml");
        if (!configFile.exists()) {
            try (InputStream in = getResource("settings.toml");
                 OutputStream out = new FileOutputStream(configFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                getLogger().severe("Could not save settings.toml to " + configFile.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }
}
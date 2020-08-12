package fr.ferdinandklr.nullstorage;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    
    @Override
    public void onEnable() {
        System.out.println("the plugin is enabled !");
    }

    @Override
    public void onDisable() {
        System.out.println("the plugin is disabled !");
    }
    
}
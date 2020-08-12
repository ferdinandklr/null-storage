package fr.ferdinandklr.nullstorage;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    
    @Override
    public void onEnable() {

        // get the plugin manager
        PluginManager pm = getServer().getPluginManager();

        // register the block placing event listener
        pm.registerEvents(new NullStorageCrateManager(this), this);

        /* THE PLUGIN IS ENABLED */
        System.out.println("the plugin is enabled !");
    }

    @Override
    public void onDisable() {
        /* THE PLUGIN IS DISABLED */
        System.out.println("the plugin is disabled !");
    }
    
}
package fr.ferdinandklr.nullstorage;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The class of the plugin
 */
public class main extends JavaPlugin {
    
    /**
     * Run on plugin is loaded
     */
    @Override
    public void onEnable() {

        // get the plugin manager
        PluginManager pm = getServer().getPluginManager();

        // register the block placing event listener
        pm.registerEvents(new NullStorageCrateManager(this), this);

        // set the command executor
        this.getCommand("nullstorage").setExecutor(new GetNullStorageCrate(this));

        /* THE PLUGIN IS ENABLED */
        System.out.println("the plugin is enabled !");
    }

    /**
     * Run when plugin is unloaded
     */
    @Override
    public void onDisable() {
        /* THE PLUGIN IS DISABLED */
        System.out.println("the plugin is disabled !");
    }
    
}
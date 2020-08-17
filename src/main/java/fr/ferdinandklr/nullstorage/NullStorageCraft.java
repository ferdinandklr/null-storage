package fr.ferdinandklr.nullstorage;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class NullStorageCraft implements Listener {

    ItemStack default_null_crate;
    JavaPlugin plugin;

    /**
     * Register the null storage crate craft
     * 
     * @param plugin Ths current plugin instance
     */
    public NullStorageCraft(JavaPlugin plugin) {
        this.plugin = plugin;
        default_null_crate = NullStorageCreator.create(plugin);
        NamespacedKey key = new NamespacedKey(plugin, "null_crate");
        ShapedRecipe recipe = new ShapedRecipe(key, default_null_crate);
        recipe.shape("GGG", "GSG", "GGG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.SHULKER_BOX);
        Bukkit.addRecipe(recipe);
    }

    /**
     * Run whenever a null storage crate is being crafted
     */
    @EventHandler
    public void onNullStorageCrateCraft(CraftItemEvent e) {
        // if the item crafted is a null crate
        if (e.getRecipe().getResult().equals(default_null_crate)) {
            // change the item's id
            ItemMeta im = e.getCurrentItem().getItemMeta();
            im.getPersistentDataContainer().set(new NamespacedKey(plugin, "id"), PersistentDataType.STRING, UUID.randomUUID().toString());
            e.getCurrentItem().setItemMeta(im);
        }
    }
     
}
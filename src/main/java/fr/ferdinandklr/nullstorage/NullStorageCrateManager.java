package fr.ferdinandklr.nullstorage;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class NullStorageCrateManager implements Listener {

    private JavaPlugin plugin;
    NamespacedKey key;

    /**
     * Initialise the container
     * @param plugin The current plugin
     */
    public NullStorageCrateManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "nullstorage");
    }

    /**
     * This function determines if a particular block is a null storage crate
     * 
     * @param itemstack The item stack to be determined
     */
    public boolean isNullStorage(ItemStack itemstack) {
        return itemstack.getType() == Material.STONE && itemstack.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER);
    }

    /**
     * This function detects whenever a player tries to place a null block down
     */
    @EventHandler
    public void onStorageBlockPlace(BlockPlaceEvent e) {

        // check if the item in the hand of the player is a null box
        ItemStack is;
        boolean is_in_main_hand = true;
        if (isNullStorage(e.getPlayer().getInventory().getItemInMainHand())) {
            is = e.getPlayer().getInventory().getItemInMainHand();
        } else if (isNullStorage(e.getPlayer().getInventory().getItemInOffHand())) {
            is = e.getPlayer().getInventory().getItemInOffHand();
            is_in_main_hand = false;
        } else {
            return;
        }

        // if the item is a null box
        e.getBlock().setType(Material.OBSIDIAN);
        // ItemMeta im = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
        // im.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 987654321);
        // e.getPlayer().getInventory().getItemInMainHand().setItemMeta(im);


        // make sure the item in hand isn't edited
        if (is_in_main_hand) {
            e.getPlayer().getInventory().setItemInMainHand(is);
        } else {
            e.getPlayer().getInventory().setItemInOffHand(is);
        }

    }

    /**
     *  This function is called when a player tries to open his null storage inventory
     */
    @EventHandler
    public void onNullStorageCrateRightClick(PlayerInteractEvent e) {
        // ItemStack im;
        if (isNullStorage(e.getPlayer().getInventory().getItemInMainHand())) {

        } else if (isNullStorage(e.getPlayer().getInventory().getItemInOffHand())) {

        } else {
            return;
        }
        e.setCancelled(true);
        Inventory i = Bukkit.createInventory(null, 9);
        ItemStack diamonds = new ItemStack(Material.DIAMOND);
        diamonds.setAmount(2);
        i.addItem(diamonds);
        e.getPlayer().openInventory(i);
    }

}
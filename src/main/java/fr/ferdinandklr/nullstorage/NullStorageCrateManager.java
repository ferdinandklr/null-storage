package fr.ferdinandklr.nullstorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class NullStorageCrateManager implements Listener {

    JavaPlugin plugin;
    HashMap<UUID, Inventory> opened_null_crates = new HashMap<UUID, Inventory>();

    /**
     * Initialise the container
     * @param plugin The current plugin
     */
    public NullStorageCrateManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * This function checks whether a particular block is a null storage crate or not
     * 
     * @param itemstack The item stack to be determined
     */
    public boolean isNullStorage(ItemStack itemstack) {
        return itemstack.getType() == Material.STONE && itemstack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "id"), PersistentDataType.STRING);
    }

    /**
     * This function is called whenever a player tries to place a null storage crate
     */
    @EventHandler
    public void onStorageBlockPlace(BlockPlaceEvent e) {

        // check if the item in the hand of the player is a null crate
        ItemStack null_crate = e.getItemInHand();
        EquipmentSlot null_crate_hand = e.getHand();
        if (!isNullStorage(null_crate)) { return; }

        // deserialize the inventory
        String serialized_inventory = null_crate.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "content"), PersistentDataType.STRING);
        Inventory inventory;
        try {
            inventory = InventorySerializer.deserialize(serialized_inventory);
        } catch (Exception err) {
            inventory = Bukkit.createInventory(null, 18);
        }

        // check that the player has enough items in his inventory
        int first_not_empty_slot = -1;
        for (int index = 0; index < inventory.getSize(); index++) {
            if (inventory.getContents()[index] != null) {
                first_not_empty_slot = index;
                break;
            }
        }
        if (first_not_empty_slot == -1) { e.setCancelled(true); return; }

        // decrement the items in the inventory
        inventory.getItem(first_not_empty_slot).setAmount(inventory.getItem(first_not_empty_slot).getAmount() - 1);

        // serialize and save the inventory back
        try {
            serialized_inventory = InventorySerializer.serialize(inventory);
        } catch (IOException err) {
            serialized_inventory = "error";
        }
        ItemMeta im = null_crate.getItemMeta();
        im.getPersistentDataContainer().set(new NamespacedKey(plugin, "content"), PersistentDataType.STRING, serialized_inventory);
        null_crate.setItemMeta(im);

        // make sure the item in hand isn't edited
        e.getPlayer().getInventory().setItem(null_crate_hand, null_crate);

    }

    /**
     *  This function is called when a player tries to open his null storage inventory
     */
    @EventHandler
    public void onNullStorageCrateOpen(PlayerInteractEvent e) {

        // if player didn't click in the air, quit
        if (e.getClickedBlock() != null) { return; }

        // if player is sneaking, quit
        if (e.getPlayer().isSneaking()) { return; }

        // check if player is holding a null storage crate
        EquipmentSlot hand_wich_clicked = e.getHand();
        if (!isNullStorage(e.getPlayer().getInventory().getItem(hand_wich_clicked))) { return; }

        // cancel the default event
        e.setCancelled(true);
        
        // deserialize the inventory
        String serialized_inventory = e.getItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "content"), PersistentDataType.STRING);
        Inventory inventory;
        try {
            inventory = InventorySerializer.deserialize(serialized_inventory);
        } catch (Exception err) {
            inventory = Bukkit.createInventory(null, 18);
        }

        // set the inventory as opened
        opened_null_crates.put(e.getPlayer().getUniqueId(), inventory);

        // open the inventory to the player
        e.getPlayer().openInventory(inventory);

    }

    /**
     *  This function is called when a player tries to close his null storage inventory
     *  We make sure here that the inventory is being save back into the item's nbts
     */
    @EventHandler
    public void onNullStorageInventoryClosed(InventoryCloseEvent e) {

        // check if the player is holding a null storage crate
        ItemStack null_crate = e.getPlayer().getInventory().getItemInMainHand();
        if (!isNullStorage(null_crate)) { return; }
        
        // check if the player has a null crate opened
        if (!opened_null_crates.containsKey(e.getPlayer().getUniqueId())) { return; }

        // get the inventory and check if it's the same as the opened one
        Inventory inventory = opened_null_crates.get(e.getPlayer().getUniqueId());
        if (!inventory.equals(e.getInventory())) { return; }
        opened_null_crates.remove(e.getPlayer().getUniqueId());
        
        // serialize the inventory
        String serialized_inventory;
        try {
            serialized_inventory = InventorySerializer.serialize(inventory);
        } catch (IOException err) {
            serialized_inventory = "error";
        }

        // save the inventory back
        ItemMeta im = null_crate.getItemMeta();
        im.getPersistentDataContainer().set(new NamespacedKey(plugin, "content"), PersistentDataType.STRING, serialized_inventory);
        null_crate.setItemMeta(im);

    }

    /*
     *  This event runs whenever a player tries to add items to is null storage crate
     */
    @EventHandler
    public void onNullStorageInventoryClick(InventoryClickEvent e) {

        // check if the player is holding a null storage crate
        ItemStack is = e.getWhoClicked().getInventory().getItemInMainHand();
        if (!isNullStorage(is)) { return; }

        // deserialize the inventory
        String serialized_inventory = is.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "content"), PersistentDataType.STRING);
        Inventory inventory;
        try {
            inventory = InventorySerializer.deserialize(serialized_inventory);
        } catch (Exception err) {
            inventory = Bukkit.createInventory(null, 18);
        }

        // check that the opened inventory is the one from the null crate
        if (e.getInventory().equals(inventory)) {
            if (e.getCurrentItem() != null && (e.getCurrentItem().getType() != Material.STONE || isNullStorage(e.getCurrentItem()))) {
                e.setCancelled(true);
            }
        }

    }
 
}
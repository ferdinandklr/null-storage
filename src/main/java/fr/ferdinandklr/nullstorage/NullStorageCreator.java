package fr.ferdinandklr.nullstorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class NullStorageCreator {

    /**
     * This function helps generating null storage crates
     * 
     * @param plugin The current plugin instance
     * @return The freshly created null crate
     */
    public static ItemStack create(Plugin plugin) {
        // create the item
        ItemStack paper = new ItemStack(Material.PAPER);
        paper.setAmount(1);
        ItemMeta im = paper.getItemMeta();
        im.getPersistentDataContainer().set(new NamespacedKey(plugin, "id"), PersistentDataType.STRING, UUID.randomUUID().toString());
        im.addEnchant(Enchantment.LURE, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.setDisplayName("null crate");
        List<String> lore = new ArrayList<String>();
        lore.add("right click in the air to see null crate's content");
        im.setLore(lore);

        // create inventory
        Inventory inventory = Bukkit.createInventory(null, 9);
        String serialized_inventory;
        try {
            serialized_inventory = InventorySerializer.serialize(inventory);
        } catch (IOException e) {
            serialized_inventory = "error during serialization";
        }
        im.getPersistentDataContainer().set(new NamespacedKey(plugin, "content"), PersistentDataType.STRING, serialized_inventory);
        paper.setItemMeta(im);

        // return the item
        return paper;
    }
    
}
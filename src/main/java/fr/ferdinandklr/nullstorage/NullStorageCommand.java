package fr.ferdinandklr.nullstorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class NullStorageCommand implements CommandExecutor {

    JavaPlugin plugin;

    public NullStorageCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // create the item
        ItemStack paper = new ItemStack(Material.PAPER);
        paper.setAmount(1);
        ItemMeta im = paper.getItemMeta();
        im.getPersistentDataContainer().set(new NamespacedKey(plugin, "id"), PersistentDataType.STRING, UUID.randomUUID().toString());
        im.addEnchant(Enchantment.LURE, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.setDisplayName("Null crate");
        List<String> lore = new ArrayList<String>();
        lore.add("right click in void to see content");
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

        // give item
        paper.setItemMeta(im);
        Player player = (Player) sender;
        player.getInventory().addItem(paper);

        return true;
	}
    
}
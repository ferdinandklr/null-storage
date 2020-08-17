package fr.ferdinandklr.nullstorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class InventorySerializer {

    /**
     * Serialize a chest inventory to a Base64 string
     * 
     * @param inventory The inventory to serialize
     * @return The serialized inventory as a string
     * @throws IOException
     */
    public static String serialize(Inventory inventory) throws IOException {
        String serialized_inventory;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BukkitObjectOutputStream bos = new BukkitObjectOutputStream(os);
            bos.writeInt(inventory.getSize());
            for (int k = 0; k < inventory.getSize(); k++) {
                bos.writeObject(inventory.getItem(k));
            }
            bos.close();
            serialized_inventory = Base64Coder.encodeLines(os.toByteArray());
            return serialized_inventory;
        } catch(IOException e) {
            throw e;
        }
    }

    /**
     * Deserialize a Base64 string to a chest inventory
     * 
     * @param serialized_inventory The inventory to deserialize
     * @return The deserialized inventory
     */
    public static Inventory deserialize(String serialized_inventory) throws IOException, ClassNotFoundException {
        try {
            Inventory inventory;
            ByteArrayInputStream ais = new ByteArrayInputStream(Base64Coder.decodeLines(serialized_inventory));
            BukkitObjectInputStream bis = new BukkitObjectInputStream(ais);
            inventory = Bukkit.createInventory(null, bis.readInt());
            for (int k = 0; k < inventory.getSize(); k++) {
                inventory.setItem(k, (ItemStack) bis.readObject());
            }
            return inventory;
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }
    
}
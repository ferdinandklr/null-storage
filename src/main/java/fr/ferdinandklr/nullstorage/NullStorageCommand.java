package fr.ferdinandklr.nullstorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class NullStorageCommand implements CommandExecutor {

    JavaPlugin plugin;

    public NullStorageCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // check the sender is a player
        if (!(sender instanceof Player)) {
            ExecutorLogger.err(sender, "you must be a player to execute this commmand");
            return true;
        }
        Player player = (Player) sender;

        // check the player has permission
        if (!player.hasPermission("nullstorage.command.nullstorage")) {
            ExecutorLogger.err(sender, "you don't have the permission to execute this command");
            return true;
        }

        // create the item
        ItemStack paper = NullStorageCreator.create(plugin);

        // give item to the player
        player.getInventory().addItem(paper);

        return true;
	}
    
}
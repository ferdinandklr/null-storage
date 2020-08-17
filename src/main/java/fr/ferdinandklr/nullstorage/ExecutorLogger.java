package fr.ferdinandklr.nullstorage;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class ExecutorLogger {

    /**
     *  Send a confirmation message to an executor
     *  @param executor The thing that executed something else
     *                  (might be a console, a player, a commandblock ...)
     *  @param msg The message you want to send to the executor
     */
    public static void ok(CommandSender executor, String msg) {
        executor.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + msg);
    }

    /**
     *  Send an info message to an executor
     *  @param executor The thing that executed something else
     *                  (might be a console, a player, a commandblock ...)
     *  @param msg The message you want to send to the executor
     */
    public static void info(CommandSender executor, String msg) {
        executor.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + msg);
    }

    /**
     *  Send an error message to an executor
     *  @param executor The thing that executed something else
     *                  (might be a console, a player, a commandblock ...)
     *  @param msg The message you want to send to the executor
     */
    public static void err(CommandSender executor, String msg) {
        executor.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "/!\\ " + msg);
    }
    
}
package com.yourname.smartphone.commands;

import com.yourname.smartphone.gui.HPMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.getInventory().contains(Material.COAL)) {
            p.sendMessage("§cKamu tidak memiliki HP! Beli di /tokohp");
            return true;
        }
        new HPMenu().open(p);
        return true;
    }
}

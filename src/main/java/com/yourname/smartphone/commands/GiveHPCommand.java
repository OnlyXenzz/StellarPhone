package com.yourname.smartphone.commands;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class GiveHPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        ItemStack hp = new ItemStack(Material.COAL);
        ItemMeta meta = hp.getItemMeta();
        meta.setDisplayName(TextUtils.format("&bSmartphone"));
        meta.setLore(Arrays.asList(TextUtils.format("&7Klik untuk menggunakan handphone")));
        meta.setCustomModelData(100021);
        hp.setItemMeta(meta);
        p.getInventory().addItem(hp);
        p.sendMessage(TextUtils.format("&aHP (Smartphone) diberikan!"));
        return true;
    }
}

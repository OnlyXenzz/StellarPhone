package com.yourname.smartphone.gui;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BagMenu {
    
    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, TextUtils.format("&bTas Penyimpanan"));
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(TextUtils.format("&cTutup"));
        close.setItemMeta(closeMeta);
        inv.setItem(53, close);
        p.openInventory(inv);
    }
}

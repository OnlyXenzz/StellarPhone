package com.yourname.smartphone.gui;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class GPSMenu {
    
    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, TextUtils.format("&bGPS Menu"));
        inv.setItem(0, createButton(Material.COMPASS, "&aSpawn", "&7Teleport ke spawn"));
        inv.setItem(1, createButton(Material.COMPASS, "&aHome", "&7Teleport ke home"));
        if (SmartphonePlugin.getInstance().getGpsManager().hasVillageLocation(p)) {
            inv.setItem(2, createButton(Material.COMPASS, "&aVillage", "&7Teleport ke village"));
        } else {
            inv.setItem(2, createButton(Material.BARRIER, "&7Village", "&cBelum diset! Use /setvillage"));
        }
        inv.setItem(8, createButton(Material.BARRIER, "&cTutup", ""));
        p.openInventory(inv);
    }
    
    private ItemStack createButton(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtils.format(name));
        if (!lore.isEmpty()) meta.setLore(Arrays.asList(TextUtils.format(lore)));
        item.setItemMeta(meta);
        return item;
    }
}

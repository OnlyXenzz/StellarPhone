package com.yourname.smartphone.gui;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class ShopMenu {
    
    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, TextUtils.format("&6Toko HP"));
        inv.setItem(3, createShopItem(Material.COAL, "&bSmartphone", "&7HP canggih dengan banyak fitur", 1000));
        inv.setItem(5, createShopItem(Material.GOLDEN_SWORD, "&6Charger HP", "&7Cas HP kamu", 500));
        inv.setItem(8, createShopItem(Material.BARRIER, "&cTutup", "", 0));
        p.openInventory(inv);
    }
    
    private ItemStack createShopItem(Material material, String name, String lore, int price) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtils.format(name));
        if (price > 0) {
            meta.setLore(Arrays.asList(TextUtils.format(lore), TextUtils.format("&fHarga: &a$" + price)));
        } else {
            meta.setLore(Arrays.asList(TextUtils.format(lore)));
        }
        item.setItemMeta(meta);
        return item;
    }
}

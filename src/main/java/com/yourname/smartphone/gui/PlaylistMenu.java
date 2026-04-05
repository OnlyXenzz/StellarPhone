package com.yourname.smartphone.gui;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class PlaylistMenu {
    
    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, TextUtils.format("&bPlaylist"));
        inv.setItem(0, createButton(Material.GREEN_WOOL, "&aPlay", "&7Mulai pemutaran"));
        inv.setItem(1, createButton(Material.RED_WOOL, "&cStop", "&7Hentikan pemutaran"));
        inv.setItem(2, createButton(Material.YELLOW_WOOL, "&6Skip", "&7Lompat ke lagu berikutnya"));
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

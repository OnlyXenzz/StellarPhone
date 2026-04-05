package com.yourname.smartphone.gui;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class HPMenu {
    
    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, TextUtils.format("&bHP Menu"));
        inv.setItem(0, createMenuItem(Material.PAPER, "&aSMS", "&7Kirim pesan ke player lain"));
        inv.setItem(1, createMenuItem(Material.PAPER, "&aCall", "&7Hubungi player lain"));
        inv.setItem(2, createMenuItem(Material.PAPER, "&aGPS", "&7Teleport ke lokasi tersimpan"));
        inv.setItem(3, createMenuItem(Material.PAPER, "&bStats", "&7Lihat statistik skillmu"));
        inv.setItem(4, createMenuItem(Material.PAPER, "&aAlarm", "&7Aktifkan alarm"));
        inv.setItem(5, createMenuItem(Material.PAPER, "&aBag", "&7Buka tas penyimpanan"));
        inv.setItem(6, createMenuItem(Material.PAPER, "&aGadget", "&7Buka menu gadget"));
        inv.setItem(7, createMenuItem(Material.PAPER, "&eNotes", "&7Catatan pribadi"));
        inv.setItem(8, createMenuItem(Material.BARRIER, "&cTutup", ""));
        p.openInventory(inv);
    }
    
    private ItemStack createMenuItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtils.format(name));
        if (!lore.isEmpty()) meta.setLore(Arrays.asList(TextUtils.format(lore)));
        item.setItemMeta(meta);
        return item;
    }
}

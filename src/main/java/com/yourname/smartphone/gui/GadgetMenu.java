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

public class GadgetMenu {
    
    public void open(Player p) {
        var bm = SmartphonePlugin.getInstance().getBatteryManager();
        Inventory inv = Bukkit.createInventory(null, 9, TextUtils.format("&bGadget Menu"));
        boolean senter = bm.isSenter(p);
        boolean kompas = bm.isKompas(p);
        boolean musik = bm.isMusik(p);
        boolean batterySaver = bm.isBatterySaver(p);
        inv.setItem(0, createGadgetItem(Material.TORCH, "&aSenter", senter ? "&7[&aON&7]" : "&7[&cOFF&7]", senter));
        inv.setItem(1, createGadgetItem(Material.COMPASS, "&aKompas", kompas ? "&7[&aON&7]" : "&7[&cOFF&7]", kompas));
        inv.setItem(2, createGadgetItem(Material.CLOCK, "&aKamera", "&7Simpan foto lokasi saat ini", false));
        inv.setItem(3, createGadgetItem(Material.JUKEBOX, "&aMusik", musik ? "&7[&aON&7]" : "&7[&cOFF&7]", musik));
        inv.setItem(4, createGadgetItem(Material.REDSTONE_TORCH, "&aBattery Saver", batterySaver ? "&7[&aON&7]" : "&7[&cOFF&7]", batterySaver));
        inv.setItem(5, createGadgetItem(Material.CLOCK, "&aTimer", "&7Set timer (ketik di chat)", false));
        inv.setItem(8, createGadgetItem(Material.BARRIER, "&cTutup", "", false));
        p.openInventory(inv);
    }
    
    private ItemStack createGadgetItem(Material material, String name, String status, boolean isOn) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtils.format(name + " " + status));
        meta.setLore(Arrays.asList(TextUtils.format("&7Klik untuk " + (isOn ? "mematikan" : "menyalakan"))));
        item.setItemMeta(meta);
        return item;
    }
}

package com.yourname.smartphone.gui;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.manager.NotesManager;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesMenu {
    
    public void open(Player p) {
        List<NotesManager.Note> notes = SmartphonePlugin.getInstance().getNotesManager().getNotes(p);
        int size = Math.min(54, Math.max(9, ((notes.size() + 8) / 9) * 9));
        Inventory inv = Bukkit.createInventory(null, size, TextUtils.format("&bCatatan"));
        for (int i = 0; i < notes.size() && i < 53; i++) {
            NotesManager.Note note = notes.get(i);
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(TextUtils.format("&f" + note.title));
            List<String> lore = new ArrayList<>();
            lore.add(TextUtils.format("&7" + new Date(note.timestamp)));
            lore.add(TextUtils.format("&7" + note.content.substring(0, Math.min(50, note.content.length())) + "..."));
            lore.add("");
            lore.add(TextUtils.format("&cKlik untuk menghapus"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(i, item);
        }
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(TextUtils.format("&cTutup"));
        close.setItemMeta(closeMeta);
        inv.setItem(size - 1, close);
        p.openInventory(inv);
    }
              }

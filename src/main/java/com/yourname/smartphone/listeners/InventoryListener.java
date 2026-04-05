package com.yourname.smartphone.listeners;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.gui.*;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();

        if (title.equals(TextUtils.format("&bHP Menu"))) {
            e.setCancelled(true);
            handleHPMenuClick(p, e.getRawSlot());
        } else if (title.equals(TextUtils.format("&bGadget Menu"))) {
            e.setCancelled(true);
            handleGadgetMenuClick(p, e.getRawSlot());
        } else if (title.equals(TextUtils.format("&bGPS Menu"))) {
            e.setCancelled(true);
            handleGPSMenuClick(p, e.getRawSlot());
        } else if (title.equals(TextUtils.format("&6Toko HP"))) {
            e.setCancelled(true);
            handleShopMenuClick(p, e.getRawSlot());
        } else if (title.equals(TextUtils.format("&bTas Penyimpanan"))) {
            e.setCancelled(true);
            if (e.getRawSlot() == 53) p.closeInventory();
        } else if (title.equals(TextUtils.format("&bCatatan"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
                SmartphonePlugin.getInstance().getNotesManager().deleteNote(p, e.getRawSlot());
                new NotesMenu().open(p);
            }
        } else if (title.equals(TextUtils.format("&bPlaylist"))) {
            e.setCancelled(true);
            if (e.getRawSlot() == 0) { SmartphonePlugin.getInstance().getPlaylistManager().playPlaylist(p); p.closeInventory(); }
            else if (e.getRawSlot() == 1) { SmartphonePlugin.getInstance().getPlaylistManager().stopPlaylist(p); p.closeInventory(); }
            else if (e.getRawSlot() == 2) { SmartphonePlugin.getInstance().getPlaylistManager().skipTrack(p); p.closeInventory(); }
            else if (e.getRawSlot() == 8) p.closeInventory();
        }
    }

    private void handleHPMenuClick(Player p, int slot) {
        switch (slot) {
            case 0: p.closeInventory(); SmartphonePlugin.getInstance().getSmsManager().startSMSMode(p); break;
            case 1: p.closeInventory(); SmartphonePlugin.getInstance().getSmsManager().startCallMode(p); break;
            case 2: p.closeInventory(); new GPSMenu().open(p); break;
            case 3: p.closeInventory(); p.performCommand("stats"); break;
            case 4: p.closeInventory(); p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_CAT_HISS, 1.0f, 1.0f); p.sendMessage(TextUtils.format("&c⚠ Alarm aktif!")); break;
            case 5: p.closeInventory(); new BagMenu().open(p); break;
            case 6: p.closeInventory(); new GadgetMenu().open(p); break;
            case 7: p.closeInventory(); new NotesMenu().open(p); break;
            case 8: p.closeInventory(); break;
        }
    }

    private void handleGadgetMenuClick(Player p, int slot) {
        var bm = SmartphonePlugin.getInstance().getBatteryManager();
        switch (slot) {
            case 0:
                if (bm.isSenter(p)) { bm.setSenter(p, false); p.sendMessage(TextUtils.format("&cSenter dimatikan!")); }
                else if (bm.getBattery(p) > 0) { bm.setSenter(p, true); p.sendMessage(TextUtils.format("&aSenter dinyalakan!")); }
                else p.sendMessage(TextUtils.format("&cBaterai habis!"));
                p.closeInventory(); new GadgetMenu().open(p); break;
            case 1:
                if (bm.isKompas(p)) { bm.setKompas(p, false); p.sendMessage(TextUtils.format("&cKompas dimatikan!")); }
                else if (bm.getBattery(p) > 0) { bm.setKompas(p, true); p.sendMessage(TextUtils.format("&aKompas dinyalakan!")); }
                else p.sendMessage(TextUtils.format("&cBaterai habis!"));
                p.closeInventory(); new GadgetMenu().open(p); break;
            case 2:
                p.closeInventory();
                if (bm.getBattery(p) >= 5) {
                    p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
                    int x = p.getLocation().getBlockX(); int y = p.getLocation().getBlockY(); int z = p.getLocation().getBlockZ();
                    p.sendMessage(TextUtils.format("&aFoto disimpan! &7Lokasi: " + p.getWorld().getName() + " x:" + x + " y:" + y + " z:" + z));
                    bm.addBattery(p, -5);
                } else p.sendMessage(TextUtils.format("&cBaterai tidak cukup! (butuh 5)"));
                break;
            case 3:
                if (bm.isMusik(p)) { bm.setMusik(p, false); p.stopSound(org.bukkit.Sound.MUSIC_DISC_FAR); p.sendMessage(TextUtils.format("&cMusik dimatikan!")); }
                else if (bm.getBattery(p) > 0) { bm.setMusik(p, true); p.playSound(p.getLocation(), org.bukkit.Sound.MUSIC_DISC_FAR, 1.0f, 1.0f); p.sendMessage(TextUtils.format("&aMusik dinyalakan!")); }
                else p.sendMessage(TextUtils.format("&cBaterai habis!"));
                p.closeInventory(); new GadgetMenu().open(p); break;
            case 4:
                if (bm.isBatterySaver(p)) { bm.setBatterySaver(p, false); p.sendMessage(TextUtils.format("&cBattery saver dimatikan!")); }
                else { bm.setBatterySaver(p, true); p.sendMessage(TextUtils.format("&aBattery saver dinyalakan!")); }
                p.closeInventory(); new GadgetMenu().open(p); break;
            case 5: p.closeInventory(); SmartphonePlugin.getInstance().getSmsManager().startTimerMode(p); break;
            case 8: p.closeInventory(); break;
        }
    }

    private void handleGPSMenuClick(Player p, int slot) {
        var gps = SmartphonePlugin.getInstance().getGpsManager();
        switch (slot) {
            case 0: p.closeInventory(); p.performCommand("spawn"); break;
            case 1: p.closeInventory(); p.performCommand("home"); break;
            case 2: p.closeInventory(); if (gps.hasVillageLocation(p)) { gps.teleportToVillage(p); } else { p.sendMessage(TextUtils.format("&cLokasi village belum diset! Use /setvillage")); } break;
            case 8: p.closeInventory(); break;
        }
    }

    private void handleShopMenuClick(Player p, int slot) {
        var eco = SmartphonePlugin.getInstance().getEconomy();
        switch (slot) {
            case 3:
                if (eco != null && eco.has(p, 1000)) {
                    eco.withdrawPlayer(p, 1000);
                    ItemStack hp = new ItemStack(org.bukkit.Material.COAL);
                    var meta = hp.getItemMeta();
                    meta.setDisplayName(TextUtils.format("&bSmartphone"));
                    meta.setLore(java.util.Arrays.asList(TextUtils.format("&7Klik untuk menggunakan handphone")));
                    meta.setCustomModelData(100021);
                    hp.setItemMeta(meta);
                    p.getInventory().addItem(hp);
                    p.sendMessage(TextUtils.format("&aKamu membeli &bSmartphone &asenilai &e$1000&a!"));
                    p.closeInventory();
                } else p.sendMessage(TextUtils.format("&cSaldo tidak cukup! Butuh &e$1000"));
                break;
            case 5:
                if (eco != null && eco.has(p, 500)) {
                    eco.withdrawPlayer(p, 500);
                    ItemStack charger = new ItemStack(org.bukkit.Material.GOLDEN_SWORD);
                    var meta = charger.getItemMeta();
                    meta.setDisplayName(TextUtils.format("&6Charger HP"));
                    meta.setLore(java.util.Arrays.asList(TextUtils.format("&7Klik kanan untuk mengecas HP")));
                    meta.setCustomModelData(100022);
                    charger.setItemMeta(meta);
                    p.getInventory().addItem(charger);
                    p.sendMessage(TextUtils.format("&aKamu membeli &6Charger HP &asenilai &e$500&a!"));
                    p.closeInventory();
                } else p.sendMessage(TextUtils.format("&cSaldo tidak cukup! Butuh &e$500"));
                break;
            case 8: p.closeInventory(); break;
        }
    }
}

package com.yourname.smartphone.manager;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupManager {
    
    private final File backupFolder;
    
    public BackupManager() {
        backupFolder = new File("plugins/SmartphonePlugin/backups");
        if (!backupFolder.exists()) backupFolder.mkdirs();
    }
    
    public void backupPlayerData(Player p) {
        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File backupFile = new File(backupFolder, p.getUniqueId().toString() + "_" + timestamp + ".yml");
            YamlConfiguration backup = new YamlConfiguration();
            backup.set("battery", SmartphonePlugin.getInstance().getBatteryManager().getBattery(p));
            if (SmartphonePlugin.getInstance().getGpsManager().hasVillageLocation(p)) {
                var loc = SmartphonePlugin.getInstance().getGpsManager().getVillageLocation(p);
                backup.set("village.world", loc.getWorld().getName());
                backup.set("village.x", loc.getX());
                backup.set("village.y", loc.getY());
                backup.set("village.z", loc.getZ());
            }
            var notes = SmartphonePlugin.getInstance().getNotesManager().getNotes(p);
            for (int i = 0; i < notes.size(); i++) {
                backup.set("notes." + i + ".title", notes.get(i).title);
                backup.set("notes." + i + ".content", notes.get(i).content);
                backup.set("notes." + i + ".timestamp", notes.get(i).timestamp);
            }
            var playlist = SmartphonePlugin.getInstance().getPlaylistManager().getPlaylist(p);
            for (int i = 0; i < playlist.size(); i++) {
                backup.set("playlist." + i, playlist.get(i));
            }
            backup.save(backupFile);
            p.sendMessage(TextUtils.format("&aData berhasil dibackup! &7(" + timestamp + ")"));
        } catch (Exception e) {
            e.printStackTrace();
            p.sendMessage(TextUtils.format("&cGagal melakukan backup!"));
        }
    }
    
    public void restoreBackup(Player p, String filename) {
        try {
            File backupFile = new File(backupFolder, filename);
            if (!backupFile.exists()) {
                p.sendMessage(TextUtils.format("&cFile '&b" + filename + "&c' tidak ditemukan!"));
                listBackups(p);
                return;
            }
            YamlConfiguration backup = YamlConfiguration.loadConfiguration(backupFile);
            if (backup.contains("battery")) {
                SmartphonePlugin.getInstance().getBatteryManager().setBattery(p, backup.getInt("battery"));
            }
            if (backup.contains("notes")) {
                var notesSection = backup.getConfigurationSection("notes");
                if (notesSection != null) {
                    for (String key : notesSection.getKeys(false)) {
                        String title = backup.getString("notes." + key + ".title");
                        String content = backup.getString("notes." + key + ".content");
                        long timestamp = backup.getLong("notes." + key + ".timestamp");
                        SmartphonePlugin.getInstance().getNotesManager().restoreNote(p, title, content, timestamp);
                    }
                }
            }
            p.sendMessage(TextUtils.format("&aData berhasil direstore dari &b" + filename));
        } catch (Exception e) {
            e.printStackTrace();
            p.sendMessage(TextUtils.format("&cGagal merestore backup!"));
        }
    }
    
    public void listBackups(Player p) {
        File[] files = backupFolder.listFiles((dir, name) -> name.endsWith(".yml") && name.startsWith(p.getUniqueId().toString()));
        if (files == null || files.length == 0) { p.sendMessage(TextUtils.format("&7Tidak ada backup untukmu")); return; }
        p.sendMessage(TextUtils.format("&bDaftar backupmu:"));
        for (File file : files) {
            p.sendMessage(TextUtils.format("&7- &f" + file.getName().replace(p.getUniqueId().toString() + "_", "")));
        }
    }
    
    public void saveAllData() {
        for (Player p : Bukkit.getOnlinePlayers()) backupPlayerData(p);
        SmartphonePlugin.getInstance().getNotesManager().saveNotes();
    }
}

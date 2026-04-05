package com.yourname.smartphone.manager;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.*;

public class NotesManager {
    
    private final Map<UUID, List<Note>> notes = new HashMap<>();
    private final File notesFile;
    private YamlConfiguration config;
    
    public NotesManager() {
        notesFile = new File("plugins/SmartphonePlugin/notes.yml");
        if (!notesFile.exists()) {
            notesFile.getParentFile().mkdirs();
            try { notesFile.createNewFile(); } catch (Exception e) { e.printStackTrace(); }
        }
        loadNotes();
    }
    
    private void loadNotes() {
        config = YamlConfiguration.loadConfiguration(notesFile);
        for (String key : config.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                List<Note> playerNotes = new ArrayList<>();
                var section = config.getConfigurationSection(key);
                if (section != null) {
                    for (String noteKey : section.getKeys(false)) {
                        String title = config.getString(key + "." + noteKey + ".title");
                        String content = config.getString(key + "." + noteKey + ".content");
                        long timestamp = config.getLong(key + "." + noteKey + ".timestamp");
                        playerNotes.add(new Note(title, content, timestamp));
                    }
                }
                notes.put(uuid, playerNotes);
            } catch (Exception ignored) {}
        }
    }
    
    public void saveNotes() {
        for (Map.Entry<UUID, List<Note>> entry : notes.entrySet()) {
            String uuid = entry.getKey().toString();
            config.set(uuid, null);
            int i = 0;
            for (Note note : entry.getValue()) {
                config.set(uuid + "." + i + ".title", note.title);
                config.set(uuid + "." + i + ".content", note.content);
                config.set(uuid + "." + i + ".timestamp", note.timestamp);
                i++;
            }
        }
        try { config.save(notesFile); } catch (Exception e) { e.printStackTrace(); }
    }
    
    public void addNote(Player p, String title, String content) {
        UUID uuid = p.getUniqueId();
        notes.computeIfAbsent(uuid, k -> new ArrayList<>());
        notes.get(uuid).add(new Note(title, content, System.currentTimeMillis()));
        p.sendMessage(TextUtils.format("&aCatatan '&b" + title + "&a' ditambahkan!"));
        saveNotes();
    }
    
    public void restoreNote(Player p, String title, String content, long timestamp) {
        UUID uuid = p.getUniqueId();
        notes.computeIfAbsent(uuid, k -> new ArrayList<>());
        notes.get(uuid).add(new Note(title, content, timestamp));
        saveNotes();
    }
    
    public void deleteNote(Player p, int index) {
        UUID uuid = p.getUniqueId();
        List<Note> playerNotes = notes.get(uuid);
        if (playerNotes == null || index < 0 || index >= playerNotes.size()) {
            p.sendMessage(TextUtils.format("&cCatatan tidak ditemukan!"));
            return;
        }
        Note removed = playerNotes.remove(index);
        p.sendMessage(TextUtils.format("&aCatatan '&b" + removed.title + "&a' dihapus!"));
        saveNotes();
    }
    
    public List<Note> getNotes(Player p) { return notes.getOrDefault(p.getUniqueId(), new ArrayList<>()); }
    
    public String formatNoteList(Player p) {
        List<Note> playerNotes = getNotes(p);
        if (playerNotes.isEmpty()) return TextUtils.format("&7Tidak ada catatan.");
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.format("&bDaftar catatan:\n"));
        for (int i = 0; i < playerNotes.size(); i++) {
            Note note = playerNotes.get(i);
            sb.append(TextUtils.format("&7" + i + ". &f" + note.title + " &7(" + new Date(note.timestamp) + ")\n"));
        }
        return sb.toString();
    }
    
    public static class Note {
        public String title, content;
        public long timestamp;
        public Note(String title, String content, long timestamp) {
            this.title = title;
            this.content = content;
            this.timestamp = timestamp;
        }
    }
}

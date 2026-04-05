package com.yourname.smartphone.manager;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.*;

public class PlaylistManager {
    
    private final Map<UUID, List<String>> playlists = new HashMap<>();
    private final Map<UUID, Integer> currentTrack = new HashMap<>();
    private final Map<UUID, Boolean> isPlaying = new HashMap<>();
    private final Map<UUID, Integer> taskId = new HashMap<>();
    private final Map<String, Sound> availableSongs = new HashMap<>();
    
    public PlaylistManager() {
        availableSongs.put("electric", Sound.BLOCK_NOTE_BLOCK_PLING);
        availableSongs.put("beat", Sound.BLOCK_NOTE_BLOCK_BASS);
        availableSongs.put("piano", Sound.BLOCK_NOTE_BLOCK_HARP);
        availableSongs.put("drum", Sound.BLOCK_NOTE_BLOCK_SNARE);
        availableSongs.put("guitar", Sound.BLOCK_NOTE_BLOCK_GUITAR);
        availableSongs.put("end", Sound.MUSIC_DISC_11);
        availableSongs.put("chill", Sound.MUSIC_DISC_FAR);
        availableSongs.put("rock", Sound.MUSIC_DISC_CAT);
        availableSongs.put("melloh", Sound.MUSIC_DISC_MELLOHI);
        availableSongs.put("strad", Sound.MUSIC_DISC_STRAD);
    }
    
    public List<String> getPlaylist(Player p) {
        return playlists.getOrDefault(p.getUniqueId(), new ArrayList<>());
    }
    
    public void addToPlaylist(Player p, String songName) {
        UUID uuid = p.getUniqueId();
        playlists.computeIfAbsent(uuid, k -> new ArrayList<>());
        String key = songName.toLowerCase();
        if (!availableSongs.containsKey(key)) {
            p.sendMessage(TextUtils.format("&cLagu '&b" + songName + "&c' tidak ditemukan!"));
            p.sendMessage(TextUtils.format("&7Lagu tersedia: " + String.join(", ", availableSongs.keySet())));
            return;
        }
        playlists.get(uuid).add(key);
        p.sendMessage(TextUtils.format("&aLagu '&b" + songName + "&a' ditambahkan ke playlist!"));
    }
    
    public void removeFromPlaylist(Player p, int index) {
        UUID uuid = p.getUniqueId();
        List<String> list = playlists.get(uuid);
        if (list == null || index < 0 || index >= list.size()) {
            p.sendMessage(TextUtils.format("&cIndeks tidak valid!"));
            return;
        }
        String removed = list.remove(index);
        p.sendMessage(TextUtils.format("&aLagu '&b" + removed + "&a' dihapus dari playlist!"));
    }
    
    public void playPlaylist(Player p) {
        UUID uuid = p.getUniqueId();
        List<String> list = playlists.get(uuid);
        if (list == null || list.isEmpty()) { p.sendMessage(TextUtils.format("&cPlaylist kosong!")); return; }
        stopPlaylist(p);
        currentTrack.put(uuid, 0);
        isPlaying.put(uuid, true);
        playNextTrack(p);
    }
    
    private void playNextTrack(Player p) {
        UUID uuid = p.getUniqueId();
        if (!isPlaying.getOrDefault(uuid, false)) return;
        List<String> list = playlists.get(uuid);
        int track = currentTrack.getOrDefault(uuid, 0);
        if (track >= list.size()) {
            p.sendMessage(TextUtils.format("&aPlaylist selesai!"));
            isPlaying.put(uuid, false);
            return;
        }
        String songName = list.get(track);
        Sound sound = availableSongs.get(songName);
        if (sound != null) {
            p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
            p.sendMessage(TextUtils.format("&aMemutar '&b" + songName + "&a' (" + (track + 1) + "/" + list.size() + ")"));
            int task = Bukkit.getScheduler().runTaskLater(SmartphonePlugin.getInstance(), () -> {
                currentTrack.put(uuid, track + 1);
                playNextTrack(p);
            }, 80L).getTaskId();
            taskId.put(uuid, task);
        }
    }
    
    public void stopPlaylist(Player p) {
        UUID uuid = p.getUniqueId();
        isPlaying.put(uuid, false);
        if (taskId.containsKey(uuid)) { Bukkit.getScheduler().cancelTask(taskId.get(uuid)); taskId.remove(uuid); }
        p.sendMessage(TextUtils.format("&cPlaylist dihentikan!"));
    }
    
    public void skipTrack(Player p) {
        UUID uuid = p.getUniqueId();
        if (!isPlaying.getOrDefault(uuid, false)) { p.sendMessage(TextUtils.format("&cTidak ada playlist yang sedang diputar!")); return; }
        if (taskId.containsKey(uuid)) Bukkit.getScheduler().cancelTask(taskId.get(uuid));
        currentTrack.put(uuid, currentTrack.getOrDefault(uuid, 0) + 1);
        playNextTrack(p);
        p.sendMessage(TextUtils.format("&aSkip ke lagu berikutnya!"));
    }
    
    public String getPlaylistInfo(Player p) {
        UUID uuid = p.getUniqueId();
        List<String> list = playlists.get(uuid);
        if (list == null || list.isEmpty()) return TextUtils.format("&7Playlist kosong");
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.format("&bPlaylist musik:\n"));
        for (int i = 0; i < list.size(); i++) {
            String marker = (isPlaying.getOrDefault(uuid, false) && currentTrack.getOrDefault(uuid, 0) == i) ? "▶ " : "  ";
            sb.append(TextUtils.format("&7" + marker + i + ". &f" + list.get(i) + "\n"));
        }
        return sb.toString();
    }
    
    public List<String> getAvailableSongs() { return new ArrayList<>(availableSongs.keySet()); }
}

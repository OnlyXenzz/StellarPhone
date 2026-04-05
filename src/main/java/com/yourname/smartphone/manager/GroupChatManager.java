package com.yourname.smartphone.manager;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.*;

public class GroupChatManager {
    
    private final Map<String, Set<UUID>> groups = new HashMap<>();
    private final Map<UUID, String> activeGroup = new HashMap<>();
    
    public void createGroup(String groupName, Player creator) {
        if (groups.containsKey(groupName.toLowerCase())) {
            creator.sendMessage(TextUtils.format("&cGrup '&b" + groupName + "&c' sudah ada!"));
            return;
        }
        groups.put(groupName.toLowerCase(), new HashSet<>());
        joinGroup(groupName, creator);
        creator.sendMessage(TextUtils.format("&aGrup '&b" + groupName + "&a' berhasil dibuat!"));
    }
    
    public void joinGroup(String groupName, Player player) {
        String key = groupName.toLowerCase();
        if (!groups.containsKey(key)) { player.sendMessage(TextUtils.format("&cGrup '&b" + groupName + "&c' tidak ditemukan!")); return; }
        groups.get(key).add(player.getUniqueId());
        activeGroup.put(player.getUniqueId(), key);
        player.sendMessage(TextUtils.format("&aBergabung ke grup '&b" + groupName + "&a'"));
    }
    
    public void leaveGroup(Player player) {
        String group = activeGroup.remove(player.getUniqueId());
        if (group == null) { player.sendMessage(TextUtils.format("&cKamu tidak sedang dalam grup apapun!")); return; }
        Set<UUID> members = groups.get(group);
        if (members != null) {
            members.remove(player.getUniqueId());
            if (members.isEmpty()) groups.remove(group);
        }
        player.sendMessage(TextUtils.format("&cKeluar dari grup '&b" + group + "&c'"));
    }
    
    public void sendGroupMessage(Player player, String message) {
        String group = activeGroup.get(player.getUniqueId());
        if (group == null) { player.sendMessage(TextUtils.format("&cKamu tidak sedang dalam grup chat!")); return; }
        Set<UUID> members = groups.get(group);
        if (members == null) { activeGroup.remove(player.getUniqueId()); return; }
        for (UUID memberId : members) {
            Player member = Bukkit.getPlayer(memberId);
            if (member != null && member.isOnline()) {
                member.sendMessage(TextUtils.format("&3[Grup &b" + group + "&3] &7" + player.getName() + "&7: &f" + message));
            }
        }
    }
    
    public void listGroups(Player player) {
        if (groups.isEmpty()) { player.sendMessage(TextUtils.format("&7Tidak ada grup yang tersedia")); return; }
        player.sendMessage(TextUtils.format("&bDaftar grup:"));
        for (String group : groups.keySet()) {
            player.sendMessage(TextUtils.format("&7- &f" + group + " &7(" + groups.get(group).size() + " member)"));
        }
    }
    
    public void listMembers(Player player, String groupName) {
        Set<UUID> members = groups.get(groupName.toLowerCase());
        if (members == null) { player.sendMessage(TextUtils.format("&cGrup tidak ditemukan!")); return; }
        player.sendMessage(TextUtils.format("&bMember grup '" + groupName + "':"));
        for (UUID memberId : members) {
            Player member = Bukkit.getPlayer(memberId);
            if (member != null) player.sendMessage(TextUtils.format("&7- &f" + member.getName()));
        }
    }
    
    public String getActiveGroup(Player player) {
        String group = activeGroup.get(player.getUniqueId());
        return group != null ? group : "tidak ada";
    }
}

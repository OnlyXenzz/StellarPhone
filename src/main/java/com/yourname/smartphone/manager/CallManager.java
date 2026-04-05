package com.yourname.smartphone.manager;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CallManager {
    
    private final Map<UUID, UUID> incomingCall = new HashMap<>();
    private final Map<UUID, UUID> activeCall = new HashMap<>();
    
    public void startCall(Player caller, Player target) {
        int battery = SmartphonePlugin.getInstance().getBatteryManager().getBattery(caller);
        if (battery < 5) { caller.sendMessage(TextUtils.format("&cBaterai tidak cukup untuk menelepon!")); return; }
        incomingCall.put(target.getUniqueId(), caller.getUniqueId());
        SmartphonePlugin.getInstance().getBatteryManager().addBattery(caller, -5);
        caller.sendMessage(TextUtils.format("&aMenuju &b" + target.getName() + "&a..."));
        target.sendTitle(TextUtils.format("&b☎ Panggilan masuk"), TextUtils.format("&7Dari: &f" + caller.getName()), 10, 40, 10);
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
        target.sendMessage(TextUtils.format("&a[ Ketik &f/angkat &auntuk menerima atau &f/tolak &auntuk menolak ]"));
    }
    
    public void answerCall(Player target) {
        UUID callerId = incomingCall.remove(target.getUniqueId());
        if (callerId == null) { target.sendMessage(TextUtils.format("&cTidak ada panggilan masuk!")); return; }
        Player caller = Bukkit.getPlayer(callerId);
        if (caller == null || !caller.isOnline()) { target.sendMessage(TextUtils.format("&cPenggil tidak ditemukan!")); return; }
        activeCall.put(target.getUniqueId(), caller.getUniqueId());
        activeCall.put(caller.getUniqueId(), target.getUniqueId());
        target.sendMessage(TextUtils.format("&aPanggilan dari &b" + caller.getName() + " &adiangkat!"));
        caller.sendMessage(TextUtils.format("&b" + target.getName() + " &amengangkat telepon!"));
    }
    
    public void rejectCall(Player target) {
        UUID callerId = incomingCall.remove(target.getUniqueId());
        if (callerId == null) { target.sendMessage(TextUtils.format("&cTidak ada panggilan masuk!")); return; }
        Player caller = Bukkit.getPlayer(callerId);
        if (caller != null && caller.isOnline()) caller.sendMessage(TextUtils.format("&c" + target.getName() + " &cmenolak panggilanmu!"));
        target.sendMessage(TextUtils.format("&cKamu menolak panggilan tersebut!"));
    }
    
    public void endCall(Player player) {
        UUID partnerId = activeCall.remove(player.getUniqueId());
        if (partnerId == null) { player.sendMessage(TextUtils.format("&cKamu tidak sedang dalam panggilan!")); return; }
        Player partner = Bukkit.getPlayer(partnerId);
        if (partner != null && partner.isOnline()) {
            activeCall.remove(partner.getUniqueId());
            partner.sendMessage(TextUtils.format("&c" + player.getName() + " &cmengakhiri panggilan."));
        }
        player.sendMessage(TextUtils.format("&cPanggilan diakhiri."));
    }
    
    public boolean isInCall(Player player) { return activeCall.containsKey(player.getUniqueId()); }
    
    public Player getCallPartner(Player player) {
        UUID partnerId = activeCall.get(player.getUniqueId());
        return partnerId != null ? Bukkit.getPlayer(partnerId) : null;
    }
    
    public boolean hasIncomingCall(Player player) { return incomingCall.containsKey(player.getUniqueId()); }
}

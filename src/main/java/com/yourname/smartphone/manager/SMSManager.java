package com.yourname.smartphone.manager;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SMSManager {
    
    private final Map<UUID, String> pendingSMS = new HashMap<>();
    private final Map<UUID, Boolean> smsMode = new HashMap<>();
    private final Map<UUID, Boolean> targetMode = new HashMap<>();
    private final Map<UUID, Boolean> callMode = new HashMap<>();
    private final Map<UUID, Boolean> timerMode = new HashMap<>();
    private final Map<UUID, Integer> timerTaskId = new HashMap<>();
    
    public boolean isInSMSMode(Player p) {
        return smsMode.getOrDefault(p.getUniqueId(), false);
    }
    
    public void startSMSMode(Player p) {
        smsMode.put(p.getUniqueId(), true);
        p.sendMessage(TextUtils.format("&bKetik pesanmu di chat:"));
    }
    
    public void processSMSMessage(Player p, String message) {
        if (smsMode.getOrDefault(p.getUniqueId(), false)) {
            pendingSMS.put(p.getUniqueId(), message);
            smsMode.remove(p.getUniqueId());
            targetMode.put(p.getUniqueId(), true);
            p.sendMessage(TextUtils.format("&bKirim SMS ke siapa? Ketik nama player:"));
        } else if (targetMode.getOrDefault(p.getUniqueId(), false)) {
            String smsText = pendingSMS.remove(p.getUniqueId());
            targetMode.remove(p.getUniqueId());
            Player target = Bukkit.getPlayer(message);
            if (target != null && target.isOnline()) {
                sendSMS(p, target, smsText);
            } else {
                p.sendMessage(TextUtils.format("&cPlayer &b" + message + " &ctidak online!"));
            }
        }
    }
    
    public void sendSMS(Player from, Player to, String message) {
        int battery = SmartphonePlugin.getInstance().getBatteryManager().getBattery(from);
        if (battery < 1) { from.sendMessage(TextUtils.format("&cBaterai habis!")); return; }
        to.sendMessage(TextUtils.format("&b[SMS] &7Dari &f" + from.getName() + "&7: &f" + message));
        to.playSound(to.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.5f);
        from.sendMessage(TextUtils.format("&aSMS terkirim ke &b" + to.getName() + "&a!"));
        SmartphonePlugin.getInstance().getBatteryManager().addBattery(from, -1);
    }
    
    public boolean isInCallMode(Player p) {
        return callMode.getOrDefault(p.getUniqueId(), false);
    }
    
    public void startCallMode(Player p) {
        callMode.put(p.getUniqueId(), true);
        p.sendMessage(TextUtils.format("&bKetik nama player yang ingin kamu hubungi:"));
    }
    
    public void processCallMode(Player p, String targetName) {
        if (!callMode.getOrDefault(p.getUniqueId(), false)) return;
        callMode.remove(p.getUniqueId());
        Player target = Bukkit.getPlayer(targetName);
        if (target == null || !target.isOnline()) {
            p.sendMessage(TextUtils.format("&cPlayer &b" + targetName + " &ctidak ditemukan!"));
            return;
        }
        if (target.getUniqueId().equals(p.getUniqueId())) {
            p.sendMessage(TextUtils.format("&cKamu tidak bisa menelepon diri sendiri!"));
            return;
        }
        SmartphonePlugin.getInstance().getCallManager().startCall(p, target);
    }
    
    public boolean isInTimerMode(Player p) {
        return timerMode.getOrDefault(p.getUniqueId(), false);
    }
    
    public void startTimerMode(Player p) {
        timerMode.put(p.getUniqueId(), true);
        p.sendMessage(TextUtils.format("&bMasukkan durasi timer dalam detik:"));
    }
    
    public void processTimer(Player p, String input) {
        if (!timerMode.getOrDefault(p.getUniqueId(), false)) return;
        timerMode.remove(p.getUniqueId());
        try {
            int seconds = Integer.parseInt(input);
            if (seconds <= 0) { p.sendMessage(TextUtils.format("&cMasukkan angka lebih dari 0!")); return; }
            p.sendMessage(TextUtils.format("&aTimer &b" + seconds + " detik &adimulai!"));
            if (timerTaskId.containsKey(p.getUniqueId())) {
                Bukkit.getScheduler().cancelTask(timerTaskId.get(p.getUniqueId()));
            }
            int taskId = Bukkit.getScheduler().runTaskLater(SmartphonePlugin.getInstance(), () -> {
                if (p.isOnline()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_CAT_HISS, 1.0f, 1.0f);
                    p.sendTitle(TextUtils.format("&c⏰ Timer selesai!"), TextUtils.format("&7" + seconds + " detik telah berlalu"), 10, 40, 10);
                    p.sendMessage(TextUtils.format("&c⏰ Timer &b" + seconds + " detik &ctelah selesai!"));
                }
                timerTaskId.remove(p.getUniqueId());
            }, seconds * 20L).getTaskId();
            timerTaskId.put(p.getUniqueId(), taskId);
        } catch (NumberFormatException e) {
            p.sendMessage(TextUtils.format("&cMasukkan angka yang valid!"));
        }
    }
    
    public void cancelTimer(Player p) {
        if (timerTaskId.containsKey(p.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(timerTaskId.get(p.getUniqueId()));
            timerTaskId.remove(p.getUniqueId());
            p.sendMessage(TextUtils.format("&cTimer dibatalkan!"));
        }
    }
}

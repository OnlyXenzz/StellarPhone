package com.yourname.smartphone.manager;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BatteryManager {
    
    private final Map<UUID, Integer> battery = new HashMap<>();
    private final Map<UUID, Boolean> senter = new HashMap<>();
    private final Map<UUID, Boolean> kompas = new HashMap<>();
    private final Map<UUID, Boolean> musik = new HashMap<>();
    private final Map<UUID, Boolean> batterySaver = new HashMap<>();
    private final Map<UUID, Boolean> charging = new HashMap<>();
    private final Map<UUID, Integer> chargingTaskId = new HashMap<>();
    
    public int getBattery(Player p) {
        return battery.getOrDefault(p.getUniqueId(), 100);
    }
    
    public void setBattery(Player p, int amount) {
        battery.put(p.getUniqueId(), Math.min(100, Math.max(0, amount)));
    }
    
    public void addBattery(Player p, int amount) {
        setBattery(p, getBattery(p) + amount);
    }
    
    public void startCharging(Player p) {
        if (charging.getOrDefault(p.getUniqueId(), false)) {
            p.sendMessage(TextUtils.format("&cKamu sedang mengecas! Tunggu selesai."));
            return;
        }
        if (getBattery(p) >= 100) {
            p.sendMessage(TextUtils.format("&cBaterai sudah penuh!"));
            return;
        }
        charging.put(p.getUniqueId(), true);
        p.sendMessage(TextUtils.format("&aMulai pengecasan... &7(+5 per detik)"));
        int taskId = Bukkit.getScheduler().runTaskTimer(SmartphonePlugin.getInstance(), () -> {
            if (!charging.getOrDefault(p.getUniqueId(), false) || !p.isOnline()) {
                charging.remove(p.getUniqueId());
                if (chargingTaskId.containsKey(p.getUniqueId())) {
                    Bukkit.getScheduler().cancelTask(chargingTaskId.get(p.getUniqueId()));
                    chargingTaskId.remove(p.getUniqueId());
                }
                return;
            }
            addBattery(p, 5);
            p.sendActionBar(TextUtils.format("&aMengecas... &e" + getBattery(p) + "&7%"));
            if (getBattery(p) >= 100) {
                setBattery(p, 100);
                charging.remove(p.getUniqueId());
                p.sendMessage(TextUtils.format("&aBaterai penuh!"));
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                if (chargingTaskId.containsKey(p.getUniqueId())) {
                    Bukkit.getScheduler().cancelTask(chargingTaskId.get(p.getUniqueId()));
                    chargingTaskId.remove(p.getUniqueId());
                }
            }
        }, 0L, 20L).getTaskId();
        chargingTaskId.put(p.getUniqueId(), taskId);
    }
    
    public void stopCharging(Player p) {
        if (chargingTaskId.containsKey(p.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(chargingTaskId.get(p.getUniqueId()));
            chargingTaskId.remove(p.getUniqueId());
        }
        charging.remove(p.getUniqueId());
        p.sendMessage(TextUtils.format("&cPengecasan dihentikan!"));
    }
    
    public boolean isCompassActive() {
        return kompas.values().stream().anyMatch(v -> v);
    }
    
    public void updateBattery() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getUniqueId();
            int drain = 0;
            if (senter.getOrDefault(uuid, false)) drain += 2;
            if (kompas.getOrDefault(uuid, false)) drain += 1;
            if (musik.getOrDefault(uuid, false)) drain += 1;
            if (batterySaver.getOrDefault(uuid, false)) drain = Math.max(1, drain / 2);
            if (drain > 0) {
                addBattery(p, -drain);
                if (getBattery(p) <= 0) {
                    setBattery(p, 0);
                    setSenter(p, false);
                    setKompas(p, false);
                    setMusik(p, false);
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    p.sendMessage(TextUtils.format("&cBaterai HP habis! Matikan fitur yang aktif."));
                }
            }
        }
    }
    
    public void updateCompassDisplay(Player p) {
        if (kompas.getOrDefault(p.getUniqueId(), false)) {
            int x = p.getLocation().getBlockX();
            int y = p.getLocation().getBlockY();
            int z = p.getLocation().getBlockZ();
            p.sendActionBar(TextUtils.format("&bKompas &7| &fx: &a" + x + " &fy: &a" + y + " &fz: &a" + z + " &7| &fBaterai: &e" + getBattery(p) + "&7%"));
        }
    }
    
    public boolean isSenter(Player p) { return senter.getOrDefault(p.getUniqueId(), false); }
    public void setSenter(Player p, boolean value) {
        senter.put(p.getUniqueId(), value);
        if (value) p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
        else p.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
    
    public boolean isKompas(Player p) { return kompas.getOrDefault(p.getUniqueId(), false); }
    public void setKompas(Player p, boolean value) { kompas.put(p.getUniqueId(), value); }
    
    public boolean isMusik(Player p) { return musik.getOrDefault(p.getUniqueId(), false); }
    public void setMusik(Player p, boolean value) { musik.put(p.getUniqueId(), value); }
    
    public boolean isBatterySaver(Player p) { return batterySaver.getOrDefault(p.getUniqueId(), false); }
    public void setBatterySaver(Player p, boolean value) { batterySaver.put(p.getUniqueId(), value); }
    
    public boolean isCharging(Player p) { return charging.getOrDefault(p.getUniqueId(), false); }
    public void setCharging(Player p, boolean value) { charging.put(p.getUniqueId(), value); }
}

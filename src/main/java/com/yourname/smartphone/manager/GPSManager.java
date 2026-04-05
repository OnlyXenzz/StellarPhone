package com.yourname.smartphone.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GPSManager {
    
    private final Map<UUID, Location> villageLocation = new HashMap<>();
    
    public void setVillageLocation(Player p, Location loc) { villageLocation.put(p.getUniqueId(), loc); }
    public Location getVillageLocation(Player p) { return villageLocation.get(p.getUniqueId()); }
    public boolean hasVillageLocation(Player p) { return villageLocation.containsKey(p.getUniqueId()); }
    
    public void teleportToVillage(Player p) {
        Location loc = getVillageLocation(p);
        if (loc != null) {
            p.teleport(loc);
            p.sendMessage("§aTeleport ke village...");
        }
    }
}

package com.yourname.smartphone.listeners;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        
        if (SmartphonePlugin.getInstance().getSmsManager().isInSMSMode(p)) {
            e.setCancelled(true);
            SmartphonePlugin.getInstance().getSmsManager().processSMSMessage(p, message);
            return;
        }
        
        if (SmartphonePlugin.getInstance().getSmsManager().isInCallMode(p)) {
            e.setCancelled(true);
            SmartphonePlugin.getInstance().getSmsManager().processCallMode(p, message);
            return;
        }
        
        if (SmartphonePlugin.getInstance().getCallManager().isInCall(p)) {
            e.setCancelled(true);
            Player partner = SmartphonePlugin.getInstance().getCallManager().getCallPartner(p);
            if (partner != null) {
                partner.sendMessage(TextUtils.format("&3[Telepon] &7" + p.getName() + "&7: &f" + message));
                p.sendMessage(TextUtils.format("&3[Telepon] &7Ke &b" + partner.getName() + "&7: &f" + message));
            }
            return;
        }
        
        if (message.startsWith("@")) {
            e.setCancelled(true);
            SmartphonePlugin.getInstance().getGroupChatManager().sendGroupMessage(p, message.substring(1));
            return;
        }
        
        if (SmartphonePlugin.getInstance().getSmsManager().isInTimerMode(p)) {
            e.setCancelled(true);
            SmartphonePlugin.getInstance().getSmsManager().processTimer(p, message);
        }
    }
}

package com.yourname.smartphone.listeners;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.gui.HPMenu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemClickListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null || item.getType() == Material.AIR) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        if (item.getType() == Material.COAL && meta.hasCustomModelData() && meta.getCustomModelData() == 100021) {
            e.setCancelled(true);
            new HPMenu().open(e.getPlayer());
        }

        if (item.getType() == Material.GOLDEN_SWORD && meta.hasCustomModelData() && meta.getCustomModelData() == 100022) {
            e.setCancelled(true);
            SmartphonePlugin.getInstance().getBatteryManager().startCharging(e.getPlayer());
        }
    }
}

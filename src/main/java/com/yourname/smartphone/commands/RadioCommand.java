package com.yourname.smartphone.commands;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RadioCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length < 1) { p.sendMessage(TextUtils.format("&cUsage: &7/radio <pesan>")); return true; }
        int battery = SmartphonePlugin.getInstance().getBatteryManager().getBattery(p);
        if (battery < 10) { p.sendMessage(TextUtils.format("&cBaterai tidak cukup untuk radio! (butuh 10)")); return true; }
        String message = String.join(" ", args);
        SmartphonePlugin.getInstance().getBatteryManager().addBattery(p, -10);
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(TextUtils.format("&5[Radio] &7" + p.getName() + "&7: &d" + message));
        }
        p.sendMessage(TextUtils.format("&aPesan radio terkirim ke semua player!"));
        return true;
    }
}

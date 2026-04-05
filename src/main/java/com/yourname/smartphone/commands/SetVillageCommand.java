package com.yourname.smartphone.commands;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetVillageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        SmartphonePlugin.getInstance().getGpsManager().setVillageLocation(p, p.getLocation());
        p.sendMessage(TextUtils.format("&aLokasi village berhasil disimpan!"));
        return true;
    }
}

package com.yourname.smartphone.commands;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SyncCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        p.sendMessage(TextUtils.format("&aData disinkronisasi! &7(fitur dalam pengembangan)"));
        return true;
    }
}

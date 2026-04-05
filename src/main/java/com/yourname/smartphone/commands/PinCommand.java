package com.yourname.smartphone.commands;

import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length < 1) { p.sendMessage(TextUtils.format("&cUsage: &7/pin <set/unlock> [pin]")); return true; }
        switch (args[0].toLowerCase()) {
            case "set":
                if (args.length < 2) { p.sendMessage(TextUtils.format("&cUsage: &7/pin set <pin>")); }
                else { p.sendMessage(TextUtils.format("&aPin berhasil disetting! &7(fitur dalam pengembangan)")); }
                break;
            case "unlock":
                if (args.length < 2) { p.sendMessage(TextUtils.format("&cUsage: &7/pin unlock <pin>")); }
                else { p.sendMessage(TextUtils.format("&aHP terbuka kunci!")); }
                break;
            default:
                p.sendMessage(TextUtils.format("&cUsage: &7/pin <set/unlock> [pin]"));
                break;
        }
        return true;
    }
}

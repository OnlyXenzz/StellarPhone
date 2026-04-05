package com.yourname.smartphone.commands;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length < 1) { sendHelp(p); return true; }
        switch (args[0].toLowerCase()) {
            case "save": SmartphonePlugin.getInstance().getBackupManager().backupPlayerData(p); break;
            case "list": SmartphonePlugin.getInstance().getBackupManager().listBackups(p); break;
            case "restore": if (args.length < 2) { p.sendMessage(TextUtils.format("&cUsage: /backup restore <nama_file>")); } else { SmartphonePlugin.getInstance().getBackupManager().restoreBackup(p, args[1]); } break;
            default: sendHelp(p); break;
        }
        return true;
    }
    
    private void sendHelp(Player p) {
        p.sendMessage(TextUtils.format("&b/backup save &8- &7Simpan data"));
        p.sendMessage(TextUtils.format("&b/backup list &8- &7Lihat daftar backup"));
        p.sendMessage(TextUtils.format("&b/backup restore <nama> &8- &7Pulihkan data"));
    }
}

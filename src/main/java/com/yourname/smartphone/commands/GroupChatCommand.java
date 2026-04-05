package com.yourname.smartphone.commands;

import com.yourname.smartphone.SmartphonePlugin;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GroupChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length < 1) { sendHelp(p); return true; }
        switch (args[0].toLowerCase()) {
            case "create": if (args.length < 2) { p.sendMessage(TextUtils.format("&cUsage: /groupchat create <nama>")); } else { SmartphonePlugin.getInstance().getGroupChatManager().createGroup(args[1], p); } break;
            case "join": if (args.length < 2) { p.sendMessage(TextUtils.format("&cUsage: /groupchat join <nama>")); } else { SmartphonePlugin.getInstance().getGroupChatManager().joinGroup(args[1], p); } break;
            case "leave": SmartphonePlugin.getInstance().getGroupChatManager().leaveGroup(p); break;
            case "list": SmartphonePlugin.getInstance().getGroupChatManager().listGroups(p); break;
            case "members": if (args.length < 2) { p.sendMessage(TextUtils.format("&cUsage: /groupchat members <nama>")); } else { SmartphonePlugin.getInstance().getGroupChatManager().listMembers(p, args[1]); } break;
            default: sendHelp(p); break;
        }
        return true;
    }
    
    private void sendHelp(Player p) {
        p.sendMessage(TextUtils.format("&bPerintah group chat:"));
        p.sendMessage(TextUtils.format("&7/groupchat create <nama> &8- &7Buat grup baru"));
        p.sendMessage(TextUtils.format("&7/groupchat join <nama> &8- &7Bergabung ke grup"));
        p.sendMessage(TextUtils.format("&7/groupchat leave &8- &7Keluar dari grup"));
        p.sendMessage(TextUtils.format("&7/groupchat list &8- &7Lihat daftar grup"));
        p.sendMessage(TextUtils.format("&7Untuk chat di grup, ketik &f@pesan"));
    }
}

package com.yourname.smartphone.commands;

import com.yourname.smartphone.SmartphonePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CallCommands {
    
    public static class AngkatCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) return true;
            SmartphonePlugin.getInstance().getCallManager().answerCall((Player) sender);
            return true;
        }
    }
    
    public static class TolakCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) return true;
            SmartphonePlugin.getInstance().getCallManager().rejectCall((Player) sender);
            return true;
        }
    }
    
    public static class TutupCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) return true;
            SmartphonePlugin.getInstance().getCallManager().endCall((Player) sender);
            return true;
        }
    }
}

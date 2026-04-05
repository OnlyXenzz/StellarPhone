package com.yourname.smartphone;

import com.yourname.smartphone.commands.*;
import com.yourname.smartphone.listeners.*;
import com.yourname.smartphone.manager.*;
import com.yourname.smartphone.utils.TextUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class SmartphonePlugin extends JavaPlugin {
    
    private static SmartphonePlugin instance;
    private BatteryManager batteryManager;
    private CallManager callManager;
    private SMSManager smsManager;
    private GPSManager gpsManager;
    private NotesManager notesManager;
    private PlaylistManager playlistManager;
    private GroupChatManager groupChatManager;
    private BackupManager backupManager;
    private net.milkbowl.vault.economy.Economy economy;
    
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        batteryManager = new BatteryManager();
        callManager = new CallManager();
        smsManager = new SMSManager();
        gpsManager = new GPSManager();
        notesManager = new NotesManager();
        playlistManager = new PlaylistManager();
        groupChatManager = new GroupChatManager();
        backupManager = new BackupManager();
        
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            var reg = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (reg != null) {
                economy = reg.getProvider();
                getLogger().info("Vault economy found!");
            }
        }
        
        registerCommands();
        
        getServer().getPluginManager().registerEvents(new ItemClickListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        
        getServer().getScheduler().runTaskTimer(this, batteryManager::updateBattery, 0L, 1200L);
        getServer().getScheduler().runTaskTimer(this, () -> {
            getServer().getOnlinePlayers().forEach(p -> batteryManager.updateCompassDisplay(p));
        }, 0L, 20L);
        
        getLogger().info(TextUtils.toSmallCaps("StellarPhone Enabled!"));
    }
    
    @Override
    public void onDisable() {
        backupManager.saveAllData();
        getLogger().info(TextUtils.toSmallCaps("StellarPhone Disabled!"));
    }
    
    private void registerCommands() {
        getCommand("givehp").setExecutor(new GiveHPCommand());
        getCommand("setvillage").setExecutor(new SetVillageCommand());
        getCommand("angkat").setExecutor(new CallCommands.AngkatCommand());
        getCommand("tolak").setExecutor(new CallCommands.TolakCommand());
        getCommand("tutuptelepon").setExecutor(new CallCommands.TutupCommand());
        getCommand("tokohp").setExecutor(new ShopCommand());
        getCommand("hp").setExecutor(new HPCommand());
        getCommand("radio").setExecutor(new RadioCommand());
        getCommand("groupchat").setExecutor(new GroupChatCommand());
        getCommand("backup").setExecutor(new BackupCommand());
        getCommand("sync").setExecutor(new SyncCommand());
        getCommand("pin").setExecutor(new PinCommand());
    }
    
    public static SmartphonePlugin getInstance() { return instance; }
    public BatteryManager getBatteryManager() { return batteryManager; }
    public CallManager getCallManager() { return callManager; }
    public SMSManager getSmsManager() { return smsManager; }
    public GPSManager getGpsManager() { return gpsManager; }
    public NotesManager getNotesManager() { return notesManager; }
    public PlaylistManager getPlaylistManager() { return playlistManager; }
    public GroupChatManager getGroupChatManager() { return groupChatManager; }
    public BackupManager getBackupManager() { return backupManager; }
    public net.milkbowl.vault.economy.Economy getEconomy() { return economy; }
}

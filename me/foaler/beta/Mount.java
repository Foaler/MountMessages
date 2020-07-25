package me.foaler.beta;

import me.foaler.beta.commands.MessageCommand;
import me.foaler.beta.commands.TogglePrivateMessagesCommand;
import me.foaler.beta.data.DataManager;
import me.foaler.beta.listeners.ConnectListener;
import me.foaler.beta.utils.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Mount extends JavaPlugin {

    private static Mount instance;

    private ServerVersion serverVersion;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;
        serverVersion = new ServerVersion();

        if(serverVersion.getVersion().isLower(ServerVersion.Version.v1_8_R1) || serverVersion.getVersion().isHigher(ServerVersion.Version.v1_10_R1)) {
            getLogger().info("MountMessages does not support this version (" + serverVersion.getVersion() + ")");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            dataManager = new DataManager();
            saveDefaultConfig();

            Bukkit.getServer().getPluginManager().registerEvents(new ConnectListener(), this);

            getCommand("message").setExecutor(new MessageCommand());
            getCommand("msg").setExecutor(new MessageCommand());
            getCommand("privatemessages").setExecutor(new TogglePrivateMessagesCommand());
            getCommand("toggleprivatemessages").setExecutor(new TogglePrivateMessagesCommand());
            getCommand("togglemessages").setExecutor(new TogglePrivateMessagesCommand());
        }
    }

    @Override
    public void onDisable() {

    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public static Mount getInstance() {
        return instance;
    }
}

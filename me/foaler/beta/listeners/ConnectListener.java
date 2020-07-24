package me.foaler.beta.listeners;

import me.foaler.beta.Mount;
import me.foaler.beta.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Mount.getInstance().getDataManager().getDataMap().put(event.getPlayer().getUniqueId(), new PlayerData(event.getPlayer().getUniqueId()));
        PlayerData data = Mount.getInstance().getDataManager().getData(event.getPlayer().getUniqueId());

        setup(event);
    }

    public void setup(PlayerJoinEvent event) {
        if(!Mount.getInstance().getConfig().isConfigurationSection("Users." + event.getPlayer().getName() + ".privatemessages")) {

        } else {
            Mount.getInstance().getConfig().set("Users." + event.getPlayer().getName() + ".privatemessages", true);
            Mount.getInstance().saveConfig();
        }
    }
}

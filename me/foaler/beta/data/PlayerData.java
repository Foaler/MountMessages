package me.foaler.beta.data;

import me.foaler.beta.Mount;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private Player player;

    private Map<Player, Boolean> privateMessages;
    public boolean messages;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);

        messages = Mount.getInstance().getConfig().getBoolean("Users." + getPlayer().getName() + ".privatemessages");

        privateMessages = new HashMap<>();
    }

    public Map<Player, Boolean> getPrivateMessages() {
        return privateMessages;
    }

    public Player getPlayer() {
        return player;
    }
}

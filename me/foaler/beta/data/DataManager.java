package me.foaler.beta.data;

import javax.print.attribute.HashPrintServiceAttributeSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {

    private Map<UUID, PlayerData> dataMap = new HashMap<>();

    public DataManager() {

    }

    public PlayerData getData(UUID uuid) {
        return dataMap.get(uuid);
    }

    public Map<UUID, PlayerData> getDataMap() {
        return dataMap;
    }
}

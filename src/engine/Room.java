package engine;

import java.util.HashMap;
import java.util.Map;

public class Room {
    private String name;
    private String description;
    private Map<String, String> exits = new HashMap<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setExit(String direction, String roomName) {
        exits.put(direction, roomName);
    }

    public String getDescription() {
        return name + ": " + description;
    }
}
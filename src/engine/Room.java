package engine;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private Map<String, String> exits = new HashMap<>();
    private List<String> items = new ArrayList<>();

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

    public String getExit(String direction) {
        return exits.get(direction);
    }

    // ΠΡΟΣΘΕΣΕ ΑΥΤΟ: Για να μπορεί ο WorldBuilder να διαβάζει τις εξόδους από το JSON
    public Map<String, String> getExits() {
        return exits;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public String getName() {
        return name;
    }
}
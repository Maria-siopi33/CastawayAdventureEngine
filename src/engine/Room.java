package engine;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Room {
    private String name;
    private String description;

    // 1. Διόρθωση: List<String> αντί για List<Item>
    private Map<Direction, Room> exits = new HashMap<>();

    private List<Item> items = new ArrayList<>();
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // 2. Διόρθωση: Δέχεται Direction και Room αντί για Strings
    public void setExit(Direction direction, Room room) {
        exits.put(direction, room);
    }

    public String getDescription() {
        return name + ": " + description;
    }

    // 3. Διόρθωση: Δέχεται Direction και επιστρέφει αντικείμενο Room
    public Room getExit(Direction direction) {
        return exits.get(direction);
    }

    // 4. Διόρθωση: Επιστρέφει το σωστό Map
    public Map<Direction, Room> getExits() {
        return exits;
    }

    // Η μέθοδος δέχεται πλέον το Object (Item) και όχι String
    public void addItem(Item item) {
        items.add(item);
    }
    // αυτό θα μας χρειαστεί σύντομα για το LookCommand!
    public List<Item> getItems() {
        return items;
    }
    public String getName() {
        return name;
    }
}
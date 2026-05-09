package engine;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Room {
    private String name;
    private String description;

    private Map<Direction, Room> exits = new HashMap<>();

    private List<Item> items = new ArrayList<>();
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Δέχεται Direction και Room αντί για Strings
    public void setExit(Direction direction, Room room) {
        exits.put(direction, room);
    }

    public String getDescription() {
        return name + ": " + description;
    }

    // Δέχεται Direction και επιστρέφει αντικείμενο Room
    public Room getExit(Direction direction) {
        return exits.get(direction);
    }

    // Επιστρέφει το σωστό Map
    public Map<Direction, Room> getExits() {
        return exits;
    }

    // Η μέθοδος δέχεται πλέον το Object (Item) και όχι String
    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    // Βοηθητική μέθοδος για να βρίσκουμε ένα αντικείμενο στο δωμάτιο με το όνομά του
    public Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }
}
package engine;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private String description;

    private Map<Direction, Room> exits = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private Map<String, String> lockedObjects = new HashMap<>();
    private List<NPC> npcs = new ArrayList<>(); // Λίστα με NPCs στο δωμάτιο

    // ΠΡΟΣΘΗΚΗ: Το δωμάτιο γνωρίζει αν περιέχει νερό (για την SwimCommand)
    private boolean hasWater = false;
    private boolean isWinRoom = false;

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

    // ΠΡΟΣΘΗΚΗ: Getter και Setter για τη διαχείριση της ιδιότητας του νερού
    public void setHasWater(boolean hasWater) {
        this.hasWater = hasWater;
    }

    public boolean hasWater() {
        return hasWater;
    }
    public void addLockedObject(String objectName, String keyName) {
        lockedObjects.put(objectName.toLowerCase(), keyName.toLowerCase());
    }

    public boolean isLocked(String objectName) {
        return lockedObjects.containsKey(objectName.toLowerCase());
    }

    public String getRequiredKey(String objectName) {
        return lockedObjects.get(objectName.toLowerCase());
    }

    public void unlockObject(String objectName) {
        lockedObjects.remove(objectName.toLowerCase());
    }
    public boolean isWinRoom() { return isWinRoom; }
    public void setWinRoom(boolean winRoom) { isWinRoom = winRoom; }
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public List<NPC> getNPCs() {
        return npcs;
    }
    public NPC findNPC(String npcName) {
        for (NPC npc : npcs) {
            if (npc.getName().equalsIgnoreCase(npcName)) {
                return npc;
            }
        }
        return null;
    }
}
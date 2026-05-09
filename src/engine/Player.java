package engine;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Room currentRoom;
    private List<CarryableItem> inventory;

    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
    }

    // Getters & Setters
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    // Διαχείριση Inventory
    public void addItem(CarryableItem item) {
        inventory.add(item);
    }

    public void removeItem(CarryableItem item) {
        inventory.remove(item);
    }

    public List<CarryableItem> getInventory() {
        return inventory;
    }

    public boolean hasItem(String itemName) {
        return inventory.stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }
    // Επιστρέφει το αντικείμενο αν υπάρχει, αλλιώς null
    public CarryableItem findInInventory(String itemName) {
        for (CarryableItem item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
}
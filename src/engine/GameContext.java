package engine;

public class GameContext {
    private Room currentRoom;

    // Ο Constructor: Δέχεται το δωμάτιο στο οποίο ξεκινάει ο παίκτης
    public GameContext(Room startingRoom) {
        this.currentRoom = startingRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
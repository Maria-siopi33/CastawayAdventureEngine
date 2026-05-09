package engine;

public class GameContext {
    // Αντί για Room, τώρα κρατάμε την οντότητα Player
    private Player player;
    private boolean isRunning; // Χρήσιμο για να ξέρει το engine πότε τελειώνει το παιχνίδι

    // Ο Constructor δέχεται πλέον το αντικείμενο Player
    public GameContext(Player player) {
        this.player = player;
        this.isRunning = true;
    }

    public Player getPlayer() {
        return player;
    }

    // Helper μέθοδος: Επιστρέφει το δωμάτιο του παίκτη
    // Έτσι δεν χρειάζεται να αλλάξεις παντού τον παλιό σου κώδικα
    public Room getCurrentRoom() {
        return player.getCurrentRoom();
    }

    // Helper μέθοδος: Αλλάζει το δωμάτιο μέσω του Player
    public void setCurrentRoom(Room room) {
        player.setCurrentRoom(room);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
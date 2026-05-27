package engine;

public class GameContext {
    private Player player;
    private boolean isRunning;
    private boolean isEasyMode; // Η επιλογή του παίκτη

    public GameContext(Player player, boolean isEasyMode) {
        this.player = player;
        this.isRunning = true;
        this.isEasyMode = isEasyMode;
    }

    public Player getPlayer() {
        return player;
    }

    public Room getCurrentRoom() {
        return player.getCurrentRoom();
    }

    public void setCurrentRoom(Room room) {
        player.setCurrentRoom(room);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public boolean isEasyMode() {
        return isEasyMode;
    }
}
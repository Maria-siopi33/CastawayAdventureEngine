package command;

import engine.GameContext;
import engine.Item; // Βεβαιώσου ότι έχεις κάνει import το Item
import engine.Room;
import java.util.List;

public class LookCommand implements Command {
    private GameContext context;

    public LookCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        Room currentRoom = context.getCurrentRoom();
        StringBuilder response = new StringBuilder();

        // 1. Περιγραφή δωματίου
        response.append(currentRoom.getDescription()).append("\n");

        // 2. Έλεγχος για αντικείμενα στο δωμάτιο
        if (!currentRoom.getItems().isEmpty()) {
            response.append("\nΣτο χώρο βλέπεις τα εξής αντικείμενα:\n");
            for (Item item : currentRoom.getItems()) {
                response.append("- ").append(item.getName())
                        .append(": ").append(item.getDescription()).append("\n");
            }
        } else {
            response.append("\nΔεν φαίνεται να υπάρχει κάτι χρήσιμο στο έδαφος.");
        }

        return response.toString();
    }
}
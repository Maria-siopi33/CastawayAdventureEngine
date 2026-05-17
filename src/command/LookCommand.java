package command;

import engine.GameContext;
import engine.Item;
import engine.Room;
import engine.Direction;
import parser.ParsedCommand;
import java.util.Map;

public class LookCommand implements Command {
    private GameContext context;

    public LookCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        Room currentRoom = context.getCurrentRoom();
        String target = command.getDirectObject();

        // 1. Αν ο παίκτης έγραψε "look at [item]" (στοχευμένη εξέταση)
        if (target != null && !target.isEmpty()) {
            // Ψάχνουμε πρώτα στην τσάντα του παίκτη
            Item itemToLook = context.getPlayer().findInInventory(target);

            // Αν δεν το έχει, ψάχνουμε κάτω στο δωμάτιο
            if (itemToLook == null) {
                itemToLook = currentRoom.findItem(target);
            }

            if (itemToLook != null) {
                return itemToLook.getName() + ": " + itemToLook.getDescription();
            } else {
                return "You don't see any " + target + " here to look at.";
            }
        }

        // 2. Αν ο παίκτης έγραψε απλά "look" (Γενική εξέταση χώρου)
        StringBuilder response = new StringBuilder();

        // Περιγραφή δωματίου
        response.append(currentRoom.getDescription()).append("\n");

        // Εμφάνιση Διαθέσιμων Εξόδων
        response.append("\nExits: ");
        Map<Direction, Room> exits = currentRoom.getExits();
        if (exits.isEmpty()) {
            response.append("None. You are trapped!");
        } else {
            for (Direction dir : exits.keySet()) {
                response.append(dir.name()).append(" ");
            }
        }
        response.append("\n");

        // Έλεγχος για αντικείμενα στο δωμάτιο
        if (!currentRoom.getItems().isEmpty()) {
            response.append("\nΣτο χώρο βλέπεις τα εξής αντικείμενα:\n");
            for (Item item : currentRoom.getItems()) {
                response.append("- ").append(item.getName()).append("\n");
            }
        }

        return response.toString().trim();
    }
}
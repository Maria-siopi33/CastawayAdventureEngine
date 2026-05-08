package command;

import engine.GameContext;
import engine.Room;
import java.util.List;

public class TakeCommand implements Command {
    private GameContext context;

    public TakeCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        // Έλεγχος αν ο χρήστης έγραψε μόνο "take"
        if (tokens.size() < 2) {
            return "What do you want to take?";
        }

        // Παίρνουμε το όνομα του αντικειμένου από τα tokens
        String itemName = tokens.get(1);

        // Παίρνουμε το τρέχον δωμάτιο από το context
        Room currentRoom = context.getCurrentRoom();

        // Έλεγχος αν το αντικείμενο υπάρχει στο δωμάτιο
        // Υποθέτουμε ότι η κλάση Room έχει μια μέθοδο hasItem(String name)
        if (currentRoom.getItems().contains(itemName)) {

            /* ΠΡΟΣΩΡΙΝΟ: Εδώ κανονικά θα γινόταν:
               currentRoom.removeItem(itemName);
               context.getPlayer().getInventory().add(itemName);
            */

            return "You picked up the " + itemName + ". (It's now in your imaginary bag!)";
        } else {
            // Αν το αντικείμενο δεν υπάρχει στο JSON του δωματίου
            return "There is no " + itemName + " here.";
        }
    }
}
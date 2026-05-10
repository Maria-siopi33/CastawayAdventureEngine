package command;

import engine.*;
import java.util.List;

public class TakeCommand implements Command {
    private GameContext context;

    public TakeCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        // 1. Έλεγχος αν ο χρήστης έγραψε τι θέλει να πάρει
        if (tokens.size() < 2) {
            return "What do you want to take?";
        }

        String itemName = tokens.get(1);
        Room currentRoom = context.getCurrentRoom();

        // 2. Χρησιμοποιούμε τη νέα μέθοδο findItem που πρόσθεσε η ομάδα στην Room
        // Αυτή επιστρέφει αντικείμενο τύπου Item, όχι String!
        Item item = currentRoom.findItem(itemName);

        // 3. Έλεγχος αν το αντικείμενο υπάρχει όντως στο δωμάτιο
        if (item == null) {
            return "There is no " + itemName + " here.";
        }

        // 4. ΕΔΩ ΕΙΝΑΙ ΤΟ ΚΛΕΙΔΙ: Έλεγχος αν το αντικείμενο μπορεί να μεταφερθεί
        // Χρησιμοποιούμε την "instanceof" για να δούμε αν είναι CarryableItem
        if (item instanceof CarryableItem) {

            // Το προσθέτουμε στο Inventory του Player (μέσω του context)
            context.getPlayer().addItem((CarryableItem) item);

            // Το αφαιρούμε από το δωμάτιο
            currentRoom.removeItem(item);

            return "You picked up the " + item.getName() + ". It's now in your bag!";
        } else {
            // Αν είναι π.χ. StaticItem (όπως η Rock στο JSON σας)
            return "The " + item.getName() + " is too heavy or stuck. You can't take it.";
        }
    }
}
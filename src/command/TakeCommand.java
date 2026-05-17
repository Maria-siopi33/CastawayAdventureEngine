package command;

import engine.*;
import parser.ParsedCommand; // Προσθήκη του import

public class TakeCommand implements Command {
    private GameContext context;

    public TakeCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        String itemName = command.getDirectObject();

        if (itemName == null || itemName.isEmpty()) {
            return "What do you want to take?";
        }

        Room currentRoom = context.getCurrentRoom();
        Item item = currentRoom.findItem(itemName);

        if (item == null) {
            return "There is no " + itemName + " here.";
        }

        // Η ΑΝΤΙΚΕΙΜΕΝΟΣΤΡΑΦΗΣ ΜΑΓΕΙΑ ΕΔΩ: Χωρίς instanceof!
        if (item.canBePickedUp()) {
            context.getPlayer().addItem((CarryableItem) item);
            currentRoom.removeItem(item);
            return "You picked up the " + item.getName() + ". It's now in your bag!";
        } else {
            return "The " + item.getName() + " is too heavy or stuck. You can't take it.";
        }
    }
}
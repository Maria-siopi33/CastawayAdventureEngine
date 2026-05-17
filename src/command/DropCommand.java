package command;

import engine.GameContext;
import parser.ParsedCommand;

public class DropCommand implements Command {
    private GameContext context;

    public DropCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        String itemName = command.getDirectObject();
        if (itemName == null || itemName.isEmpty()) {
            return "What do you want to drop?";
        }
        // Εδώ βρίσκουμε το carryable item στην τσάντα
        engine.CarryableItem item = context.getPlayer().findInInventory(itemName);

        if (item != null) {
            context.getPlayer().removeItem(item);
            // Εδώ το προσθέτουμε στο δωμάτιο ως Item
            context.getCurrentRoom().addItem((engine.Item) item);
            return "You dropped the " + itemName + ".";
        }
        return "You don't have a " + itemName + " in your bag.";
    }
}
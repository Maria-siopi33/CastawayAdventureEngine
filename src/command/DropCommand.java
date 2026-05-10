package command;

import engine.GameContext;
import java.util.List;


public class DropCommand implements Command {
    private GameContext context;

    public DropCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        if (tokens.size() < 2) return "What do you want to drop?";

        String itemName = tokens.get(1);
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
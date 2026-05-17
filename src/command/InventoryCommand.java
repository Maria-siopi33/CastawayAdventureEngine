package command;

import engine.GameContext;
import engine.CarryableItem;
import parser.ParsedCommand;
import java.util.List;

public class InventoryCommand implements Command {
    private GameContext context;

    public InventoryCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        List<CarryableItem> bag = context.getPlayer().getInventory();

        if (bag.isEmpty()) {
            return "Your bag is empty. Go find some items!";
        }

        StringBuilder response = new StringBuilder("You look inside your bag and find:\n");
        for (CarryableItem item : bag) {
            response.append("- ").append(item.getName())
                    .append(" (").append(item.getDescription()).append(")\n");
        }

        return response.toString().trim();
    }
}
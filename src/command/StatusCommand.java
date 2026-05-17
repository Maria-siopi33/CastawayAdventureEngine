package command;

import engine.GameContext;
import parser.ParsedCommand;

public class StatusCommand implements Command {
    private GameContext context;

    public StatusCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        String currentRoomName = context.getCurrentRoom().getName();
        int itemTotal = context.getPlayer().getInventory().size();

        StringBuilder status = new StringBuilder();
        status.append("=== PLAYER STATUS ===\n");
        status.append("Location: ").append(currentRoomName).append("\n");
        status.append("Items in bag: ").append(itemTotal).append("\n");

        // Μελλοντικά αν βάλετε Health/HP, θα προστίθεται εδώ!
        // status.append("Health: 100/100\n");

        status.append("=====================");

        return status.toString();
    }
}
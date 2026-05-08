package command;

import engine.Direction;
import engine.GameContext;
import engine.Room;
import java.util.List;
import java.util.Map;

public class GoCommand implements Command {
    private GameContext context;

    public GoCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        if (tokens.size() < 2) {
            return "Προς τα πού; (π.χ. go north)";
        }

        String directionStr = tokens.get(1).toUpperCase();
        Direction direction;
        try {
            direction = Direction.valueOf(directionStr);
        } catch (IllegalArgumentException e) {
            return "Δεν είναι έγκυρη κατεύθυνση (North, South, East, West).";
        }

        Room nextRoom = context.getCurrentRoom().getExit(direction);

        if (nextRoom != null) {
            context.setCurrentRoom(nextRoom);
            // Επιστρέφουμε το κείμενο ως ένα ενιαίο String
            /*return "Πήγες στο: " + context.getCurrentRoom().getName() + "\n" +
                    context.getCurrentRoom().getDescription();*/
            return "You are at the " + nextRoom.getName().toLowerCase() + ".\n" +
                    nextRoom.getDescription().replace(nextRoom.getName() + ":", "").trim();
        } else {
            return "Δεν μπορείς να πας προς τα εκεί.";
        }
    }
}
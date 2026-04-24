package command;

import engine.Direction;
import engine.GameContext;
import engine.Room;
import java.util.List;
import java.util.Map;

public class GoCommand implements Command {
    private GameContext context;
    private Map<String, Room> world;

    public GoCommand(GameContext context, Map<String, Room> world) {
        this.context = context;
        this.world = world;
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
            return "Πήγες στο: " + context.getCurrentRoom().getName() + "\n" +
                    context.getCurrentRoom().getDescription();
        } else {
            return "Δεν μπορείς να πας προς τα εκεί.";
        }
    }
}
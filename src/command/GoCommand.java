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
    public void execute(List<String> tokens) {
        if (tokens.size() < 2) {
            System.out.println("Προς τα πού; (π.χ. go north)");
            return;
        }

        // 1. Παίρνουμε την κατεύθυνση και προσπαθούμε να τη μετατρέψουμε σε Enum
        String directionStr = tokens.get(1).toUpperCase();
        Direction direction;
        try {
            direction = Direction.valueOf(directionStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Δεν είναι έγκυρη κατεύθυνση (North, South, East, West).");
            return;
        }

        // 2. Ζητάμε το αντικείμενο Room απευθείας
        Room nextRoom = context.getCurrentRoom().getExit(direction);

        // 3. Αν υπάρχει, πάμε εκεί
        if (nextRoom != null) {
            context.setCurrentRoom(nextRoom);
            System.out.println("Πήγες στο: " + context.getCurrentRoom().getName());
            System.out.println(context.getCurrentRoom().getDescription());
        } else {
            System.out.println("Δεν μπορείς να πας προς τα εκεί.");
        }
    }
}
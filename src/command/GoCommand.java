package command;

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

        String direction = tokens.get(1);
        String nextRoomId = context.getCurrentRoom().getExit(direction);

        if (nextRoomId != null && world.containsKey(nextRoomId)) {
            context.setCurrentRoom(world.get(nextRoomId));//το νεο δωματιο ->τρεχον
            System.out.println("Πήγες στο: " + context.getCurrentRoom().getName());
            System.out.println(context.getCurrentRoom().getDescription());
            //εδω θα μπορουσε να λεει ποιες ειναι οι διαθεσιμες εξοδους απο το δωματιο
        } else {
            System.out.println("Δεν μπορείς να πας προς τα εκεί.");
        }
    }
}
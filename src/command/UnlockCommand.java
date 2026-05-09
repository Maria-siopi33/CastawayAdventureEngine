package command;

import engine.GameContext;
import engine.Room;
import java.util.List;

public class UnlockCommand implements Command {
    private GameContext context;

    public UnlockCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        // Έλεγχος αν ο παίκτης έγραψε τι θέλει να ξεκλειδώσει
        if (tokens.size() < 2) {
            return "What are you trying to unlock?";
        }

        String target = tokens.get(1).toLowerCase();
        Room currentRoom = context.getCurrentRoom();
        String desc = currentRoom.getDescription().toLowerCase();

        // Έλεγχος αν αυτό που θέλει να ξεκλειδώσει υπάρχει στην περιγραφή
        // Π.χ. αν η περιγραφή λέει "There is a locked chest here"
        if (desc.contains(target)) {
            return "You try to unlock the " + target + "... but you'll need a key for that. (Inventory system coming soon!)";
        } else {
            return "I don't see any " + target + " here to unlock.";
        }
    }
}
package command;

import engine.GameContext;
import engine.Room;
import java.util.List;

public class SwimCommand implements Command {
    private GameContext context;

    public SwimCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        Room currentRoom = context.getCurrentRoom();
        String desc = currentRoom.getDescription().toLowerCase();
        String name = currentRoom.getName().toLowerCase();

        // Ελέγχουμε αν το όνομα ή η περιγραφή παραπέμπουν σε παραλία
        if (name.contains("beach") || desc.contains("shore") || desc.contains("beach")) {
            return "You take a deep dive!";
        } else {
            return "There's no water here. You can't swim in the " + name + "!";
        }
    }
}
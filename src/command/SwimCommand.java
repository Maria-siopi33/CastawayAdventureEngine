package command;

import engine.GameContext;
import engine.Room;
import parser.ParsedCommand;

public class SwimCommand implements Command {
    private GameContext context;

    public SwimCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        Room currentRoom = context.getCurrentRoom();

        // Η εντολή απλώς ρωτάει το δωμάτιο αν έχει νερό!
        if (currentRoom.hasWater()) {
            return "You take a deep dive!";
        } else {
            return "There's no water here. You can't swim in the " + currentRoom.getName() + "!";
        }
    }
}
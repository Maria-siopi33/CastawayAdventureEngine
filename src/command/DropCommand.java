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
        if (tokens.size() < 2) {
            return "What do you want to drop?";
        }
        return "You dropped the " + tokens.get(1) + ".";
    }
}
package command;

import engine.GameContext;
import java.util.List;

public class LookCommand implements Command {
    private GameContext context;

    public LookCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        return context.getCurrentRoom().getDescription();
    }
}
package command;

import engine.GameContext;
import java.util.List;

public class LookCommand implements Command {
    private GameContext context;

    public LookCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public void execute(List<String> tokens) {
        System.out.println(context.getCurrentRoom().getDescription());
    }
}
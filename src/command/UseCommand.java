package command;

import engine.GameContext;
import java.util.List;

public class UseCommand implements Command {
    private GameContext context;

    public UseCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(List<String> tokens) {
        if (tokens.size() < 2) {
            return "What do you want to use?";
        }

        String item = tokens.get(1);
        // Προσωρινή απάντηση μέχρι να βαλουμε αντικείμενα
        return "You attempt to use the " + item + ". It doesn't seem to do anything right now, but it might be useful later!";
    }
}
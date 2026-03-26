package command;

import engine.GameContext;
import engine.Room;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, String> aliases = new HashMap<>();

    public CommandHandler(GameContext context, Map<String, Room> world) {
        commands.put("go", new GoCommand(context, world));
        commands.put("look", new LookCommand(context));

        // συνώνυμα
        aliases.put("walk", "go");
        aliases.put("move", "go");
    }

    public void handle(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) return;

        String inputVerb = tokens.get(0).toLowerCase();

        // Αν η λεξη ειναι συνωνυμο
        String officialVerb = aliases.getOrDefault(inputVerb, inputVerb);

        // Τώρα ψάξε την επίσημη εντολή
        Command cmd = commands.get(officialVerb);

        if (cmd != null) {
            cmd.execute(tokens);
        } else {
            System.out.println("Δεν γνωρίζω την εντολή: " + inputVerb);
        }
    }
}
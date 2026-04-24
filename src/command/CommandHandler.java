package command;

import engine.GameContext;
import engine.Room;
import parser.ParsedCommand;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, String> aliases = new HashMap<>();

    public CommandHandler(GameContext context, Map<String, Room> world) {
        commands.put("go", new GoCommand(context, world));
        commands.put("look", new LookCommand(context));

        // Συνώνυμα
        aliases.put("walk", "go");
        aliases.put("move", "go");
    }

    public String handle(ParsedCommand pc) {
        if (pc == null || pc.getAction().equals("UNKNOWN")) {
            return "Δεν κατάλαβα τι θέλεις να κάνεις.";
        }

        String inputVerb = pc.getAction().toLowerCase();
        String officialVerb = aliases.getOrDefault(inputVerb, inputVerb);

        Command cmd = commands.get(officialVerb);

        if (cmd != null) {
            List<String> tokens = new ArrayList<>();
            tokens.add(officialVerb);
            if (!pc.getDirectObject().isEmpty()) {
                tokens.add(pc.getDirectObject());
            }

            return cmd.execute(tokens);
        } else {
            return "Δεν γνωρίζω την εντολή: " + inputVerb;
        }
    }
}
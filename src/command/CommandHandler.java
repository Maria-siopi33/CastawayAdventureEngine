package command;

import engine.GameContext;
import engine.Room;
import parser.ParsedCommand;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CommandHandler {
    // Κρατάμε μόνο το Map των εντολών
    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler(GameContext context, Map<String, Room> world) {
        commands.put("GO", new GoCommand(context));
        commands.put("LOOK", new LookCommand(context));
        commands.put("TAKE", new TakeCommand(context));
        commands.put("USE", new UseCommand(context));
        commands.put("SWIM", new SwimCommand(context));
        commands.put("UNLOCK", new UnlockCommand(context));
        commands.put("DROP", new DropCommand(context));
    }

    public String handle(ParsedCommand pc) {
        if (pc == null || pc.getAction().equals("UNKNOWN")) {
            return "I don't understand what you want to do..";
        }

        // Παίρνουμε την εντολή απευθείας από τον Parser
        String action = pc.getAction();

        Command cmd = commands.get(action);

        if (cmd != null) {
            List<String> tokens = new ArrayList<>();
            tokens.add(action);

            // Αν υπάρχει αντικείμενο (π.χ. "shell"), το προσθέτουμε στα tokens
            if (!pc.getDirectObject().isEmpty()) {
                tokens.add(pc.getDirectObject());
            }

            return cmd.execute(tokens);
        } else {
            return "Η εντολή '" + action + "' αναγνωρίστηκε αλλά δεν έχει υλοποιηθεί ακόμα.";
        }
    }
}
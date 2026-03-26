package command;

import engine.GameContext;
import engine.Room;
import parser.ParsedCommand; // 1. ΠΡΟΣΘΕΣΕ ΑΥΤΟ ΤΟ IMPORT
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList; // Χρειάζεται για τη μετατροπή παρακάτω

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

    // 2. ΑΛΛΑΓΗ ΣΤΗΝ ΠΑΡΑΜΕΤΡΟ: Από List<String> σε ParsedCommand
    public void handle(ParsedCommand pc) {
        if (pc == null) return;

        // Παίρνουμε το ρήμα από το αντικείμενο ParsedCommand
        String inputVerb = pc.getAction().toLowerCase();

        // Αν η λέξη είναι συνώνυμο (αν και ο Parser το κάνει ήδη, το κρατάμε για ασφάλεια)
        String officialVerb = aliases.getOrDefault(inputVerb, inputVerb);

        Command cmd = commands.get(officialVerb);

        if (cmd != null) {
            // 3. ΜΕΤΑΤΡΟΠΗ: Οι εντολές σου (GoCommand, κλπ) περιμένουν List<String>.
            // Φτιάχνουμε μια λίστα "tokens" από το ParsedCommand για να μην χαλάσουν οι εντολές.
            List<String> tokens = new ArrayList<>();
            tokens.add(officialVerb); // π.χ. "go"
            if (!pc.getDirectObject().isEmpty()) {
                tokens.add(pc.getDirectObject()); // π.χ. "north"
            }

            cmd.execute(tokens);
        } else {
            System.out.println("Δεν γνωρίζω την εντολή: " + inputVerb);
        }
    }
}
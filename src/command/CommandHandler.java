package command;

import parser.ParsedCommand;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CommandHandler {
    // Κρατάμε το Map που θα μας έρθει από τη Main
    private final Map<String, Command> commands;

    // Ο Constructor δέχεται πλέον ΜΟΝΟ το έτοιμο Map
    public CommandHandler(Map<String, Command> commands) {
        this.commands = commands;
    }

    public String handle(ParsedCommand pc) {
        if (pc == null || pc.getAction() == null || pc.getAction().equals("UNKNOWN")) {
            return "I don't understand what you want to do..";
        }

        String action = pc.getAction();
        Command cmd = commands.get(action);

        if (cmd != null) {
            // Μετατρέπουμε το ParsedCommand σε List<String> για να είναι συμβατό
            // με την execute(List<String> tokens) που έχουν ήδη οι εντολές σας
            List<String> tokens = new ArrayList<>();
            tokens.add(action);

            if (pc.getDirectObject() != null && !pc.getDirectObject().isEmpty()) {
                tokens.add(pc.getDirectObject());
            }

            return cmd.execute(tokens);
        } else {
            return "The command '" + action + "' is recognized but not implemented yet.";
        }
    }
}
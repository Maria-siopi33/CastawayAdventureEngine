package command;

import parser.ParsedCommand;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> commands;

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
            // Η ΜΑΓΕΙΑ ΕΔΩ: Του περνάμε απευθείας το αντικείμενο, χωρίς μετατροπές!
            return cmd.execute(pc);
        } else {
            return "The command '" + action + "' is recognized but not implemented yet.";
        }
    }
}
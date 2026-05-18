package command;

import engine.GameContext;
import engine.NPC;
import parser.ParsedCommand;

public class TalkCommand implements Command {
    private GameContext context;

    public TalkCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        String targetNPCName = command.getDirectObject();

        // --- FIX ΓΙΑ ΤΟ PARSER BUG ("talk to ghost") ---
        if (targetNPCName.isEmpty() && command.getIndirectObject() != null && !command.getIndirectObject().isEmpty()) {
            targetNPCName = command.getIndirectObject();
        }

        if (targetNPCName == null || targetNPCName.isEmpty()) {
            return "Who do you want to talk to?";
        }

        // Ψάχνουμε τον NPC στο τρέχον δωμάτιο
        NPC npc = context.getCurrentRoom().findNPC(targetNPCName);

        if (npc == null) {
            return "There is no one here named '" + targetNPCName + "'.";
        }

        // Τυπώνουμε το διάλογο ανάλογα με το State του!
        return npc.getName() + " says: \"" + npc.getDialogue() + "\"";
    }
}
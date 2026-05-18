package command;

import engine.GameContext;
import engine.Item;
import engine.Room;
import engine.Direction;
import engine.NPC;
import parser.ParsedCommand;
import java.util.Map;

public class LookCommand implements Command {
    private GameContext context;

    public LookCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        Room currentRoom = context.getCurrentRoom();
        String target = command.getDirectObject();

        // --- FIX ΓΙΑ ΤΟ PARSER BUG ("look at ghost") ---
        if (target != null && target.isEmpty() && command.getIndirectObject() != null && !command.getIndirectObject().isEmpty()) {
            target = command.getIndirectObject();
        }

        // 1. Στοχευμένη εξέταση (look at [item] ή [npc])
        if (target != null && !target.isEmpty()) {

            // Η ΓΡΑΜΜΗ ΠΟΥ ΕΛΕΙΠΕ: Ψάχνουμε πρώτα στην τσάντα του παίκτη
            Item itemToLook = context.getPlayer().findInInventory(target);

            // Αν δεν το έχει, ψάχνουμε κάτω στο δωμάτιο
            if (itemToLook == null) {
                itemToLook = currentRoom.findItem(target);
            }

            if (itemToLook != null) {
                return itemToLook.getName() + ": " + itemToLook.getDescription();
            }

            // ΠΡΟΣΘΗΚΗ: Ψάχνουμε μήπως είναι NPC (π.χ. look at ghost)
            NPC npcToLook = currentRoom.findNPC(target);
            if (npcToLook != null) {
                return npcToLook.getName() + ": " + npcToLook.getDescription();
            }

            return "You don't see any '" + target + "' here to look at.";
        }

        // 2. Γενική εξέταση χώρου (look)
        StringBuilder response = new StringBuilder();

        // Περιγραφή δωματίου
        response.append(currentRoom.getDescription()).append("\n");

        // Εμφάνιση Διαθέσιμων Εξόδων
        response.append("\nExits: ");
        Map<Direction, Room> exits = currentRoom.getExits();
        if (exits.isEmpty()) {
            response.append("None. You are trapped!");
        } else {
            for (Direction dir : exits.keySet()) {
                response.append(dir.name()).append(" ");
            }
        }
        response.append("\n");

        // Έλεγχος για αντικείμενα στο δωμάτιο
        if (!currentRoom.getItems().isEmpty()) {
            response.append("\nYou see the following items here:\n");
            for (Item item : currentRoom.getItems()) {
                response.append("- ").append(item.getName()).append("\n");
            }
        }

        // Έλεγχος για NPCs στο δωμάτιο
        if (!currentRoom.getNPCs().isEmpty()) {
            response.append("\nCharacters present:\n");
            for (NPC npc : currentRoom.getNPCs()) {
                response.append("- ").append(npc.getName()).append("\n");
            }
        }

        return response.toString().trim();
    }
}
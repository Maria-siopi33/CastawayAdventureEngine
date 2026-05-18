package command;

import engine.GameContext;
import engine.Room;
import engine.NPC;
import engine.CarryableItem;
import parser.ParsedCommand;

public class UseCommand implements Command {
    private GameContext context;

    public UseCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        String itemToUse = command.getDirectObject();
        String target = command.getIndirectObject();

        if (itemToUse == null || itemToUse.isEmpty()) {
            return "What do you want to use?";
        }

        if (!context.getPlayer().hasItem(itemToUse)) {
            return "You don't have a " + itemToUse + " in your bag!";
        }

        if (target != null && !target.isEmpty()) {
            Room currentRoom = context.getCurrentRoom();

            // --- ΕΛΕΓΧΟΣ ΓΙΑ ΧΡΗΣΗ ΠΑΝΩ ΣΕ NPC (Ghost) ---
            if (itemToUse.equalsIgnoreCase("coin") && target.equalsIgnoreCase("ghost")) {
                NPC ghost = currentRoom.findNPC("ghost");
                if (ghost != null && ghost.getCurrentState() == NPC.State.BLOCK) {
                    // Αλλάζουμε το State σε TALK
                    ghost.setState(NPC.State.TALK);
                    return "You offer the gold coin to the ghost captain. He snatches it, his expression softening. He steps aside, allowing you to pass north!\n" +
                            "[SYSTEM]: The Ghost is now friendly. You can move North.";
                }
            }

            // --- ΕΛΕΓΧΟΣ ΓΙΑ ΚΛΕΙΔΩΜΕΝΑ ΑΝΤΙΚΕΙΜΕΝΑ (Chest) ---
            if (currentRoom.isLocked(target)) {
                String requiredKey = currentRoom.getRequiredKey(target);
                if (requiredKey.equalsIgnoreCase(itemToUse)) {
                    currentRoom.unlockObject(target);
                    return "You used the " + itemToUse + " on the " + target + ". It clicked open!";
                } else {
                    return "The " + itemToUse + " doesn't seem to work on the " + target + ".";
                }
            } else {
                return "You can't use the " + itemToUse + " on the " + target + " here.";
            }
        }

        // Αν το χρησιμοποιεί σκέτο (π.χ. use machete)
        CarryableItem itemInBag = context.getPlayer().findInInventory(itemToUse);
        return itemInBag.getUseMessage();
    }
}
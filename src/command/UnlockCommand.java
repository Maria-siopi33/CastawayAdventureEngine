package command;

import engine.GameContext;
import engine.Room;
import parser.ParsedCommand;

public class UnlockCommand implements Command {
    private GameContext context;

    public UnlockCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        String target = command.getDirectObject(); // π.χ. chest
        String tool = command.getIndirectObject(); // π.χ. key

        // 1. Έλεγχος αν ο χρήστης έγραψε τι θέλει να ξεκλειδώσει
        if (target == null || target.isEmpty()) {
            return "What are you trying to unlock?";
        }

        // 2. Έλεγχος αν έγραψε ΜΕ ΤΙ θέλει να το ξεκλειδώσει (π.χ. unlock chest WITH KEY)
        if (tool == null || tool.isEmpty()) {
            return "What do you want to unlock the " + target + " with? (e.g., unlock " + target + " with key)";
        }

        Room currentRoom = context.getCurrentRoom();

        // 3. Υπάρχει τέτοιο κλειδωμένο αντικείμενο στο δωμάτιο;
        if (!currentRoom.isLocked(target)) {
            return "There is no locked " + target + " here.";
        }

        // 4. Έχει ο παίκτης το εργαλείο/κλειδί στο Inventory του;
        if (!context.getPlayer().hasItem(tool)) {
            return "You don't have a " + tool + " in your bag!";
        }

        // 5. Είναι το ΣΩΣΤΟ κλειδί για αυτή την κλειδαριά;
        String requiredKey = currentRoom.getRequiredKey(target);
        if (requiredKey.equalsIgnoreCase(tool)) {
            currentRoom.unlockObject(target);
            return "You hear a satisfying *CLICK*. The " + target + " is now unlocked!";
        } else {
            return "You try to shove the " + tool + " into the " + target + ", but it doesn't fit.";
        }
    }
}
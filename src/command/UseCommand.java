package command;

import engine.GameContext;
import engine.Room;
import engine.CarryableItem;
import parser.ParsedCommand;

public class UseCommand implements Command {
    private GameContext context;

    public UseCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        String itemToUse = command.getDirectObject(); // π.χ. key
        String target = command.getIndirectObject(); // π.χ. chest

        if (itemToUse == null || itemToUse.isEmpty()) {
            return "What do you want to use?";
        }

        // 1. Έχει ο παίκτης αυτό που θέλει να χρησιμοποιήσει;
        if (!context.getPlayer().hasItem(itemToUse)) {
            return "You don't have a " + itemToUse + " in your bag!";
        }

        // 2. Προσπαθεί να το χρησιμοποιήσει ΠΑΝΩ ΣΕ ΚΑΤΙ; (π.χ. use key on chest)
        if (target != null && !target.isEmpty()) {
            Room currentRoom = context.getCurrentRoom();

            // Είναι το target κλειδωμένο;
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

        // 3. Αν το χρησιμοποιεί σκέτο (π.χ. use machete)
        // Παίρνουμε το αντικείμενο από το inventory και διαβάζουμε το useMessage του!
        CarryableItem itemInBag = context.getPlayer().findInInventory(itemToUse);
        return itemInBag.getUseMessage();
    }
}
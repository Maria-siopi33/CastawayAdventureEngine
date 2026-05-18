package command;

import engine.Direction;
import engine.GameContext;
import engine.Room;
import engine.NPC;
import parser.ParsedCommand;

public class GoCommand implements Command {
    private GameContext context;

    public GoCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public String execute(ParsedCommand command) {
        String directionStr = command.getDirectObject();

        if (directionStr == null || directionStr.isEmpty()) {
            return "Which direction do you want to go? (e.g., go north)";
        }

        directionStr = directionStr.toUpperCase();
        Direction direction;

        try {
            direction = Direction.valueOf(directionStr);
        } catch (IllegalArgumentException e) {
            return "That is not a valid direction (North, South, East, West).";
        }

        Room currentRoom = context.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            return "You can't go " + directionStr.toLowerCase() + " from here. There is no exit.";
        }

        // --- NPC BLOCK CHECK ---
        // Ελέγχει αν υπάρχει NPC που μπλοκάρει τη συγκεκριμένη κατεύθυνση (π.χ. North)
        if (direction == Direction.NORTH) {
            for (NPC npc : currentRoom.getNPCs()) {
                if (npc.getCurrentState() == NPC.State.BLOCK) {
                    return npc.getName() + " blocks your path and says: \"" + npc.getDialogue() + "\"";
                }
            }
        }

        // Μετακίνηση του παίκτη στο νέο δωμάτιο
        context.getPlayer().setCurrentRoom(nextRoom);

        // ΔΙΟΡΘΩΣΗ: Περνάμε 4 ορίσματα (action, directObject, preposition, indirectObject)
        //return new LookCommand(context).execute(new ParsedCommand("LOOK", null, null, null));
        return new LookCommand(context).execute(new ParsedCommand("LOOK", "", "", ""));
    }
}
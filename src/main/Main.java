package main;

import command.*; // Εισαγωγή όλων των εντολών
import engine.*;
import parser.LexicalAnalyzer;
import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Φόρτωση Κόσμου
        WorldBuilder builder = new WorldBuilder();
        Map<String, Room> world = builder.buildWorld("resources/world.json");

        if (world == null || world.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: The world was not loaded.");
            return;
        }

        // ΔΗΜΙΟΥΡΓΙΑ PLAYER & CONTEXT
        // Ξεκινάμε π.χ. από το "Beach"
        Player player = new Player(world.get("Beach"));
        GameContext context = new GameContext(player);

        // ΦΟΡΤΩΣΗ ΓΡΑΜΜΑΤΙΚΗΣ (PARSER)
        LexicalAnalyzer parser;
        try {
            parser = new LexicalAnalyzer("resources/grammar.json");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Grammar: " + e.getMessage());
            return;
        }

        // DEPENDENCY INJECTION (Το κούμπωμα)
        // Φτιάχνουμε το "λεξικό" των εντολών που θα χρησιμοποιεί ο Handler
        Map<String, Command> availableCommands = new HashMap<>();

        availableCommands.put("GO", new GoCommand(context));
        availableCommands.put("LOOK", new LookCommand(context));
        availableCommands.put("TAKE", new TakeCommand(context));
        availableCommands.put("DROP", new DropCommand(context));
        availableCommands.put("USE", new UseCommand(context));
        availableCommands.put("SWIM", new SwimCommand(context));
        availableCommands.put("UNLOCK", new UnlockCommand(context));
        availableCommands.put("INVENTORY", new InventoryCommand(context));
        availableCommands.put("STATUS", new StatusCommand(context));
        availableCommands.put("TALK", new TalkCommand(context));

        // ΔΗΜΙΟΥΡΓΙΑ COMMAND HANDLER
        // Του δίνουμε μόνο τις εντολές. Δεν χρειάζεται πλέον το 'world'!
        CommandHandler commandHandler = new CommandHandler(availableCommands);

        // ΕΚΚΙΝΗΣΗ GUI & CONTROLLER
        // Η μεταβλητή περνιέται πλέον σωστά ως 'parser'
        GameController gameController = new GameController(commandHandler, parser, context);
        Display display = new Display(gameController);
    }
}
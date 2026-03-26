package main; // Βεβαιώσου ότι το package name είναι το σωστό για το δικό σου project

import command.CommandHandler;
import engine.GameContext;
import engine.Room;
import engine.WorldBuilder;
import parser.LexicalAnalyzer;
import parser.ParsedCommand; // Προστέθηκε αυτό

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        WorldBuilder builder = new WorldBuilder();
        Map<String, Room> world = builder.buildWorld("resources/world.json");

        if (world == null || world.isEmpty()) {
            System.out.println("Σφάλμα: Ο κόσμος δεν φορτώθηκε. Ελέγξτε το path του JSON.");
            return;
        }

        GameContext context = new GameContext(world.get("Beach"));

        LexicalAnalyzer parser = null;
        try {
            // Χρησιμοποιούμε το path που βλέπω στο screenshot σου
            parser = new LexicalAnalyzer("resources/grammar.json");
            System.out.println("Ο Parser φορτώθηκε επιτυχώς!");
        } catch (Exception e) {
            System.out.println("Σφάλμα Grammar: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        CommandHandler commandHandler = new CommandHandler(context, world);

        System.out.println("Καλωσήρθατε στον Ναυαγό!");
        if (context.getCurrentRoom() != null) {
            System.out.println(context.getCurrentRoom().getDescription());
        }
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) break;

            // ΑΛΛΑΓΗ ΕΔΩ: Χρησιμοποιούμε ParsedCommand αντί για List<String>
            ParsedCommand pc = parser.analyze(input);

            // ΑΛΛΑΓΗ ΕΔΩ: Στέλνουμε το pc στον handler
            commandHandler.handle(pc);
        }

        System.out.println("Αντίο!");
    }
}
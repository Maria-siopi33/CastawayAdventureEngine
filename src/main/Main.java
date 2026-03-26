package main;

import engine.GameContext;
import engine.Room;
import engine.WorldBuilder;
import parser.LexicalAnalyzer;
import parser.ParsedCommand; // Προσθήκη του δικού σου μοντέλου
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            // 1. Φόρτωση Κόσμου (Δουλειά Αρχιτέκτονα)
            WorldBuilder builder = new WorldBuilder();
            Map<String, Room> world = builder.buildWorld("resources/world.json");

            if (world == null || world.isEmpty()) {
                System.out.println("Σφάλμα: Ο κόσμος δεν φορτώθηκε.");
                return;
            }

            // 2. Αρχικοποίηση Δικού σου Parser (Δουλειά Γλωσσολόγου)
            // Πλέον περνάμε το grammar.json για να μην είναι hardcoded οι λέξεις
            LexicalAnalyzer parser = new LexicalAnalyzer("resources/grammar.json");

            // 3. Game State
            GameContext context = new GameContext(world.get("Beach"));
            Scanner scanner = new Scanner(System.in);

            System.out.println("Καλωσήρθατε στον Ναυαγό!");
            if (context.getCurrentRoom() != null) {
                System.out.println(context.getCurrentRoom().getDescription());
            }

            // 4. Game Loop
            while (true) {
                System.out.print("\n> ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("quit")) break;

                // ΕΔΩ ΕΙΝΑΙ Η ΑΛΛΑΓΗ ΣΟΥ:
                // Αντί για List<String>, παίρνουμε το δομημένο ParsedCommand
                ParsedCommand cmd = parser.analyze(input);

                // Εκτύπωση για έλεγχο (θα αντικατασταθεί από τον CommandManager αργότερα)
                System.out.println("--- Parser Analysis ---");
                System.out.println("Action: " + cmd.getAction());
                System.out.println("Direct Object: " + cmd.getDirectObject());
                if (!cmd.getPreposition().isEmpty()) {
                    System.out.println("Prep: " + cmd.getPreposition() + " | Indirect: " + cmd.getIndirectObject());
                }
            }
            /*  Αντικατάσταση του List<String> tokens: Στην προηγούμενη έκδοση,
             ο parser απλώς έσπαγε τις λέξεις. Τώρα, χρησιμοποιείς την
             cmd.getAction(), η οποία επιστρέφει το "καθαρό" ρήμα
             (π.χ. "TAKE" αντί για "grab"), ικανοποιώντας την απαίτηση για
             συνώνυμα  */

            System.out.println("Αντίο!");

        } catch (Exception e) {
            System.err.println("Κρίσιμο σφάλμα κατά την εκκίνηση: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
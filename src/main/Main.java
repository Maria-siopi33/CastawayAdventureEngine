package main;

import parser.LexicalAnalyzer;
import java.util.List;
import java.util.Scanner;
//import parser.ParsedCommand;

// ΠΡΟΣΟΧΗ: Το όνομα της κλάσης πρέπει να ξεκινά με ΚΕΦΑΛΑΙΟ 'M'
public class Main {

    public static void main(String[] args) {

        try {
            // Ορίζουμε τη διαδρομή του αρχείου JSON
            String jsonPath = "resources/grammar.json";

            // Αρχικοποίηση του Parser με το JSON
            LexicalAnalyzer parser = new LexicalAnalyzer(jsonPath);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Καλωσήρθατε στον Ναυαγό!");
            System.out.println("--- Parser Test Mode ---");
            System.out.println("Δοκίμασε εντολές όπως: 'grab the key' ή 'unlock door with key'");

            while (true) {
                System.out.print("\n> ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("quit")) break;

                // Εκτέλεση της ανάλυσης
                ParsedCommand cmd = parser.analyze(input);

                // Εκτύπωση αποτελέσματος για έλεγχο
                System.out.println("ΑΠΟΤΕΛΕΣΜΑ PARSER:");
                System.out.println("Action: " + cmd.getAction());
                System.out.println("Direct Object: " + cmd.getDirectObject());
                System.out.println("Preposition: " + cmd.getPreposition());
                System.out.println("Indirect Object: " + cmd.getIndirectObject());
            }

        } catch (Exception e) {
            System.err.println("Σφάλμα κατά τη φόρτωση του Parser: " + e.getMessage());
            e.printStackTrace();
        }

        //LexicalAnalyzer parser = new LexicalAnalyzer();
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Καλωσήρθατε στον Ναυαγό! Πληκτρολογήστε μια εντολή:");
        //String input = scanner.nextLine();
        //List<String> tokens = parser.analyze(input);
        //System.out.println("Ο Parser αναγνώρισε: " + tokens);
    }
}
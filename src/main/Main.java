import engine.GameContext;
import engine.Room;
import engine.WorldBuilder;
import parser.LexicalAnalyzer;
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

        // 2. Ξεκινάμε το παιχνίδι
        // Προσοχή: Το "Beach" πρέπει να υπάρχει ως ID μέσα στο world.json σου!
        GameContext context = new GameContext(world.get("Beach"));

        LexicalAnalyzer parser = new LexicalAnalyzer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Καλωσήρθατε στον Ναυαγό!");
        if (context.getCurrentRoom() != null) {
            System.out.println(context.getCurrentRoom().getDescription());
        }

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) break;

            List<String> tokens = parser.analyze(input);
            System.out.println("Ο Parser αναγνώρισε: " + tokens);
        }

        System.out.println("Αντίο!");
    }
}
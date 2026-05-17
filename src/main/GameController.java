package main;

import command.CommandHandler;
import parser.LexicalAnalyzer;
import parser.ParsedCommand;
import engine.GameContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private CommandHandler commandHandler;
    private LexicalAnalyzer parser;
    private GameContext context;

    private List<String> commandHistory;
    private boolean isReplaying = false;

    public GameController(CommandHandler handler, LexicalAnalyzer parser, GameContext context) {
        this.commandHandler = handler;
        this.parser = parser;
        this.context = context;
        this.commandHistory = new ArrayList<>();
    }

    // Η κεντρική μέθοδος που δέχεται το κείμενο του χρήστη και επιστρέφει την απάντηση
    public String processInput(String input) {
        if (input.equalsIgnoreCase("SAVE")) {
            return saveGame(false);
        }

        if (input.equalsIgnoreCase("LOAD")) {
            if (!commandHistory.isEmpty()) {
                return "[SYSTEM WARNING]: You can only load a game at the very beginning! Please restart the app to load.";
            }
            return loadGame();
        }

        if (!isReplaying) {
            commandHistory.add(input);
        }

        ParsedCommand pc = parser.analyze(input);
        String response = commandHandler.handle(pc);

        StringBuilder output = new StringBuilder();

        if (!isReplaying) {
            output.append(response);
            saveGame(true); // Autosave στο παρασκήνιο
        }

        // Έλεγχος συνθήκης νίκης
        if (isGameWon() && !isReplaying) {
            output.append("\n\n***************************************************\n");
            output.append("   CONGRATULATIONS! YOU HAVE WON THE GAME!\n");
            output.append("***************************************************");
        }

        return output.toString();
    }

    public boolean isGameWon() {
        return context.getCurrentRoom() != null && context.getCurrentRoom().isWinRoom();
    }

    private String saveGame(boolean isAutosave) {
        try (PrintWriter out = new PrintWriter(new FileWriter("savegame.txt"))) {
            for (String cmd : commandHistory) {
                out.println(cmd);
            }
            if (!isAutosave) {
                return "[SYSTEM]: Game saved successfully. (" + commandHistory.size() + " actions recorded)";
            }
        } catch (IOException e) {
            if (!isAutosave) {
                return "[SYSTEM ERROR]: Could not save the game.";
            }
        }
        return "";
    }

    private String loadGame() {
        File file = new File("savegame.txt");
        if (!file.exists()) {
            return "[SYSTEM ERROR]: No savegame found.";
        }

        try {
            List<String> savedCommands = Files.readAllLines(Paths.get("savegame.txt"));
            isReplaying = true;

            for (String cmd : savedCommands) {
                processInput(cmd); // Εκτέλεση της ιστορικής εντολής
            }

            isReplaying = false;

            // Αναγκάζουμε ένα "look" στο τέλος για να δει ο παίκτης πού είναι
            String finalState = processInput("look");

            return "[SYSTEM]: Loading game state... Replaying " + savedCommands.size() + " actions.\n" +
                    "[SYSTEM]: Load complete! You are now back where you left off.\n\n" + finalState;

        } catch (IOException e) {
            isReplaying = false;
            return "[SYSTEM ERROR]: Could not load the game.";
        }
    }

    // Επιστρέφει την αρχική περιγραφή του 1ου δωματίου για το καλωσόρισμα
    public String getIntroText() {
        if (context.getCurrentRoom() != null) {
            String roomName = context.getCurrentRoom().getName();
            String description = context.getCurrentRoom().getDescription();
            String cleanDescription = description.replace(roomName + ":", "").trim();

            return "You are at the " + roomName.toLowerCase() + ".\n" + cleanDescription + "\n--------------------------------------------------";
        }
        return "";
    }
}
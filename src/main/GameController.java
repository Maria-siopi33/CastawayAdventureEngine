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

    // --- ΤΟ ΕΞΥΠΝΟ ΦΙΛΤΡΟ ΔΥΣΚΟΛΙΑΣ ---
    private String applyDifficultyFilter(String text) {
        if (text == null) return "";
        if (context.isEasyMode()) {
            // Easy Mode: Απλά κρύβουμε τις ετικέτες, και το κείμενο του hint μένει
            return text.replace("<hint>", "").replace("</hint>", "");
        } else {
            // Normal Mode: Σβήνουμε τις ετικέτες ΚΑΙ ό,τι υπάρχει ανάμεσά τους!
            return text.replaceAll("(?s)<hint>.*?</hint>", "").trim();
        }
    }

    public String processInput(String input) {
        if (input.equalsIgnoreCase("SAVE")) {
            return applyDifficultyFilter(saveGame(false));
        }

        if (input.equalsIgnoreCase("LOAD")) {
            if (!commandHistory.isEmpty()) {
                return applyDifficultyFilter("[SYSTEM WARNING]: You can only load a game at the very beginning! Please restart the app to load.");
            }
            return applyDifficultyFilter(loadGame());
        }

        if (!isReplaying) {
            commandHistory.add(input);
        }

        ParsedCommand pc = parser.analyze(input);
        String response = commandHandler.handle(pc);

        StringBuilder output = new StringBuilder();

        if (!isReplaying) {
            output.append(response);
            saveGame(true);
        }

        if (isGameWon() && !isReplaying) {
            output.append("\n\n***************************************************\n");
            output.append("   CONGRATULATIONS! YOU HAVE WON THE GAME!\n");
            output.append("***************************************************");
        }

        // Φιλτράρουμε το κείμενο πριν το δώσουμε στην οθόνη!
        return applyDifficultyFilter(output.toString());
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
                processInput(cmd);
            }

            isReplaying = false;

            String finalState = processInput("look");

            return "[SYSTEM]: Loading game state... Replaying " + savedCommands.size() + " actions.\n" +
                    "[SYSTEM]: Load complete! You are now back where you left off.\n\n" + finalState;

        } catch (IOException e) {
            isReplaying = false;
            return "[SYSTEM ERROR]: Could not load the game.";
        }
    }

    public String getIntroText() {
        if (context.getCurrentRoom() != null) {
            String roomName = context.getCurrentRoom().getName();
            String description = context.getCurrentRoom().getDescription();
            String cleanDescription = description.replace(roomName + ":", "").trim();

            String intro = "You are at the " + roomName.toLowerCase() + ".\n" + cleanDescription + "\n--------------------------------------------------";
            return applyDifficultyFilter(intro); // Φιλτράρεται και το καλωσόρισμα
        }
        return "";
    }
}
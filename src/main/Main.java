package main;

import command.CommandHandler;
import engine.GameContext;
import engine.Room;
import engine.WorldBuilder;
import parser.LexicalAnalyzer;
import javax.swing.JOptionPane;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        WorldBuilder builder = new WorldBuilder();
        Map<String, Room> world = builder.buildWorld("resources/world.json");

        if (world == null || world.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Σφάλμα: Ο κόσμος δεν φορτώθηκε.");
            return;
        }

        GameContext context = new GameContext(world.get("Beach"));

        LexicalAnalyzer parser;
        try {
            parser = new LexicalAnalyzer("resources/grammar.json");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Σφάλμα Grammar: " + e.getMessage());
            return;
        }

        CommandHandler commandHandler = new CommandHandler(context, world);

        new Display(commandHandler, parser, context);
    }
}
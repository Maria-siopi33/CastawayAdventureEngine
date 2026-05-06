package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import command.CommandHandler;
import parser.LexicalAnalyzer;
import parser.ParsedCommand;
import engine.GameContext;

public class Display {
    private JFrame window;
    private JTextArea textArea;
    private JTextField inputField;
    private CommandHandler commandHandler;
    private LexicalAnalyzer parser;

    public Display(CommandHandler handler, LexicalAnalyzer parser, GameContext context) {
        this.commandHandler = handler;
        this.parser = parser;
        createUI();

        writeText("Welcome to our game!\n");

        if (context.getCurrentRoom() != null) {
            String roomName = context.getCurrentRoom().getName();
            String description = context.getCurrentRoom().getDescription();

            String cleanDescription = description.replace(roomName + ":", "").trim();

            writeText("You are at the " + roomName.toLowerCase() + ".");
            writeText(cleanDescription);
            writeText("------------------------------------------");
        }
    }


    private void createUI() {
        window = new JFrame("Command Prompt");
        window.setSize(900, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; //Κάθε στοιχείο πιάνει μια γραμμή
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; //Οριζόντια επέκταση
        gbc.anchor = GridBagConstraints.NORTHWEST; // Όλα ξεκινούν από πάνω αριστερά

        textArea = new JTextArea();
        textArea.setEditable(false);  //ΔΕΝ μπορεί να επεξ. αυτά που εχει γράψει ήδη.
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.BLACK);

        JLabel promptLabel = new JLabel("> ");
        promptLabel.setForeground(Color.WHITE);
        promptLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
        inputPanel.add(promptLabel, BorderLayout.WEST);

        inputField = new JTextField();
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 16));
        inputField.setBorder(null);
        inputPanel.add(inputField, BorderLayout.CENTER);

        mainPanel.add(textArea, gbc);
        mainPanel.add(inputPanel, gbc);


        gbc.weighty = 1;
        mainPanel.add(new JPanel() {{ setBackground(Color.BLACK); }}, gbc);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.BLACK); // Μαύρο background παντού

        window.add(scrollPane);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();//Βγαζει τα κενά
                if (!input.isEmpty()) {
                    handleInput(input);
                }
                inputField.setText(""); //αρχικοποιει για την επομενη εισαγωγη
            }
        });

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        inputField.requestFocusInWindow();
    }

    public void writeText(String text) {
        textArea.append(text + "\n"); //Προσαρτηση του νεου κειμενου
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void handleInput(String input) {
        writeText("> " + input);
        ParsedCommand pc = parser.analyze(input); //Σπάει την πρόταση
        String response = commandHandler.handle(pc); //Αλλαζει το state του ggame
        writeText(response + "\n");
    }
}
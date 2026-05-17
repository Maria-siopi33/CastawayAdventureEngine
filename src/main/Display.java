package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display {
    private JFrame window;
    private JTextArea textArea;
    private JTextField inputField;
    private GameController controller; // Η ΜΟΝΑΔΙΚΗ ΕΞΑΡΤΗΣΗ

    public Display(GameController controller) {
        this.controller = controller;
        createUI();
        printWelcomeMessage();
    }

    private void printWelcomeMessage() {
        writeText("==================================================");
        writeText("         Welcome to the Text Adventure Engine!    ");
        writeText("==================================================");
        writeText("\nΟΔΗΓΙΕΣ ΠΑΙΧΝΙΔΙΟΥ:");
        writeText("Πληκτρολογήστε εντολές στα αγγλικά για να εξερευνήσετε τον κόσμο.");

        writeText("\n--- ΒΑΣΙΚΕΣ ΚΙΝΗΣΕΙΣ & ΕΞΕΡΕΥΝΗΣΗ ---");
        writeText("- GO [direction]          : Μετακίνηση (north, south, east, west)");
        writeText("- LOOK                    : Εξέταση όλου του τρέχοντος δωματίου");
        writeText("- LOOK AT [item]          : Προσεκτική εξέταση ενός αντικειμένου");
        writeText("- SWIM                    : Κολύμβηση (αν υπάρχει νερό)");
        writeText("--- ΔΙΑΧΕΙΡΙΣΗ ΑΝΤΙΚΕΙΜΕΝΩΝ & ΠΑΙΚΤΗ ---");
        writeText("- TAKE [item]             : Συλλογή αντικειμένου από το έδαφος");
        writeText("- DROP [item]             : Απόρριψη αντικειμένου από την τσάντα");
        writeText("- INVENTORY               : Προβολή των αντικειμένων που κρατάτε");
        writeText("- STATUS                  : Προβολή της γενικής σας κατάστασης");
        writeText("--- ΑΛΛΗΛΕΠΙΔΡΑΣΗ ---");
        writeText("- USE [item]              : Χρήση αντικειμένου μόνο του");
        writeText("- USE [item] on [target]  : Χρήση αντικειμένου πάνω σε κάποιο στόχο");
        writeText("- UNLOCK [obj] with [key] : Ξεκλείδωμα κλειδωμένου αντικειμένου");
        writeText("--- ΣΥΣΤΗΜΑ ---");
        writeText("- SAVE                    : Αποθήκευση της προόδου σας στο παιχνίδι");
        writeText("- LOAD                    : Φόρτωση προηγούμενου παιχνιδιού (ΜΟΝΟ στην αρχή)");
        writeText("=================================================================\n");
        // Ζητάει το αρχικό κείμενο από τον Controller
        writeText(controller.getIntroText());
    }

    private void createUI() {
        window = new JFrame("Command Prompt");
        window.setSize(800, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        textArea = new JTextArea();
        textArea.setEditable(false);
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
        scrollPane.getViewport().setBackground(Color.BLACK);

        window.add(scrollPane);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                if (!input.isEmpty()) {
                    handleInput(input);
                }
                inputField.setText("");
            }
        });

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        inputField.requestFocusInWindow();
    }

    public void writeText(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void handleInput(String input) {
        writeText("> " + input);

        // ΟΛΗ Η ΛΟΓΙΚΗ ΕΙΝΑΙ ΠΛΕΟΝ ΕΝΑ CALL ΣΤΟΝ CONTROLLER
        String response = controller.processInput(input);

        if (!response.isEmpty()) {
            writeText(response);
        }

        // Αν ο Controller πει ότι νικήσαμε, απλά κλειδώνει το input
        if (controller.isGameWon()) {
            inputField.setEnabled(false);
        }
    }
}
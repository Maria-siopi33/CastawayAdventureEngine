package parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class LexicalAnalyzer {
    private Map<String, String> synonymMap = new HashMap<>();
    private List<String> stopWords;
    private List<String> prepositions;

    public LexicalAnalyzer(String jsonPath) throws Exception {

        try {
            ObjectMapper mapper = new ObjectMapper();
            // Το JSON μετατρέπεται αυτόματα σε αντικείμενο Java
            GrammarConfig config = mapper.readValue(new File(jsonPath), GrammarConfig.class);


            System.out.println("Grammar loaded succesfully!");
        } catch (Exception e) {
            System.err.println("Error reading JSON: " + e.getMessage());
        }

        loadGrammar(jsonPath);
    }

    private void loadGrammar(String path) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        GrammarConfig config = mapper.readValue(new File(path), GrammarConfig.class);

        this.stopWords = config.stopWords;
        this.prepositions = config.prepositions;

        // Μετατροπή των συνωνύμων σε Map για γρήγορη αναζήτηση
        for (GrammarConfig.CommandMapping mapping : config.commands) {
            synonymMap.put(mapping.action.toLowerCase(), mapping.action);
            for (String syn : mapping.synonyms) {
                synonymMap.put(syn.toLowerCase(), mapping.action);
            }
        }
    }

    public ParsedCommand analyze(String input) {
        // 1. Καθαρισμός και Tokenization
        List<String> tokens = new ArrayList<>(Arrays.asList(input.toLowerCase().trim().split("\\s+")));

        // 2. Αφαίρεση Stop Words [cite: 54, 57]
        tokens.removeIf(word -> stopWords.contains(word));

        if (tokens.isEmpty()) return new ParsedCommand("UNKNOWN", "", "", "");

        // 3. Εύρεση Action (Ρήμα) μέσω Synonym Mapping [cite: 55]
        String action = synonymMap.getOrDefault(tokens.get(0), "UNKNOWN");
        tokens.remove(0);

        String directObj = "";
        String prep = "";
        String indirectObj = "";

        // 4. Ανάλυση για εντολές πολλαπλών αντικειμένων [cite: 56, 57]
        for (int i = 0; i < tokens.size(); i++) {
            if (prepositions.contains(tokens.get(i))) {
                prep = tokens.get(i);
                // Ό,τι είναι πριν την πρόθεση είναι το Direct Object
                directObj = String.join(" ", tokens.subList(0, i));
                // Ό,τι είναι μετά είναι το Indirect Object
                if (i + 1 < tokens.size()) {
                    indirectObj = String.join(" ", tokens.subList(i + 1, tokens.size()));
                }
                break;
            }
        }

        // Αν δεν βρέθηκε πρόθεση, όλο το υπόλοιπο είναι το Direct Object
        if (prep.isEmpty() && !tokens.isEmpty()) {
            directObj = String.join(" ", tokens);
        }

        return new ParsedCommand(action, directObj, prep, indirectObj);
    }
}
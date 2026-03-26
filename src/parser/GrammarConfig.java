package parser;

import java.util.List;

public class GrammarConfig {
    // Αυτά τα ονόματα πρέπει να είναι ΙΔΙΑ με τα κλειδιά στο JSON σου
    public List<CommandMapping> commands;
    public List<String> prepositions;
    public List<String> stopWords;

    // Εσωτερική κλάση για να διαβάζει το action και τα synonyms
    public static class CommandMapping {
        public String action;
        public List<String> synonyms;
    }
}
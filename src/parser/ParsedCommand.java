package parser;

public class ParsedCommand {
    private String action;          // Το βασικό ρήμα (π.χ. TAKE)
    private String directObject;    // Το πρώτο αντικείμενο (π.χ. key)
    private String preposition;     // Η πρόθεση (π.χ. with)
    private String indirectObject;  // Το δεύτερο αντικείμενο (π.χ. door)

    public ParsedCommand(String action, String directObject, String preposition, String indirectObject) {
        this.action = action;
        this.directObject = directObject;
        this.preposition = preposition;
        this.indirectObject = indirectObject;
    }

    // Getters για να μπορεί ο CommandManager να διαβάσει την εντολή
    public String getAction() { return action; }
    public String getDirectObject() { return directObject; }
    public String getPreposition() { return preposition; }
    public String getIndirectObject() { return indirectObject; }

    @Override
    public String toString() {
        return "Action: " + action + " | Obj1: " + directObject + " | Prep: " + preposition + " | Obj2: " + indirectObject;
    }
}

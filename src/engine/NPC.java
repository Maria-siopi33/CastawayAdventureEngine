package engine;

import java.util.HashMap;
import java.util.Map;

public class NPC {
    // Enum για τις καταστάσεις (State Machine)
    public enum State {
        IGNORE, // Ο NPC είναι εκεί αλλά δεν εμποδίζει
        BLOCK,  // Ο NPC εμποδίζει την έξοδο (North)
        TALK    // Ο NPC έχει ξεκλειδωθεί και σε αφήνει
    }

    private String id;
    private String name;
    private String description;
    private State currentState;

    // Αποθηκεύει τους διαλόγους: π.χ. State.BLOCK -> "Δεν περνάς!"
    private Map<State, String> dialogues = new HashMap<>();

    public NPC(String id, String name, String description, State initialState) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentState = initialState;
    }

    // Προσθήκη διαλόγου για συγκεκριμένο State
    public void addDialogue(State state, String text) {
        dialogues.put(state, text);
    }

    // Επιστρέφει το σωστό μήνυμα ανάλογα με το τρέχον State
    public String getDialogue() {
        return dialogues.get(currentState);
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
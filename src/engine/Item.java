package engine;

public abstract class Item {
    // protected σημαίνει ότι οι "θυγατρικές" κλάσεις θα έχουν πρόσβαση σε αυτά
    protected String name;
    protected String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Κάθε υποκλάση θα ΠΡΕΠΕΙ να απαντάει
    // αν μπορεί να συλλεχθεί ή όχι, με τον δικό της τρόπο.
    public abstract boolean canBePickedUp();
}
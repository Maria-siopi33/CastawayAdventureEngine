package engine;

public class StaticItem extends Item {

    public StaticItem(String name, String description) {
        super(name, description); // Στέλνει τα δεδομένα στη μητρική κλάση
    }

    @Override
    public boolean canBePickedUp() {
        return false; // Δεν μπορείς να το πάρεις!
    }
}
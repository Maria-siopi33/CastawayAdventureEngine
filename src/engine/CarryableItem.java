package engine;

// Πρέπει να κάνει extends την κλάση Item (ή implements αν το Item είναι interface)
public class CarryableItem extends Item {

    public CarryableItem(String name, String description) {
        super(name, description); // Καλεί τον constructor της Item
    }
}
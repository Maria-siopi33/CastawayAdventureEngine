package engine;

public class CarryableItem extends Item {

    public CarryableItem(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean canBePickedUp() {
        return true; // Μπορεί να μπει στο Inventory του παίκτη!
    }
}
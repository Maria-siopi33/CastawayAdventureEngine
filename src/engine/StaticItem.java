package engine;

public class StaticItem extends Item {
    public StaticItem(String name, String description) {
        super(name, description);
    }
    @Override
    public boolean canBePickedUp() {
        return false;
    }
}
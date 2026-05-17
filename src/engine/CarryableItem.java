package engine;

public class CarryableItem extends Item {
    // ΠΡΟΣΘΗΚΗ: Το μήνυμα που τυπώνεται όταν κάνεις "use [item]"
    private String useMessage;

    // Αλλάζουμε λίγο τον constructor για να δέχεται το μήνυμα
    public CarryableItem(String name, String description, String useMessage) {
        super(name, description);
        this.useMessage = useMessage;
    }

    @Override
    public boolean canBePickedUp() {
        return true;
    }

    // ΠΡΟΣΘΗΚΗ: Ο Getter για το μήνυμα
    public String getUseMessage() {
        return useMessage;
    }
}
package dungeoncrawl.logic.actors.items.looting;

import dungeoncrawl.logic.Cell;

public class Key extends Item implements PickupableItem {

    public Key(Cell cell) {
        super(cell, "Key of Wisdom");
    }

    @Override
    public String getTileName() {
        return "key";
    }
}

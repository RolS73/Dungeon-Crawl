package dungeoncrawl.logic.actors.items.looting;

import dungeoncrawl.logic.Cell;

public class Life extends Item implements PickupableItem {

    private String tileName = "life";
    private int lifeRestoreAmount;

    public Life(Cell cell, int lifeRestoreAmount) {
        super(cell, "Life");
        this.lifeRestoreAmount = lifeRestoreAmount;
    }

    public Life(String nameInput, int lifeRestoreAmount) {
        super(nameInput);
        this.lifeRestoreAmount = lifeRestoreAmount;
    }

    @Override
    public int getHealth() {
        return lifeRestoreAmount;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }
}


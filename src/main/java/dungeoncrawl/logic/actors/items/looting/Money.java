package dungeoncrawl.logic.actors.items.looting;

import dungeoncrawl.logic.Cell;

public class Money extends Item implements PickupableItem {

    private String tileName = "Money";
    private int moneyAmount;

    public Money(Cell cell, int moneyAmount) {
        super(cell, "Money");
        this.moneyAmount = moneyAmount;
    }

    public Money(String nameInput, int moneyAmount) {
        super(nameInput);
        this.moneyAmount = moneyAmount;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

}

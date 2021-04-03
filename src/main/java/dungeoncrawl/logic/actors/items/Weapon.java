package dungeoncrawl.logic.actors.items;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.actors.items.looting.Item;
import dungeoncrawl.logic.actors.items.looting.PickupableItem;

public class Weapon extends Item implements PickupableItem {

    private String tileName = "staff";

    private int attackpowerIncrease;

    public Weapon(Cell cell, String name, int attackpowerIncrease) {
        super(cell, name);
        this.attackpowerIncrease = attackpowerIncrease;
    }

    public Weapon(String name, int attackpowerIncrease) {
        super(name);
        this.attackpowerIncrease = attackpowerIncrease;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    public int getAttackpowerIncrease() {
        return attackpowerIncrease;
    }

    public void setAttackpowerIncrease(int attackpowerIncrease) {
        this.attackpowerIncrease = attackpowerIncrease;
    }
}

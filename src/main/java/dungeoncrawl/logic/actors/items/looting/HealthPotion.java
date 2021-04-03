package dungeoncrawl.logic.actors.items.looting;

import dungeoncrawl.logic.Cell;

import java.util.Objects;

public class HealthPotion extends Item implements PickupableItem {

    public static final int LIMIT = 3;

    private String tileName = "healthPoti";
    public HealthPotion(Cell cell, String name) {
        super(cell, name);
    }

    public HealthPotion(String name) {
        super(name);
    }

    @Override
    public String getTileName() {
        return super.getTileName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthPotion that = (HealthPotion) o;
        return Objects.equals(getTileName(), that.getTileName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTileName());
    }
}

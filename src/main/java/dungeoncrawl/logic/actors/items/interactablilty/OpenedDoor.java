package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.items.looting.Item;

public class OpenedDoor extends Item {

    private String anotherTileName = "openedDoor";

    public OpenedDoor(Cell cell) {
        super(cell, "Way to Doom");
        this.getCell().setCellType(CellType.FLOOR);
    }

    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String anotherTileName) {
        this.anotherTileName = anotherTileName;
    }
}

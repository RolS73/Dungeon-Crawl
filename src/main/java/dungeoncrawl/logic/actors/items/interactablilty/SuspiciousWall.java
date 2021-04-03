
package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.Item;

public class SuspiciousWall extends Item implements InteractiveObject, Switch {

    private String anotherTileName = this.getCell().getTileName();
    private String groupName;

    int[] coordinates;

    public SuspiciousWall(Cell cell) {
        super(cell, "Door to Secrets");


    }

    @Override
    public boolean isThisObjectInteractive() {
        return this.getCell().getCellType().equals(CellType.WALL); //|| this.anotherTileName.equals("empty");
    }


    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            Sounds.playSound("IllusioryWall");
            this.getCell().setCellType(CellType.FLOOR);
            this.anotherTileName = this.getCell().getTileName();
        }
    }

    @Override
    public String getTileName() {
        return this.anotherTileName;
    }

    public void setTileName(String tileName) {
        this.anotherTileName = tileName;
    }

    @Override
    public boolean isMoveOnPossibleAfterInteraction() {
        return true;
    }

    @Override
    public boolean isPlayerInteractingFromLegalDirection(Cell cell) {
        return true;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell().equals(cell);
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean isThisFromTheSameGroup(String groupName) {
        return this.groupName.equals(groupName);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}


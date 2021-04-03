package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.items.looting.Item;

public class GateOpenableByASwitch extends Item implements InteractiveObject, Switch {

    private String anotherTileName = "gateOpenableByASwitch";
    private String groupName;
    private Cell ThisCell;


    public GateOpenableByASwitch(String name) {
        super(name);
    }

    public GateOpenableByASwitch(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public void interact() {
        anotherTileName = "floor";
        this.getCell().setCellType(CellType.FLOOR);
    }

    @Override
    public String getTileName() {
        return this.anotherTileName;
    }

    @Override
    public boolean isThisObjectInteractive() {
        return false;
    }

    @Override
    public boolean isMoveOnPossibleAfterInteraction() {
        return false;
    }

    @Override
    public boolean isPlayerInteractingFromLegalDirection(Cell cell) {
        return true;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell() == cell;
    }

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


package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class SuspiciousWall extends Item implements InteractiveObject, Switch {

    private String anotherTileName = "suspiciousWall";
    private String groupName;

    int[] coordinates;

    public SuspiciousWall(Cell cell) {
        super(cell, "Door to Secrets");


    }

    @Override
    public boolean isThisObjectInteractive() {
        return this.anotherTileName.equals("suspiciousWall") || this.anotherTileName.equals("empty");
    }


    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            Sounds.playSound("IllusioryWall");
            this.anotherTileName = "floor";
            this.getCell().setCellType(CellType.FLOOR);
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


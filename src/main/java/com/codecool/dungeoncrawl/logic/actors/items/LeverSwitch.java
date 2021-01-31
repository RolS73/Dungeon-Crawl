

package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class LeverSwitch extends Item implements Switch {

    private String anotherTileName = "leverSwitchOff";
    private String groupName;

    public LeverSwitch(Cell cell) {
        super(cell, "leverSwitch");
    }

    @Override
    public void interact() {
        anotherTileName = "leverSwitchOn";
        System.out.println("switch activated");
    }

    @Override
    public String getTileName() {
        return this.anotherTileName;
    }

    @Override
    public boolean isThisObjectInteractive() {
        return this.anotherTileName.equals("leverSwitchOff");
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


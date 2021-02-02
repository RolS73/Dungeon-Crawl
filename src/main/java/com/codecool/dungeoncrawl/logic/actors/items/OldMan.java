package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class OldMan extends NonPlayerCharacter implements InteractiveObject, Switch {

    String anotherTileName = "friendlyOldMan";
    String groupName = "oldManGroup";

    public OldMan(Cell cell) {
        super(cell);
        super.setHealth(9999);
        super.setAttackPower(100);
    }



    @Override
    public String getTileName() {
        return anotherTileName;
    }

    @Override
    public void interact() {

        System.out.println("Talking");

    }

    @Override
    public boolean isThisObjectInteractive() {
        return true;
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
        return this.getCell().equals(cell);
    }

    @Override
    public String getGroupName() {
        return null;
    }

    @Override
    public boolean isThisFromTheSameGroup(String groupName) {
        return false;
    }
}

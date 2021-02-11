package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class FriendlyWhiteWizard extends NonPlayerCharacter implements InteractiveObject, Switch {

    String anotherTileName = "WhiteWizard";
    String groupName = "FriendlyWhiteWizardGroup1";

    public FriendlyWhiteWizard(Cell cell) {
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

        // Talking choice box

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
        return this.getCell().equals(cell);
    }

    @Override
    public String getGroupName() {
        return null;
    }

    @Override
    public void setGroupName(String hiddenEnemyGroup1) {

    }

    @Override
    public boolean isThisFromTheSameGroup(String groupName) {
        return false;
    }
}

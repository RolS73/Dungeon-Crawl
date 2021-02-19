package com.codecool.dungeoncrawl.logic.actors.items.interactablilty;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class TorchPuzzle extends Item implements InteractiveObject, Switch {

    private String name = "puzzleFireStandInActive";
    private static int totalTorchesActivated = 0;
    private String groupName;

    public TorchPuzzle(Cell cell) {
        super(cell, "Door to Secrets");
        this.setName("Trigger for Secrets");
    }

    @Override
    public boolean isThisObjectInteractive() {
        return true;
    }

    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            this.name = "puzzleFireStandActive";
            totalTorchesActivated++;
            System.out.println(totalTorchesActivated);
            if (isConditionForSecretMet()) {
                Main.cheatingMapGetter().getSwitchablesCollection()
                        .stream()
                        .filter(x -> x.getGroupName() != null)
                        .filter(x -> x.isThisFromTheSameGroup("hiddenRoomGroup"))
                        .forEach(InteractiveObject::interact);
            }
        }
    }

    private static boolean isConditionForSecretMet() {
        return totalTorchesActivated == 3;
    }

    @Override
    public String getTileName() {
        return this.name;
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
        return groupName;
    }

    @Override
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean isThisFromTheSameGroup(String groupName) {
        return this.groupName.equals(groupName);
    }


}

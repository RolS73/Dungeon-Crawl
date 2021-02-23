package com.codecool.dungeoncrawl.logic.actors.items.interactablilty;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class ForestPuzzle extends Item implements InteractiveObject, Switch {

    private String name = "leverSwitchOff";
    private static int totalEntitiesActivated = 0;
    private String groupName;
    private boolean isAlreadyActivated;


    public ForestPuzzle(Cell cell) {
        super(cell, "Door to Destiny");

    }

    @Override
    public boolean isThisObjectInteractive() {
        return !isAlreadyActivated;
    }

    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            this.name = "leverSwitchOn";
            totalEntitiesActivated++;
            System.out.println(totalEntitiesActivated);
            isAlreadyActivated = true;
            /*if (isConditionForSecretMet()) {
                Main.cheatingMapGetter().getSwitchablesCollection()
                        .stream()
                        .filter(x -> x.getGroupName() != null)
                        .filter(x -> x.isThisFromTheSameGroup("hiddenRoomGroup"))
                        .forEach(InteractiveObject::interact);
            }*/
        }
    }

    private static boolean isConditionForSecretMet() {
        return totalEntitiesActivated == 3;
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

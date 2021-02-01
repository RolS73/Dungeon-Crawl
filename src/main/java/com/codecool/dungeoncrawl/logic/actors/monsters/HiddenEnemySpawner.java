package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.items.InteractiveObject;
import com.codecool.dungeoncrawl.logic.actors.items.Item;
import com.codecool.dungeoncrawl.logic.actors.items.Switch;

public class HiddenEnemySpawner extends Item implements InteractiveObject, Switch {

    String groupName;
    String enemyType;
    String anotherTileName = "floor";

    public HiddenEnemySpawner(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean isThisFromTheSameGroup(String groupName) {
        return this.groupName.equals(groupName);
    }

    @Override
    public void interact() {
        Skeleton hiddenSkeleton = new Skeleton(this.getCell());
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getTileName() {
        if (enemyType != null) {
            return enemyType;
        } else {
            return anotherTileName;
        }
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
        return false;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return false;
    }

}

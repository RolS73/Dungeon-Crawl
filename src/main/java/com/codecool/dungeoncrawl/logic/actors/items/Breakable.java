package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Breakable extends Item implements InteractiveObject {
    private String tileName = "breakable";

    public Breakable(Cell cell, String name) {
        super(cell, name);
        setAttackPower(0);
    }

    public String getName() {
        return this.tileName;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            this.getCell().setItem(new LootTable().getItemFromTable());
        }
    }

    @Override
    public boolean isThisObjectInteractive() {
        return true;
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
}

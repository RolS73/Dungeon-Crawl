package com.codecool.dungeoncrawl.logic.actors.items;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class ChestCommon extends Item implements InteractiveObject {
    private String name;

    public ChestCommon(Cell cell, String name) {
        super(cell, name);
        setAttackPower(0);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getTileName() {
        return "barrel";
    }

    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            this.getCell().setItem(new LootTable("Rare").getItemFromTable());
            this.getCell().setCellType(CellType.FLOOR);
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
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell().equals(cell);
    }
}


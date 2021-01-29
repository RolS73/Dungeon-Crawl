package com.codecool.dungeoncrawl.logic.actors.items;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Chest extends Item implements InteractiveObject {
    private String tileName;

    public Chest(Cell cell, String tileName) {
        super(cell, tileName);
        setAttackPower(0);
    }

    public void setName(String name) {
        this.tileName = name;
    }

    public String getName() {
        return this.tileName;
    }

    @Override
    public String getTileName() {
        return "barrel";
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
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
    public boolean isPlayerInteractingFromLegalDirection(Cell cell) {
        return true;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell().equals(cell);
    }
}


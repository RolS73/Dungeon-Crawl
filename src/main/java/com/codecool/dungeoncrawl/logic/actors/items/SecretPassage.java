
package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class SecretPassage extends Item implements InteractiveObject {

    private String anotherTileName = "secretPassage";

    public SecretPassage(Cell cell) {
        super(cell, "Path to Secrets");
    }

    @Override
    public void interact() {
        if (anotherTileName.equals("empty")) {
            Sounds.playSound("Move5");
        }
        if (isThisObjectInteractive() && anotherTileName.equals("secretPassage")) {
            this.anotherTileName = "empty";
            this.getCell().setCellType(CellType.FLOOR);
        }
    }

    @Override
    public String getTileName() {
        return this.anotherTileName;
    }

    public void setTileName(String newName) {
        anotherTileName = newName;
    }

    @Override
    public boolean isMoveOnPossibleAfterInteraction() {
        return false;
    }

    @Override
    public boolean isThisObjectInteractive() {
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


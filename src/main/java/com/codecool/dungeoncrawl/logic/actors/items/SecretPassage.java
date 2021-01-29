
package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class SecretPassage extends Item implements InteractiveObject {

    private String name = "secretPassage";

    int[] coordinates;

    public SecretPassage(Cell cell) {

        super(cell, "Path to Secrets");
        this.coordinates = new int[]{cell.getX(), cell.getY()};
        this.setName("Path to Secrets");

    }

    @Override
    public boolean isThisObjectInteractive() {
        return true;
    }

    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            Sounds.playSound("Move5");;
        }
    }

    @Override
    public String getTileName() {
        return this.name;
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

    public int[] getCoordinates() {
        return coordinates;
    }
}


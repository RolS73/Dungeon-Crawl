
package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class SuspiciousWall extends Item implements InteractiveObject {

    private String name = "suspiciousWall";
    private boolean isThisCurrentlyInteractive = true;

    int[] coordinates;

    public SuspiciousWall(Cell cell) {

        super(cell, "Door to Secrets");
        this.coordinates = new int[]{cell.getX(), cell.getY()};
        this.setName("Door to Secrets");

        isThisCurrentlyInteractive = isThisObjectInteractive();
    }

    @Override
    public boolean isThisObjectInteractive() {
        return isThisCurrentlyInteractive;
    }



    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            this.getCell().setItem(new SecretPassage(this.getCell()));
            Sounds.playSound("IllusioryWall");
            this.getCell().setCellType(CellType.FLOOR);
            isThisCurrentlyInteractive = false;
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


package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class LockedDoor extends Item implements InteractiveObject {

    private String name = "lockedDoor";

    int[] coordinates;

        public LockedDoor(Cell cell) {

            super(cell, "Door to Doom");
            this.coordinates = new int[]{cell.getX(), cell.getY()};
            this.setName("Door to Doom");

        }

        @Override
        public boolean isThisObjectInteractive(){
            for (Item item : Main.inventory) {
                if (item.getName().equals("Key of Wisdom")) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void interact() {
            if (isThisObjectInteractive()) {
                this.getCell().setItem(new OpenedDoor(getCell()));
                this.getCell().setCellType(CellType.FLOOR);
                Main.inventory.removeIf(item -> item.getName().equals("Key of Wisdom"));
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
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell().equals(cell);
    }

    public int[] getCoordinates() {
        return coordinates;
    }
}

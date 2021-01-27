package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;

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
            return Main.Inventory.contains("Key of Wisdom");
        }

        @Override
        public void interact() {
            if (isThisObjectInteractive()) {
                this.getCell().setItem(new OpenedDoor(getCell()));
                Main.Inventory.remove("Key of Wisdom");
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

    public int[] getCoordinates() {
        return coordinates;
    }
}

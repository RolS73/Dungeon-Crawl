package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.InventoryManager;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class LockedDoor extends Item implements InteractiveObject {

    private String name = "lockedDoor";

        public LockedDoor(Cell cell) {
            super(cell, "Door to Doom");
            this.setName("Door to Doom");
        }


    @Override
        public boolean isThisObjectInteractive(){
            for (Item item : InventoryManager.inventory) {
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
                InventoryManager.inventory.removeIf(item -> item.getName().equals("Key of Wisdom"));
                Sounds.playSound("Door5b");
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
}

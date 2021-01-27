package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;

public class LockedDoor extends Item implements InteractableObject {

    private String name = "lockedDoor";


        public LockedDoor(Cell cell) {
            super(cell);
            this.setName("Door to Doom");
        }

        @Override
        public boolean isInteractable(){
            return Main.inventory.contains("Key of Wisdom");
        }

        @Override
        public void interact() {
            if (isInteractable()) {
                this.name = "openedDoor";
                Main.inventory.remove("Key of Wisdom");
            }
        }

        @Override
        public String getTileName() {
            return this.name;
        }

}

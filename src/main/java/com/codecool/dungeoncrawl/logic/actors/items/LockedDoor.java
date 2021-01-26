package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class LockedDoor extends Item {

        public LockedDoor(Cell cell) {
            super(cell);
            this.setName("Door to Doom");
        }

        @Override
        public String getTileName() {
            return "lockedDoor";
        }
}

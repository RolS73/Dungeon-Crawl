package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class OpenedDoor extends Item {

    public OpenedDoor(Cell cell) {
        super(cell, "Way to Doom");
    }

    @Override
    public String getTileName() {
        return "openedDoor";
    }
}

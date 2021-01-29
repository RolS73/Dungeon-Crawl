package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class OpenedDoor extends Item {

    private String anotherTileName = "openedDoor";

    public OpenedDoor(Cell cell) {
        super(cell, "Way to Doom");
    }

    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String anotherTileName) {
        this.anotherTileName = anotherTileName;
    }
}

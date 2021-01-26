package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Life extends Item {

    public Life(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "life";
    }
}

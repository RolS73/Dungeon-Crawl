package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item {

    public Key(Cell cell) {
        super(cell, "Key of Wisdom");
    }

    @Override
    public String getTileName() {
        return "key";
    }
}

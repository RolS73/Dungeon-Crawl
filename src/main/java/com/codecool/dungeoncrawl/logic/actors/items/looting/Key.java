package com.codecool.dungeoncrawl.logic.actors.items.looting;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item implements PickupableItem {

    public Key(Cell cell) {
        super(cell, "Key of Wisdom");
    }

    @Override
    public String getTileName() {
        return "key";
    }
}

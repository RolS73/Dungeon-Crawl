package com.codecool.dungeoncrawl.logic.actors.items.looting;

import com.codecool.dungeoncrawl.logic.Cell;

public class HealthPotion extends Item implements PickupableItem {

    public static final int LIMIT = 3;

    private String tileName = "life";
    public HealthPotion(Cell cell, String name) {
        super(cell, name);
    }

    public HealthPotion(String name) {
        super(name);
    }

    @Override
    public String getTileName() {
        return super.getTileName();
    }
}

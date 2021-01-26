package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item {

    public Weapon(Cell cell) {
        super(cell);
        setName("SkeletonCrusher");
    }

    @Override
    public String getTileName() {
        return "weapon";
    }
}

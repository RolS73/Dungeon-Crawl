package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public abstract class Item extends Actor {

    private String name;
    private String tileName;

    public Item(Cell cell, String name) {
        super(cell);
        this.name = name;
        this.tileName = name;
        setAttackPower(0);
    }

    public Item(String name) {
        this.name = name;
        setAttackPower(0);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }
}

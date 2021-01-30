package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class TrapBloody extends Item implements EnvironmentalDamage {

    private String anotherTileName = "spikeTrapBloodyActive";

    public TrapBloody(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String newName) {
        this.anotherTileName = newName;
    }

    @Override
    public boolean isEnvironmentalDamageActive() {
        return true;
    }
}

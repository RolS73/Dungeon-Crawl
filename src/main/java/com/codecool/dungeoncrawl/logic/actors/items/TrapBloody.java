package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class TrapBloody extends Item implements EnvironmentalDamageSource {

    private String anotherTileName = "spikeTrapBloodyActive";

    public TrapBloody(Cell cell, String name) {
        super(cell, name);
    }

    public void activate(Cell cell) {

    }

    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String newName) {
        this.anotherTileName = newName;
    }

    public boolean isPlayerBeingAffectedByAnEnvironmentalDamageSource(Cell cell) {
        return this.getCell() == cell;
    }
}

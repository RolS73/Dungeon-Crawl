package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class HealthUpgrade extends Item implements PickupableItem {

    private String tileName = "HealthUpgrade";

    public HealthUpgrade(Cell cell) {
        super(cell, "HealthUpgrade");
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    @Override
    public void Pickup() {

    }

    @Override
    public boolean isThisObjectPickupable() {
        return true;
    }

    @Override
    public boolean isThisPickupableItemCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell() == cell;
    }
}

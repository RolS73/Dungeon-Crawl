package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class LifeUpgrade extends Item implements PickupableItem {

    private String tileName = "life";
    private final int lifeUpgradeAmount;

    public LifeUpgrade(Cell cell, int lifeUpgradeAmount) {
        super(cell, "Life");
        this.lifeUpgradeAmount = lifeUpgradeAmount;
    }

    public LifeUpgrade(String nameInput, int lifeRestoreAmount) {
        super(nameInput);
        this.lifeUpgradeAmount = lifeRestoreAmount;
    }

    @Override
    public int getHealth() {
        return lifeUpgradeAmount;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }
}


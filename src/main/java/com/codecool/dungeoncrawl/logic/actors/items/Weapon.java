package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item {

    private int attackpowerIncrease;

    public Weapon(Cell cell, String name, int attackpowerIncrease) {
        super(cell, name);
        this.attackpowerIncrease = attackpowerIncrease;
    }

    public Weapon(String name, int attackpowerIncrease) {
        super(name);
        this.attackpowerIncrease = attackpowerIncrease;
    }

    @Override
    public String getTileName() {
        return "weapon";
    }

    public int getAttackpowerIncrease() {
        return attackpowerIncrease;
    }

    public void setAttackpowerIncrease(int attackpowerIncrease) {
        this.attackpowerIncrease = attackpowerIncrease;
    }
}

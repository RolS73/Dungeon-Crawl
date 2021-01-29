package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class TrapBloody extends Trap {

    private int cooldown = 0;
    private String anotherTileName = "trapBloodyResting";

    public TrapBloody(Cell cell, String name) {
        super(cell, name);
    }

    public void activate(Cell cell) {
        if (cooldown == 0 && this.getCell() == cell) {
            setAnotherTileName("trapBloodyActive");
            this.cooldown = 1;
        }  else {
            if (cooldown < 0) {
                cooldown--;
                setAnotherTileName("trapBloodyResting");
            }
        }
    }

    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String newName) {
        this.anotherTileName = newName;
    }
}

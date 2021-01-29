package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class TrapPlain extends Trap {

    private static int cooldown;
    private static String anotherTilename = "trapResting";

    @Override
    public String getTileName() {
        return anotherTilename;
    }

    public static void setAnotherTileName(String newName) {
        anotherTilename = newName;
    }

    public TrapPlain(Cell cell, String name) {
        super(cell, name);
    }

    public static void activate(Cell cell) {
        if (cooldown == 0) {
            setAnotherTileName("trapActive");
            cooldown = 1;
        }  else {
            if (cooldown < 0) {
                cooldown--;
                setAnotherTileName("trapResting");
            }
        }
    }

}

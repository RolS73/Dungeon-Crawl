package com.codecool.dungeoncrawl.logic.actors.items.mapdecoration;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class CandleStand extends Item {

    // Ez a map3 firestand grafika

    private String name = "candleStand";

    public CandleStand(Cell cell, String name) {
        super(cell, name);
    }

}

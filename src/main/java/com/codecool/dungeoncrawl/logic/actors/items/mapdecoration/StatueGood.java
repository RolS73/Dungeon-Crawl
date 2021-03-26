package com.codecool.dungeoncrawl.logic.actors.items.mapdecoration;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class StatueGood extends Item {

    // Ez egy random előforduló healing station lehet.

    private String name = "statueGood";

    public StatueGood(Cell cell, String name) {
        super(cell, name);
    }

}

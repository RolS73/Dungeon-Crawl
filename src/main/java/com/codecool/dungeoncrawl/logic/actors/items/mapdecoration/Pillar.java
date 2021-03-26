package com.codecool.dungeoncrawl.logic.actors.items.mapdecoration;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class Pillar extends Item {

    private String name = "pillar";

    public Pillar(Cell cell, String name) {
        super(cell, name);
    }

}

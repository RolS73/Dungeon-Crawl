package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {

    public Player(Cell cell) {
        super(cell);
        this.setAttackPower(4); // ez új
    }

    public String getTileName() {
        return "player";
    }

}

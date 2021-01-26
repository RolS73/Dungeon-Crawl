package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {

    private List<String> playersInventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
        this.setAttackPower(4); // ez új
    }

    public String getTileName() {
        return "player";
    }

    public List<String> getPlayersInventory() {
        return playersInventory;
    }
}

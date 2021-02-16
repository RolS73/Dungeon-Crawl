package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public abstract class Monster extends Actor {

    public Monster(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return null;
    }

    public abstract void monsterMove(int x, int y);


    public void playAttackSound() {}

    public void playDeathSound() {}

    public void rollForMonsterLoot() {}
}

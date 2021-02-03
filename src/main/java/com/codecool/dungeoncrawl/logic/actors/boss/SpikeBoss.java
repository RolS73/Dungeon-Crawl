package com.codecool.dungeoncrawl.logic.actors.boss;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class SpikeBoss extends Actor implements BossInterface {


    public SpikeBoss(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return null;
    }


    @Override
    public void move(int dx, int dy) {
        //dx és dy Player koordináták

        super.move(dx, dy);
    }
}

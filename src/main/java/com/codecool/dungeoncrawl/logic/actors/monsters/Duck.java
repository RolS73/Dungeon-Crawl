package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Duck extends Monster {

    public Duck(Cell cell) {
        super(cell);
        this.setAttackPower(1);
        this.setHealth(2);
    }

    @Override
    public String getTileName() {
        return "duck";
    }

    @Override
    public void monsterMove(int x, int y) {
        Cell nextCell = this.getCell().getNeighbor(x, y);

        if(nextCell.getType() == CellType.FLOOR){
            if(nextCell.getActor() instanceof Player){
                nextCell.getActor().setHealth(nextCell.getActor().getHealth()- this.getAttackPower());
                if(this.getHealth()<1){
                    this.getCell().setActor(null);
                }
            } else if (nextCell.getActor()!=null){
            } else {
                nextCell.setActor(this);
                this.getCell().setActor(null);
                this.setCell(nextCell);
            }

        }
    }
}

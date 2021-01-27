package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Duck extends Actor{

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
                this.setHealth(this.getHealth()-nextCell.getActor().getAttackPower());
                if(this.getHealth()<1){
                    this.getCell().setActor(null);
                }
                return;
            } else if (nextCell.getActor()!=null){
                return;
            }
            nextCell.setActor(this);
            this.getCell().setActor(null);
            this.setCell(nextCell);
        }
    }
}

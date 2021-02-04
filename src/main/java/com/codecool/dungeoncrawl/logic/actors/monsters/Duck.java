package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Duck extends Monster {

    private String name = "duckD";

    public Duck(Cell cell) {
        super(cell);
        this.setAttackPower(1);
        this.setHealth(2);
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        if (y<0) {
            this.name = "duckU";
        }
        if (y>0) {
            this.name = "duckD";
        }
        if (x<0) {
            this.name = "duckL";
        }
        if (x>0) {
            this.name = "duckR";
        }
        Cell nextCell = this.getCell().getNeighbor(x, y);

        if(nextCell.getCellType() == CellType.FLOOR){
            if(nextCell.getActor() instanceof Player){
                damageCalculation(nextCell);
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth()- this.getAttackPower());
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

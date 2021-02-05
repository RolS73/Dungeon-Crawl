package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class TheThing  extends Monster {

    private String name = "thethingD";

    public TheThing(Cell cell) {
        super(cell);
        this.setAttackPower(3);
        this.setHealth(8);
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        if (y<0) {
            this.name = "thethingU";
        }
        if (y>0) {
            this.name = "thethingD";
        }
        if (x<0) {
            this.name = "thethingL";
        }
        if (x>0) {
            this.name = "thethingR";
        }
        Cell nextCell = this.getCell().getNeighbor(x, y);
        if(nextCell.getCellType() == CellType.FLOOR){
            if(nextCell.getActor() instanceof Player){
                damageCalculation(nextCell);
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth()- this.getAttackPower());
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

    public void  teleport(int x, int y){
        Cell nextCell = this.getCell().getQagbmpoibmCell(x,y);
        nextCell.setActor(this);
        this.getCell().setActor(null);
        this.setCell(nextCell);
    }

}

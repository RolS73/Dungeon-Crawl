package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.setAttackPower(2);
        this.setHealth(7);
    }

    @Override
    public String getTileName() {
        return "skeleton";
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
        //Math.Random
        //if random>05
        //random<0.75
        //x*-1
//        Cell nextCell = super.getCell().getNeighbor(x, y);
//
//        if (nextCell == null) {
//            return;
//        }
//        if (nextCell.getActor() instanceof Player) {
//            nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
//            this.setHealth(this.getHealth() - nextCell.getActor().getAttackPower());
//
//        } else if (nextCell.getType() == CellType.FLOOR && nextCell.getActor() == null) {
//
//            super.getCell().setActor(null);
//            nextCell.setActor(this);
//            super.setCell(nextCell);
//
//        }
    }
}

package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Skeleton extends Actor {

    private int wasAttacked = 0;


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

        if (nextCell.getType() == CellType.FLOOR) {
            if (nextCell.getActor() instanceof Player) {
                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
                this.setHealth(this.getHealth() - nextCell.getActor().getAttackPower());
                if (this.getHealth() < 1) {
                    this.getCell().setActor(null);
                }
            } else if (nextCell.getActor() != null) {
            } else {
                nextCell.setActor(this);
                this.getCell().setActor(null);
                this.setCell(nextCell);
            }

        }

//    private void setRandom(int x, int y){
//        double random = Math.random()*10;
//        if(random<2.5){
//            this.x = 1; this.y = y;
//        } else if(random>=2.5 && random<5){
//            this.x = y; this.y = 1;
//        }else if(random>=5 && random<7.5){
//            this.x = -1; this.y = y;
//        } else if(random>=7.5){
//            this.x = y; this.y = -1;
//        }
//    }
    }
}

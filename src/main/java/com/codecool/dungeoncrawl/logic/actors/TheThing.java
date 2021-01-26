package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class TheThing  extends Actor{

    private int count;

    public TheThing(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "thething";
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

//    private int[] coordinateGenerator(){
//        int[] lolz = new int[]{5, 6};
//        return lolz;
//    }
}

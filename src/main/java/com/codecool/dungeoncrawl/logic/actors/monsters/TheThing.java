package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class TheThing  extends Monster {

    private int count;

    public TheThing(Cell cell) {
        super(cell);
        this.setAttackPower(3);
        this.setHealth(9);
    }

    @Override
    public String getTileName() {
        return "thething";
    }

    @Override
    public void monsterMove(int x, int y) {

        Cell nextCell = this.getCell().getNeighbor(x, y);

        if(nextCell.getCellType() == CellType.FLOOR){
            if(nextCell.getActor() instanceof Player){
                nextCell.getActor().setHealth(nextCell.getActor().getHealth()- this.getAttackPower());
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

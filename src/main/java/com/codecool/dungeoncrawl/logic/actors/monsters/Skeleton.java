package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Skeleton extends Monster {

    private String name = "skeletonD";

    public Skeleton(Cell cell) {
        super(cell);
        this.setAttackPower(2);
        this.setHealth(10);
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        if (y<0) {
            this.name = "skeletonU";
        }
        if (y>0) {
            this.name = "skeletonD";
        }
        if (x<0) {
            this.name = "skeletonL";
        }
        if (x>0) {
            this.name = "skeletonR";
        }
        Cell nextCell = this.getCell().getNeighbor(x, y);

        if (nextCell.getCellType() == CellType.FLOOR && nextCell.getCellType() != CellType.OBJECT) {
            if (nextCell.getActor() instanceof Player) {
                damageCalculation(nextCell);
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
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
    }
}

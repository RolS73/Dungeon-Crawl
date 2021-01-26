package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Guardian extends Actor{
    public Guardian(Cell cell) {
        super(cell);
        this.setAttackPower(6);
    }

    @Override
    public String getTileName() {
        return "guardian";
    }

    @Override
    public void monsterMove(int x, int y) {

        Cell nextCell = super.getCell().getNeighbor(x, y);

        if (nextCell == null) {
            return;
        }
        if (nextCell.getActor() != null) {

            nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
            this.setHealth(this.getHealth() - nextCell.getActor().getAttackPower());

            if (nextCell.getType() == CellType.FLOOR && nextCell.getActor() == null) {

                this.getCell().setActor(null);
                nextCell.setActor(this);
                this.setCell(nextCell);

            }
        }
    }
}

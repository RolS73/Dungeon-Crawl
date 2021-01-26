package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Guardian extends Actor{
    public Guardian(Cell cell) {
        super(cell);
        this.setAttackPower(2);
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
        if (nextCell.getActor() instanceof Player) {
            nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
            this.setHealth(this.getHealth() - nextCell.getActor().getAttackPower());

        } else if (nextCell.getType() == CellType.FLOOR && nextCell.getActor() == null) {

            super.getCell().setActor(null);
            nextCell.setActor(this);
            super.setCell(nextCell);

        }
    }
}

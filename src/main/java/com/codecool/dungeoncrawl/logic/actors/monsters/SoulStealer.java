package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class SoulStealer extends Monster {

    private String name = "soulStealerD";

    public SoulStealer(Cell cell) {
        super(cell);
        this.setAttackPower(6);
        this.setHealth(6 * 6 + 6);
        this.setAttackSoundFiles(new String[] {"genericSwing"}); //PLACEHOLDER
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        if (y < 0) {
            this.name = "soulStealerU";
        }
        if (y > 0) {
            this.name = "soulStealerD";
        }
        if (x < 0) {
            this.name = "soulStealerL";
        }
        if (x > 0) {
            this.name = "soulStealerR";
        }
        Cell nextCell = this.getCell().getNeighbor(x, y);

        if (nextCell.getCellType() == CellType.FLOOR && nextCell.getCellType() != CellType.OBJECT) {
            if (nextCell.getActor() instanceof Player) {
//                damageCalculation(nextCell);
                attack(nextCell);
//                playAttackSound();
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
//                if (this.getHealth() < 1) {
//                    this.getCell().setActor(null);
//                }
            } else if (nextCell.getActor() != null) {
            } else {
                nextCell.setActor(this);
                this.getCell().setActor(null);
                this.setCell(nextCell);
            }

        }

    }
}


package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.RandomGenerator;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class Duck extends Monster {

    private String name = "duckD";

    public Duck(Cell cell) {
        super(cell);
        this.setAttackPower(1);
        this.setHealth(1);
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
                playAttackSound();
                //Player.playHurtSound();
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

    public void playDeathSound() {
        Sounds.playSound("littleGizmoDeath");
    }

    public void playAttackSound() {
        int randomNum = RandomGenerator.nextInt(11);
        if (randomNum < 5) {
            Sounds.playSound("littleGizmoAttack1");
        } else if (randomNum > 5 && randomNum < 11) {
            Sounds.playSound("littleGizmoAttack2");
        } else {
            Sounds.playSound("Drready");
        }
    }
}

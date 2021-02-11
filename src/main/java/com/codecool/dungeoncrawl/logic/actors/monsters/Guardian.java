package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.RandomGenerator;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class Guardian extends Monster {

    private String name = "guardianD";

    private int count = 0;

    public Guardian(Cell cell) {
        super(cell);
        this.setAttackPower(9);
        this.setHealth(20);
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        Cell nextCell = super.getCell().getNeighbor(x, y);

        if (nextCell == null) {
            return;
        }
        if (nextCell.getActor() instanceof Player) {
            count++;
//            System.out.println(count);
            if(count > 2){
                playAttackSound();
                damageCalculation(nextCell);
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
                count = 0;
            }
        } else {
            count = 0;
        }
    }

    public void monsterLookat(int x, int y) {
        if (Math.abs(x)>Math.abs(y)) {
            if (x<0) {
                this.name = "guardianL";
            }
            if (x>0) {
                this.name = "guardianR";
            }

        } else {
            if (y<0) {
                this.name = "guardianU";
            }
            if (y>0) {
                this.name = "guardianD";
            }

        }
        if (Math.abs(x) + Math.abs(y) > 1) {
            count = 0;

        }
//        System.out.println(count);
    }

    public void playDeathSound() {
        Sounds.playSound("guardianDeath");
    }

    public void playAttackSound() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("guardianAttack1");
        } else {
            Sounds.playSound("guardianAttack2");
        }
    }
}

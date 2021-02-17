package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class CursedKing extends Monster {

    /* ez lenne a "fő boss" kb egy helybe állna egy pajzs mögött miközbe projectile-eket és skeliket spawnol rád, a pajzsát deaktiválni
    kell random spawnoló krisztályok aktiválásával, ezután bele tudunk ütni 3-4-et majd kezdődik a rota előröl.
    a projectil támadások lényegében random elhelyezett trapok lesznek kicserélt tile-al, annak a rendszerét még majd kidolgozom - Roland*/

    private String name = "cursedKingD";

    private int count = 0;

    public CursedKing(Cell cell) {
        super(cell);
        this.setAttackPower(10);
        this.setHealth(999);
        this.setAttackSoundFiles(new String[] {"Sword1"}); //PLACEHOLDER
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
                attack(nextCell);
//                damageCalculation(nextCell);
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
                count = 0;
            }
        } else {
            count = 0;
        }
    }

    @Override
    public void playDeathSound() {

    }

    public void monsterLookat(int x, int y) {
        if (Math.abs(x)>Math.abs(y)) {
            if (x<0) {
                this.name = "cursedKingL";
            }
            if (x>0) {
                this.name = "cursedKingR";
            }

        } else {
            if (y<0) {
                this.name = "cursedKingU";
            }
            if (y>0) {
                this.name = "cursedKingD";
            }

        }
        if (Math.abs(x) + Math.abs(y) > 1) {
            count = 0;

        }
//        System.out.println(count);
    }
}

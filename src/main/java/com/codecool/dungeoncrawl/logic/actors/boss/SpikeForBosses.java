package com.codecool.dungeoncrawl.logic.actors.boss;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monsters.Monster;

public class SpikeForBosses extends Monster {

    //private SpikeBoss mainBody;
    int count = 0;
    boolean stunned = false;
    private String name = "spikeforbossesL";



    public SpikeForBosses(Cell cell) {
        super(cell);
       // mainBody = Main.cheatingMapGetter().getBoss1();
        setAttackPower(10);
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {

        if (x<=0) {
            this.name = "spikeforbossesL";
        }
        if (x>0) {
            this.name = "spikeforbossesR";
        }

        Cell nextCell = this.getCell().getNeighbor(x, y);

        if(stunned){
            count++;
            if (count>5){
                stunned = false;
            }
            return;
        }

        if(nextCell.getCellType()== CellType.STUNNER){
            if(count>5){
                count = 0;
            } else if(count==0) {
                stunned = true;
                return;
            }
        }

        if (nextCell.getCellType() == CellType.BOSSFLOOR || nextCell.getCellType() == CellType.STUNNER) {
            if (nextCell.getActor() instanceof Player) {
                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
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

    public void remover(){
        this.getCell().setActor(null);
    }
}

package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.RandomGenerator;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sounds;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.AllMonsterLootList;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.LootChanceCalculator;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.LootRarityLevel;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.MonsterLootList;

public class TheThing  extends Monster {

    private String name = "thethingD";
    private final String lootListName = "theThing";
    private final MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    public TheThing(Cell cell) {
        super(cell);
        this.setAttackPower(3);
        this.setHealth(8);
        this.setAttackSoundFiles(new String[] {"spectre1", "spectre2"});
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        if (y<0) {
            this.name = "thethingU";
        }
        if (y>0) {
            this.name = "thethingD";
        }
        if (x<0) {
            this.name = "thethingL";
        }
        if (x>0) {
            this.name = "thethingR";
        }
        Cell nextCell = this.getCell().getNeighbor(x, y);
        if(nextCell.getCellType() == CellType.FLOOR){
            if(nextCell.getActor() instanceof Player){
                attack(nextCell);
//                playAttackSound();
//                damageCalculation(nextCell);
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth()- this.getAttackPower());
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

    public void  teleport(int x, int y){
        Cell nextCell = this.getCell().getQagbmpoibmCell(x,y);
        nextCell.setActor(this);
        this.getCell().setActor(null);
        this.setCell(nextCell);
    }

    public void playDeathSound() {
        Sounds.playSound("spectreDeath");
    }

    public void playAttackSound() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("theThingAttack1");
        } else {
            Sounds.playSound("theThingAttack2");
        }
    }

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(1) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootRarityLevel.LEGENDARY));
        }
    }

}

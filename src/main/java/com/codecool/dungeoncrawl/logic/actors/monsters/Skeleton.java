package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.RandomGenerator;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sounds;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.AllMonsterLootList;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.LootChanceCalculator;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.MonsterLootList;

public class Skeleton extends Monster {

    private String name = "skeletonD";
    private final String lootListName = "skeleton";
    private final MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    public Skeleton(Cell cell) {
        super(cell);
        this.setAttackPower(2);
        this.setHealth(10);
        this.setAttackSoundFiles(new String[] {"ZombieAttack2", "ZombieAttack1"});
    }

    @Override
    public void playDeathSound() {
        Sounds.playSound("SkeletonDeath");
    }

    public void playAttackSound() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("ZombieAttack2");
        } else {
            Sounds.playSound("ZombieAttack1");
        }
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        if (y < 0) {
            this.name = "skeletonU";
        }
        if (y > 0) {
            this.name = "skeletonD";
        }
        if (x < 0) {
            this.name = "skeletonL";
        }
        if (x > 0) {
            this.name = "skeletonR";
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

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(40) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootChanceCalculator.calculateLootRarityFourRarities(3, 11, 26)));
        }
    }
}

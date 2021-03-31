package com.codecool.dungeoncrawl.logic.actors.monsters;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sounds;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.LootTableCalculator;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.MonsterLootList;

public class GoblinPig extends Monster {

    private String name = "goblinPigD";
    private MonsterLootList = new MonsterLootList();

    public GoblinPig(Cell cell) {
        super(cell);
        this.setAttackPower(3);
        this.setHealth(15);
        this.setAttackSoundFiles(new String[] {"goblin1", "goblin2"});
    }

    @Override
    public void playDeathSound() {
        Sounds.playSound("genericDeath");
    }

    /*public void playAttackSound() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("ZombieAttack2");
        } else {
            Sounds.playSound("ZombieAttack1");
        }
    }*/

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        if (y < 0) {
            this.name = "goblinPigU";
        }
        if (y > 0) {
            this.name = "goblinPigD";
        }
        if (x < 0) {
            this.name = "goblinPigL";
        }
        if (x > 0) {
            this.name = "goblinPigR";
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
        /*int tableRoll = RandomGenerator.nextInt(100);
        if (tableRoll > 37) {
        } else if (tableRoll > 2 && tableRoll < 37) {
            if (this.getCell().getItem() == null) {
                this.getCell().setItem(new LootTable().getMonsterCommonLoot().get(0));
            } else {
                Main.cheatingMapGetter().getPlayer().getCell().setItem(new LootTable().getMonsterCommonLoot().get(0));
            }

        } else {
            if (this.getCell().getItem() == null) {
                this.getCell().setItem(new LootTable().getMonsterUniqueLoot().get(0));
            } else {
                Main.cheatingMapGetter().getPlayer().getCell().setItem(new LootTable().getMonsterUniqueLoot().get(0));
            }
        }*/

        LootTableCalculator.calculateLootRarity(0, 8, 29, 70);
    }

}

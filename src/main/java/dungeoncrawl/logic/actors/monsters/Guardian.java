package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.RandomGenerator;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.loottable.AllMonsterLootList;
import dungeoncrawl.logic.actors.items.looting.loottable.LootChanceCalculator;
import dungeoncrawl.logic.actors.items.looting.loottable.LootRarityLevel;
import dungeoncrawl.logic.actors.items.looting.loottable.MonsterLootList;

public class Guardian extends Monster {

    private String name = "guardianD";
    private final String lootListName = "guardian";
    private final MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    private int count = 0;

    public Guardian(Cell cell) {
        super(cell);
        this.setAttackPower(9);
        this.setHealth(20);
        this.setAttackSoundFiles(new String[] {"guardianAttack1", "guardianAttack2"});
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
//                playAttackSound();
                attack(nextCell);
//                damageCalculation(nextCell);
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

    public int getCount() {
        return count;
    }

    public void playAttackSound() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("guardianAttack1");
        } else {
            Sounds.playSound("guardianAttack2");
        }
    }

    @Override
    public void playDeathSound() {
        Sounds.playSound("guardianDeath");
    }

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(1) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootRarityLevel.LEGENDARY));
        }
    }
}

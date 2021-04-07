package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.loottable.AllMonsterLootList;
import dungeoncrawl.logic.actors.items.looting.loottable.LootChanceCalculator;
import dungeoncrawl.logic.actors.items.looting.loottable.MonsterLootList;

public class GoblinPig extends Monster {

    private String name = "goblinPigD";
    private String lootListName = "goblinPig";
    private MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

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
                attack(nextCell);
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
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootChanceCalculator.calculateLootRarityFourRarities(0, 8, 29)));
        }
    }

}

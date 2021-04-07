package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.loottable.AllMonsterLootList;
import dungeoncrawl.logic.actors.items.looting.loottable.LootChanceCalculator;
import dungeoncrawl.logic.actors.items.looting.loottable.LootRarityLevel;
import dungeoncrawl.logic.actors.items.looting.loottable.MonsterLootList;

public class Duck extends Monster {

    private String name = "duckD";
    private final String lootListName = "duck";
    private final MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    public Duck(Cell cell) {
        super(cell);
        this.setAttackPower(1);
        this.setHealth(1);
        this.setAttackSoundFiles(new String[] {"duck1", "duck2", "duck1", "duck2", "duck1", "duck2", "duck1", "duck2",
                "duck1", "duck2", "duck1", "duck2", "Drready"});
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
                attack(nextCell);
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

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(1) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootRarityLevel.LEGENDARY));
        }
    }

    @Override
    public void playDeathSound() {
        Sounds.playSound("genericBloodSplashDeath2");
    }
}

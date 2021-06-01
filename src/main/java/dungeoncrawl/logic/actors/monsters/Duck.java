package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.loottable.AllMonsterLootList;
import dungeoncrawl.logic.actors.items.looting.loottable.LootChanceCalculator;
import dungeoncrawl.logic.actors.items.looting.loottable.LootRarityLevel;
import dungeoncrawl.logic.actors.items.looting.loottable.MonsterLootList;

public class Duck extends Monster {

    private final String lootListName = "duck";
    private final MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    public Duck(Cell cell) {
        super(cell);
        super.movementType = MonsterMovementType.PATROL;
        this.setAttackPower(1);
        this.setHealth(1);
        this.setAttackSoundFiles(new String[] {"duck1", "duck2", "duck1", "duck2", "duck1", "duck2", "duck1", "duck2",
                "duck1", "duck2", "duck1", "duck2", "Drready"});
        this.setDeathSoundFile("genericBloodSplashDeath2");
        fillDirectionalSpritesMap("duckU", "duckD", "duckL", "duckR");
        super.name = getActorDirectionalSpriteByDirection(Direction.DOWN);
    }

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(1) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootRarityLevel.LEGENDARY));
        }
    }
}

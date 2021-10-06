package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.RandomGenerator;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.loottable.AllMonsterLootList;
import dungeoncrawl.logic.actors.items.looting.loottable.LootChanceCalculator;
import dungeoncrawl.logic.actors.items.looting.loottable.LootRarityLevel;
import dungeoncrawl.logic.actors.items.looting.loottable.MonsterLootList;

public class Guardian extends Monster {

    private final String lootListName = "guardian";
    private final MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    public Guardian(Cell cell) {
        super(cell);
        this.setMovementType(MonsterMovementType.GUARD);
        this.setAttackPower(9);
        this.setHealth(20);
        this.setAttackSoundFiles(new String[] {"guardianAttack1", "guardianAttack2"});
        this.setDeathSoundFile("guardianDeath");
        fillDirectionalSpritesMap("guardianU", "guardianD", "guardianL", "guardianR");
        super.name = getActorDirectionalSpriteByDirection(Direction.DOWN);
    }

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(1) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootRarityLevel.LEGENDARY));
        }
    }
}

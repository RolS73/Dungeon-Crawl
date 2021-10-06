package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.Main;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.RandomGenerator;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.loottable.AllMonsterLootList;
import dungeoncrawl.logic.actors.items.looting.loottable.LootChanceCalculator;
import dungeoncrawl.logic.actors.items.looting.loottable.MonsterLootList;

public class Skeleton extends Monster {

    private final String lootListName = "skeleton";
    private final MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    public Skeleton(Cell cell) {
        super(cell);
        this.setMovementType(MonsterMovementType.PATROL);
        this.setAttackPower(2);
        this.setHealth(10);
        this.setAttackSoundFiles(new String[] {"ZombieAttack2", "ZombieAttack1"});
        this.setDeathSoundFile("SkeletonDeath");
        fillDirectionalSpritesMap("skeletonU", "skeletonD", "skeletonL", "skeletonR");
        super.name = getActorDirectionalSpriteByDirection(Direction.DOWN);
    }

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(40) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootChanceCalculator.calculateLootRarityFourRarities(3, 11, 26)));
        }
    }
}

package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.loottable.AllMonsterLootList;
import dungeoncrawl.logic.actors.items.looting.loottable.LootChanceCalculator;
import dungeoncrawl.logic.actors.items.looting.loottable.MonsterLootList;

public class GoblinPig extends Monster {

    private String lootListName = "goblinPig";
    private MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    public GoblinPig(Cell cell) {
        super(cell);
        super.movementType = MonsterMovementType.PATROL;
        this.setAttackPower(3);
        this.setHealth(15);
        this.setAttackSoundFiles(new String[] {"goblin1", "goblin2"});
        this.setDeathSoundFile("genericDeath");
        fillDirectionalSpritesMap("goblinPigU", "goblinPigD", "goblinPigL", "goblinPigR");
        super.name = getActorDirectionalSpriteByDirection(Direction.DOWN);
    }

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(40) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootChanceCalculator.calculateLootRarityFourRarities(0, 8, 29)));
        }
    }

}

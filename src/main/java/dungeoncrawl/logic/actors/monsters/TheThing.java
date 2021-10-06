package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.RandomGenerator;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.loottable.AllMonsterLootList;
import dungeoncrawl.logic.actors.items.looting.loottable.LootChanceCalculator;
import dungeoncrawl.logic.actors.items.looting.loottable.LootRarityLevel;
import dungeoncrawl.logic.actors.items.looting.loottable.MonsterLootList;

public class TheThing  extends Monster {

    private final String lootListName = "theThing";
    private final MonsterLootList lootList = AllMonsterLootList.getInstance().getIndividualLootListBasedOnName(lootListName);

    public TheThing(Cell cell) {
        super(cell);
        this.setMovementType(MonsterMovementType.PATROL);
        this.setAttackPower(3);
        this.setHealth(8);
        this.setAttackSoundFiles(new String[] {"spectre1", "spectre2"});
        this.setDeathSoundFile("spectreDeath");
        fillDirectionalSpritesMap("thethingU", "thethingD", "thethingL", "thethingR");
        super.name = getActorDirectionalSpriteByDirection(Direction.DOWN);
    }

    public void  teleport(int x, int y){
        Cell nextCell = this.getCell().getQagbmpoibmCell(x,y);
        nextCell.setActor(this);
        this.getCell().setActor(null);
        this.setCell(nextCell);
    }

    @Override
    public void rollForMonsterLoot() {
        if (LootChanceCalculator.isLootDropped(1) && this.getCell().getItem() == null) {
            this.getCell().setItem(lootList.getRandomItemFromLootListByRarity(LootRarityLevel.LEGENDARY));
        }
    }

}

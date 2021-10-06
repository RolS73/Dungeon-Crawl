package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.actors.Player;

public class SoulStealer extends Monster {

    public SoulStealer(Cell cell) {
        super(cell);
        this.setMovementType(MonsterMovementType.PATROL);
        this.setAttackPower(6);
        this.setHealth(6 * 6 + 6);
        this.setAttackSoundFiles(new String[] {"genericSwing"}); //PLACEHOLDER
        this.setDeathSoundFile("SkeletonDeath");
        fillDirectionalSpritesMap("soulStealerU", "soulStealerD", "soulStealerL", "soulStealerR");
        super.name = getActorDirectionalSpriteByDirection(Direction.DOWN);
    }

}


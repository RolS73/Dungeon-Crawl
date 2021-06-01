package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.actors.Player;

public class CursedKing extends Monster {

    /* ez lenne a "fő boss" kb egy helybe állna egy pajzs mögött miközbe projectile-eket és skeliket spawnol rád, a pajzsát deaktiválni
    kell random spawnoló krisztályok aktiválásával, ezután bele tudunk ütni 3-4-et majd kezdődik a rota előröl.
    a projectil támadások lényegében random elhelyezett trapok lesznek kicserélt tile-al, annak a rendszerét még majd kidolgozom - Roland*/

    public CursedKing(Cell cell) {
        super(cell);
        super.movementType = MonsterMovementType.GUARD;
        this.setAttackPower(10);
        this.setHealth(999);
        this.setAttackSoundFiles(new String[] {"Sword1"}); //PLACEHOLDER
        fillDirectionalSpritesMap("cursedKingU", "cursedKingD", "cursedKingL", "cursedKingR");
        super.name = getActorDirectionalSpriteByDirection(Direction.DOWN);
    }
}

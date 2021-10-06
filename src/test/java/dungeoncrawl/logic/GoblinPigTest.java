package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.monsters.GoblinPig;
import dungeoncrawl.logic.actors.monsters.Monster;
import dungeoncrawl.screens.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//import dungeoncrawl.logic.actors.monsters.GoblinPig

public class GoblinPigTest {

    GameMap testMap = new GameMap(3, 3, CellType.FLOOR);
    GoblinPig goblinPig = new GoblinPig(testMap.getCell(2, 2));

    @Test
    void correctMovementTypeAfterInitialization() {
        assertEquals(goblinPig.getMovementType(), Monster.MonsterMovementType.PATROL);
    }

    @Test
    void correctHitSoundFiles() {
        assertEquals(goblinPig.getAttackSoundFiles()[0], "goblin1");
        assertEquals(goblinPig.getAttackSoundFiles()[1], "goblin2");
    }

    @Test
    void correctDirectionalSprites() {
        assertEquals(goblinPig.getActorDirectionalSpriteByDirection(Direction.LEFT), "goblinPigL");
        assertEquals(goblinPig.getActorDirectionalSpriteByDirection(Direction.RIGHT), "goblinPigR");
        assertEquals(goblinPig.getActorDirectionalSpriteByDirection(Direction.UP), "goblinPigU");
        assertEquals(goblinPig.getActorDirectionalSpriteByDirection(Direction.DOWN), "goblinPigD");
    }
}

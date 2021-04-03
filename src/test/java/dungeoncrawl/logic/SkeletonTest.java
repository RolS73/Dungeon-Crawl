package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.monsters.Skeleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SkeletonTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(0,1));
    Skeleton skeleton = new Skeleton(gameMap.getCell(1,1));



    @Test
    void attackAndMove() {
        skeleton.monsterMove(-1,0);
        assertEquals(13, player.getHealth());
        player.move(1,0);
        Assertions.assertEquals(skeleton.getHealth(), 9);
    }

    @Test
    void moveUpdatesCells() {
        skeleton.monsterMove(1, 0);

        Assertions.assertEquals(2, skeleton.getX());
        Assertions.assertEquals(1, skeleton.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        Assertions.assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setCellType(CellType.WALL);
        skeleton.monsterMove(1, 0);

        Assertions.assertEquals(1, skeleton.getX());
        Assertions.assertEquals(1, skeleton.getY());
    }

    @Test
    void tileName() {
        assertEquals(skeleton.getTileName(), "skeletonD");
    }

    @Test
    void randomLoot() {
        skeleton.rollForMonsterLoot();
        assertNotNull(gameMap.getCell(1, 1).getItem());
    }
}

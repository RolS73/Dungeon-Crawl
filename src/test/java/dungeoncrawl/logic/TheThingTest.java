package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.monsters.TheThing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TheThingTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(0,1));
    TheThing theThing = new TheThing(gameMap.getCell(1,1));



    @Test
    void attackAndMove() {
        theThing.monsterMove(-1,0);
        assertEquals(12, player.getHealth());
        player.move(1,0);
        Assertions.assertEquals(theThing.getHealth(), 7);
    }

    @Test
    void moveUpdatesCells() {
        theThing.monsterMove(1, 0);

        Assertions.assertEquals(2, theThing.getX());
        Assertions.assertEquals(1, theThing.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        Assertions.assertEquals(theThing, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setCellType(CellType.WALL);
        theThing.monsterMove(1, 0);

        Assertions.assertEquals(1, theThing.getX());
        Assertions.assertEquals(1, theThing.getY());
    }

    @Test
    void tileName() {
        assertEquals(theThing.getTileName(), "thethingD");
    }

    @Test
    void teleportTest() {
        theThing.teleport(2,2);
        Assertions.assertEquals(theThing.getY(), 2);
        Assertions.assertEquals(theThing.getX(), 2);
    }
}

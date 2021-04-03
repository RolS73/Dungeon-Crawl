package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.monsters.Duck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DuckTest {

    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(0,1));
    Duck duck = new Duck(gameMap.getCell(1,1));



    @Test
    void attackAndMove() {
        duck.monsterMove(-1,0);
        assertEquals(14, player.getHealth());
        player.move(1,0);
        Assertions.assertEquals(0, duck.getHealth());
    }

    @Test
    void moveUpdatesCells() {
        duck.monsterMove(1, 0);

        Assertions.assertEquals(2, duck.getX());
        Assertions.assertEquals(1, duck.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        Assertions.assertEquals(duck, gameMap.getCell(2, 1).getActor());
        duck.monsterMove(0, 1);
        duck.monsterMove(0, -1);
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setCellType(CellType.WALL);
        duck.monsterMove(1, 0);

        Assertions.assertEquals(1, duck.getX());
        Assertions.assertEquals(1, duck.getY());
    }

    @Test
    void tileName() {
        assertEquals(duck.getTileName(), "duckD");
    }
}

package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.items.looting.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbor(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        assertEquals(null, cell.getNeighbor(0, -1));

        cell = map.getCell(1, 2);
        assertEquals(null, cell.getNeighbor(0, 1));
    }

    @Test
    void cellNames() {
        assertEquals("floor", map.getCell(0,0).getTileName());
    }

    @Test
    void cellSetters() {
        Player player = new Player(map.getCell(1, 1));
        map.getCell(0,0).setActor(player);
        Assertions.assertEquals(Player.class ,map.getCell(0,0).getActor().getClass());
        Key key = new Key(map.getCell(2,2));
        map.getCell(2,2).setItem(key);
        Assertions.assertEquals(Key.class, map.getCell(2,2).getItem().getClass());
        map.getCell(2,0).setActor(key);
        Assertions.assertEquals(Key.class, map.getCell(2,0).getItem().getClass());
        map.getCell(0,0).setX(4);
        map.getCell(0,2).setY(2);
        assertEquals("floor", map.getCell(0,0).getTileName());
    }
}
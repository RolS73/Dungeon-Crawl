package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monsters.Duck;
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
        assertEquals(0, duck.getHealth());
    }

    @Test
    void moveUpdatesCells() {
        duck.monsterMove(1, 0);

        assertEquals(2, duck.getX());
        assertEquals(1, duck.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertEquals(duck, gameMap.getCell(2, 1).getActor());
        duck.monsterMove(0, 1);
        duck.monsterMove(0, -1);
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setCellType(CellType.WALL);
        duck.monsterMove(1, 0);

        assertEquals(1, duck.getX());
        assertEquals(1, duck.getY());
    }

    @Test
    void tileName() {
        assertEquals(duck.getTileName(), "duckD");
    }
}

package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monsters.TheThing;
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
        assertEquals(theThing.getHealth(), 7);
    }

    @Test
    void moveUpdatesCells() {
        theThing.monsterMove(1, 0);

        assertEquals(2, theThing.getX());
        assertEquals(1, theThing.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertEquals(theThing, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setCellType(CellType.WALL);
        theThing.monsterMove(1, 0);

        assertEquals(1, theThing.getX());
        assertEquals(1, theThing.getY());
    }

    @Test
    void tileName() {
        assertEquals(theThing.getTileName(), "thethingD");
    }

    @Test
    void teleportTest() {
        theThing.teleport(2,2);
        assertEquals(theThing.getY(), 2);
        assertEquals(theThing.getX(), 2);
    }
}

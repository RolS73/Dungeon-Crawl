package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monsters.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(3, 3, CellType.BOSSFLOOR);

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setCellType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void attackPowerGrowth() {
        Player player = new Player(gameMap.getCell(1,1));
        player.raiseAttackPower(3);
        assertEquals(player.getAttackPower(), 4);
    }

    @Test
    void teleportTest() {
        Player player = new Player(gameMap.getCell(1,1));
        player.teleport(2,2);
        assertEquals(player.getY(), 2);
        assertEquals(player.getX(), 2);
    }

    @Test
    void wallCheat() {
        Player player = new Player(gameMap.getCell(1,1));
        player.setWallCheatOn(true);
        assertTrue(player.isWallCheatOn());
        gameMap = new GameMap(3, 3, CellType.EMPTY);
        player.move(1, 0);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        gameMap = new GameMap(4, 4, CellType.BOSSFLOOR);
        player.move(1, 0);
        gameMap = new GameMap(3, 3, CellType.EMPTY);
        player.move(-1, 0);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void bossFightOn() {
        Player player = new Player(gameMap.getCell(1,1));
        gameMap = new GameMap(3, 3, CellType.BOSSFLOOR);
        player.move(1, 0);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        player.move(1, 1);
        player.move(-1, -1);
        assertTrue(player.isThisABossFight());
    }

    @Test
    void vegyesUseless() {
        Player player = new Player(gameMap.getCell(1,1));
        //player.playDeathSound();
        player.setArmor(1);
        assertEquals(player.getArmor(), 1);
    }
}
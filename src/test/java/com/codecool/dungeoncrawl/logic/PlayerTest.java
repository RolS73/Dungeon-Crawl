package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(0,0));

    @Test
    void maxHealthWithRaise() {
        player.raiseMaxHealth(10);
        assertEquals(25, player.getMaxHealth());
    }

    @Test
    void moneyWithChanges() {
        assertEquals(0, player.getMoneyAmount());
        player.raiseMoneyAmount(100);
        assertEquals(100, player.getMoneyAmount());
        player.setMoneyAmount(20);
        assertEquals(20, player.getMoneyAmount());
    }

    @Test
    void getSTR() {
        assertEquals(1, player.getStrength());
    }

    @Test
    void healthChanges() {
        player.lowerHealth(3);
        assertEquals(12, player.getHealth());
        player.restoreHealth(2);
        assertEquals(14, player.getHealth());
    }

    @Test
    void playerTileName() {
        player.setTileName("baba");
        assertEquals("baba", player.getTileName());

    }

}

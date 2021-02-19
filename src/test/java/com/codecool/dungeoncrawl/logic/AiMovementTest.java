package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monsters.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AiMovementTest {

    private static GameMap gameMap = new GameMap(10, 10, CellType.FLOOR);
    AiMovement ai = new AiMovement(gameMap);
    private static Player player = new Player(gameMap.getCell(9,9));

    @BeforeAll
    static void beforeAll() {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Skeleton(gameMap.getCell(0,1)));
        monsters.add(new Duck(gameMap.getCell(0,0)));
        monsters.add(new Guardian(gameMap.getCell(0,2)));
        monsters.add(new TheThing(gameMap.getCell(2,0)));
        gameMap.monsters = monsters;
        gameMap.player = player;
    }

    @Test
    void movingMonsters() {
        ai.monsterMover();
        ai.monsterMover();
        ai.monsterMover();
        ai.monsterMover();
        ai.monsterMover();
        ai.monsterMover();
        ai.monsterMover();
        assertEquals(gameMap.getCell(7,7), gameMap.monsters.get(1).getCell());
    }

    @Test
    void nearbyTest() {
        GameMap gameMap1 = new GameMap(3,3, CellType.FLOOR);
        Skeleton skeleton = new Skeleton(gameMap1.getCell(0,0));
        Player player1 = new Player(gameMap.getCell(1,0));
        ai.monsterMover();
        assertEquals(gameMap1.getCell(0,0), skeleton.getCell());
    }
}

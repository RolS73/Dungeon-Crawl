package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monsters.Guardian;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GuardianTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(0,1));
    Guardian guardian = new Guardian(gameMap.getCell(1,1));



    @Test
    void attackAndMove() {
        guardian.monsterMove(-1,0);
        assertEquals(15, player.getHealth());
        player.move(1,0);
        assertEquals(guardian.getHealth(), 19);
        guardian.monsterMove(-1,0);
        guardian.monsterMove(-1,0);
        assertEquals(6, player.getHealth());
        guardian.monsterLookat(3, 0);
        assertEquals(0, guardian.getCount());
    }

    @Test
    void moveNotUpdatesCells() {
        guardian.monsterMove(1, 0);

        assertEquals(1, guardian.getX());
        assertEquals(1, guardian.getY());
        assertNull(gameMap.getCell(2, 1).getActor());
    }

    @Test
    void lookAtEverything() {
        guardian.monsterLookat(0, 1);
        assertEquals(guardian.getTileName(), "guardianD");
        guardian.monsterLookat(0, -1);
        assertEquals(guardian.getTileName(), "guardianU");
        guardian.monsterLookat(1, 0);
        assertEquals(guardian.getTileName(), "guardianR");
        guardian.monsterLookat(-1, 0);
        assertEquals(guardian.getTileName(), "guardianL");
    }

//    if (Math.abs(x)>Math.abs(y)) {
//            if (x<0) {
//                this.name = "guardianL";
//            }
//            if (x>0) {
//                this.name = "guardianR";
//            }
//
//        } else {
//            if (y<0) {
//                this.name = "guardianU";
//            }
//            if (y>0) {
//                this.name = "guardianD";
//            }
//
//        }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setCellType(CellType.WALL);
        guardian.monsterMove(1, 0);

        assertEquals(1, guardian.getX());
        assertEquals(1, guardian.getY());
    }

    @Test
    void tileName() {
        assertEquals(guardian.getTileName(), "guardianD");
    }
}

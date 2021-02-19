package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {

    private static GameMap gameMap = new GameMap(10, 10, CellType.FLOOR);

    @Test
    void gettersInMapClass() {
        assertNull(gameMap.getBoss1());
        assertNotNull(gameMap.getDoorsSealedFromOtherSideArray());
        assertNotNull(gameMap.getEndlessCycleTraps());
        assertNotNull(gameMap.getInteractablesArray());
        assertNotNull(gameMap.getMapStateSwitchers());
        assertNotNull(gameMap.getHiddenEnemySpawnersCollection());
        assertNotNull(gameMap.getPlacedItemsCollection());
        assertNull(gameMap.getPlayer());
        assertNotNull(gameMap.getSwitchablesCollection());
        assertNotNull(gameMap.getSuspiciousWallsCollection());
        assertNotNull(gameMap.getProjectilesCollection());
        assertNotNull(gameMap.getMapQuickTravelPassages());
        assertNotNull(gameMap.getHiddenPassagesCollection());
        assertNotNull(gameMap.getLeverSwitchCollection());
        assertNotNull(gameMap.getMonsters());
        assertNotNull(gameMap.getGateOpenableByASwitchCollection());
        assertNotNull(gameMap.getChestsCollection());
        assertNotNull(gameMap.getHiddenItemsCollection());
    }


}

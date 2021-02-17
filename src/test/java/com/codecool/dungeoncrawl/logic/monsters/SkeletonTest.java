package com.codecool.dungeoncrawl.logic.monsters;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actors.monsters.Skeleton;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SkeletonTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(0,0));
    List<Monster> skel = new ArrayList<>();



    @Test
    void attackAndMove() {
        gameMap.getMonsters().add(new Skeleton(gameMap.getCell(0,3)));


    }
}

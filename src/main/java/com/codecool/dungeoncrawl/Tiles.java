package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("suspiciousWall", new Tile(10, 17));
        tileMap.put("secretPassage", new Tile(2, 0));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("hiddenEnemySpawner", new Tile(2, 0));
        tileMap.put("stairwayUp", new Tile(4, 6));

        tileMap.put("bossfloor", new Tile(19,1));
        tileMap.put("bosshead", new Tile(22,23));
        tileMap.put("bossspike", new Tile(20,6));
        tileMap.put("bosshand", new Tile(10,22));

        tileMap.put("sealedFromOtherSideDoor", new Tile(3, 4));
        tileMap.put("sealedFromOtherSideDoorOpened", new Tile(4, 4));
        tileMap.put("lockedDoor", new Tile(3, 3));
        tileMap.put("openedDoor", new Tile(4, 3));

        tileMap.put("firestand", new Tile(4, 15));
        tileMap.put("puzzleFireStandInActive", new Tile(3, 14));
        tileMap.put("puzzleFireStandActive", new Tile(4, 14));
        tileMap.put("spikeTrapResting", new Tile(22, 0));
        tileMap.put("spikeTrapActive", new Tile(23, 0));
        tileMap.put("spikeTrapBloodyResting", new Tile(22, 1));
        tileMap.put("spikeTrapBloodyActive", new Tile(23, 1));
        tileMap.put("gateOpenableByASwitch", new Tile(5, 3));
        tileMap.put("leverSwitchOff", new Tile(3, 10));
        tileMap.put("leverSwitchOn", new Tile(4, 10));

        tileMap.put("breakable", new Tile(10, 8));
        tileMap.put("barrel", new Tile(10, 8));
        tileMap.put("crate", new Tile(15, 14));
        tileMap.put("crate2", new Tile(14, 14));

        tileMap.put("apple", new Tile(15, 29));
        tileMap.put("bread", new Tile(15, 28));
        tileMap.put("cheese", new Tile(18, 28));
        tileMap.put("fish", new Tile(17, 29));
        tileMap.put("steak", new Tile(16, 28));
        tileMap.put("ham", new Tile(17, 28));

        tileMap.put("lifeUpgrade1", new Tile(24, 22));
        tileMap.put("lifeUpgrade2", new Tile(25, 22));
        tileMap.put("lifeUpgrade3", new Tile(27, 22));
        tileMap.put("lifeUpgrade4", new Tile(26, 22));

        tileMap.put("armorUpgrade1", new Tile(0, 23));
        tileMap.put("armorUpgrade2", new Tile(1, 23));
        tileMap.put("armorUpgrade3", new Tile(4, 23));

        tileMap.put("chest1", new Tile(8, 6));
        tileMap.put("chest1Opened", new Tile(9, 6));
        tileMap.put("chest2", new Tile(10, 6));
        tileMap.put("chest2Opened", new Tile(11, 6));
        tileMap.put("chest3", new Tile(10, 6));
        tileMap.put("chest3Opened", new Tile(11, 6));

        tileMap.put("player", new Tile(27, 0));
        tileMap.put("playerArmored1", new Tile(28, 0));
        tileMap.put("playerArmored2", new Tile(31, 0));

        tileMap.put("duck", new Tile(25, 7));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("guardian", new Tile(30,6));
        tileMap.put("thething", new Tile(23,7));

        tileMap.put("key", new Tile(16, 23));
        tileMap.put("life", new Tile(26, 22));
        tileMap.put("weapon", new Tile(0, 31));

        tileMap.put("sword1", new Tile(2, 31));
        tileMap.put("dagger1", new Tile(0, 28));
        tileMap.put("staff1", new Tile(0, 26));
        tileMap.put("hammer1", new Tile(5, 29));

        tileMap.put("sword2", new Tile(1, 31));
        tileMap.put("staff2", new Tile(2, 26));
        tileMap.put("axe2", new Tile(7, 29));
        tileMap.put("hammer2", new Tile(6, 29));

        tileMap.put("sword3", new Tile(2, 30));
        tileMap.put("staff3", new Tile(1, 26));
        tileMap.put("axe3", new Tile(9, 29));
        tileMap.put("hammer3", new Tile(6, 30));


    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}

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
        tileMap.put("secretPassage", new Tile(0, 0));
        tileMap.put("floor", new Tile(2, 0));

        tileMap.put("sealedFromOtherSideDoor", new Tile(3, 4));
        tileMap.put("sealedFromOtherSideDoorOpened", new Tile(4, 4));
        tileMap.put("lockedDoor", new Tile(3, 3));
        tileMap.put("openedDoor", new Tile(4, 3));

        tileMap.put("firestand", new Tile(4, 15));
        tileMap.put("breakable", new Tile(10, 8));
        tileMap.put("barrel", new Tile(10, 8));
        tileMap.put("crate", new Tile(15, 14));
        tileMap.put("crate2", new Tile(14, 14));

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

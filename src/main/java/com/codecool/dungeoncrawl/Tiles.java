package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 48;

    private static Image tileset = new Image("/tiles48.png", 1567, 1567, true, false);
    private static Image parallax = new Image("/paralaxx1440.png", 1440, 1440, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {

            x = i * (TILE_WIDTH + 1);
            y = j * (TILE_WIDTH + 1);

            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("trees", new Tile(0, 12));
        tileMap.put("treeTop", new Tile(1, 13));
        tileMap.put("wall", new Tile(1, 0));
        tileMap.put("bushes", new Tile(1, 12));
        tileMap.put("palaceWall", new Tile(1, 14));
        tileMap.put("suspiciousWall", new Tile(1, 0));
        tileMap.put("secretPassage", new Tile(2, 0));
        tileMap.put("passage", new Tile(3, 0));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("forestPath", new Tile(4, 12));
        tileMap.put("hiddenEnemySpawner", new Tile(2, 0));
        tileMap.put("stairwayUp", new Tile(3, 0));
        tileMap.put("stairwayUpMap2", new Tile(0, 13));
        tileMap.put("stairwayDown", new Tile(6, 0));
        tileMap.put("secretPassageDown", new Tile(7, 0));

        tileMap.put("spikeboss", new Tile(2,3));
        tileMap.put("spikeforbossesL", new Tile(3,2));
        tileMap.put("spikeforbossesR", new Tile(2,2));
        tileMap.put("stunner", new Tile(5, 0));
        tileMap.put("bossfloor", new Tile(4,0));
        tileMap.put("WhiteWizard", new Tile(1, 1));
        tileMap.put("friendlyDwarf", new Tile(25, 6));

        tileMap.put("sealedFromOtherSideDoor", new Tile(8, 7));
        tileMap.put("sealedFromOtherSideDoorOpened", new Tile(9, 7));
        tileMap.put("lockedDoor", new Tile(8, 7));
        tileMap.put("openedDoor", new Tile(9, 7));

        tileMap.put("firestand", new Tile(5, 7));
        tileMap.put("puzzleFireStandInActive", new Tile(4, 7));
        tileMap.put("puzzleFireStandActive", new Tile(6, 7));

        tileMap.put("spikeTrapResting", new Tile(9, 0));
        tileMap.put("spikeTrapActive", new Tile(11, 0));
        tileMap.put("spikeTrapBloodyResting", new Tile(10, 0));
        tileMap.put("spikeTrapBloodyActive", new Tile(12, 0));
        tileMap.put("dartTurret", new Tile(4, 6));

        tileMap.put("gateOpenableByASwitch", new Tile(15, 0));

        tileMap.put("leverSwitchOff", new Tile(13, 0));
        tileMap.put("leverSwitchOn", new Tile(14, 0));

        tileMap.put("breakable", new Tile(6, 5));
        tileMap.put("barrel", new Tile(8, 5));
        tileMap.put("crate", new Tile(10, 5));
        tileMap.put("crate2", new Tile(9, 5));

        tileMap.put("apple", new Tile(12, 5));
        tileMap.put("bread", new Tile(13, 5));
        tileMap.put("cheese", new Tile(14, 5));
        tileMap.put("fish", new Tile(15, 5));
        tileMap.put("steak", new Tile(12, 6));
        tileMap.put("ham", new Tile(13, 6));

        tileMap.put("Money", new Tile(0, 8));

        tileMap.put("lifeUpgrade1", new Tile(9, 4));
        tileMap.put("lifeUpgrade2", new Tile(9, 3));
        tileMap.put("lifeUpgrade3", new Tile(9, 2));
        tileMap.put("lifeUpgrade4", new Tile(9, 1));

        tileMap.put("armorUpgrade0", new Tile(11, 1));
        tileMap.put("armorUpgrade1", new Tile(11, 2));
        tileMap.put("armorUpgrade2", new Tile(11, 3));
        tileMap.put("armorUpgrade3", new Tile(11, 4));

        tileMap.put("chest1", new Tile(0, 5));
        tileMap.put("chest1Opened", new Tile(0, 6));
        tileMap.put("chest2", new Tile(1, 5));
        tileMap.put("chest2Opened", new Tile(1, 6));
        tileMap.put("chest3", new Tile(2, 5));
        tileMap.put("chest3Opened", new Tile(2, 6));

        tileMap.put("playerD", new Tile(0, 1));
        tileMap.put("playerL", new Tile(0, 2));
        tileMap.put("playerR", new Tile(0, 3));
        tileMap.put("playerU", new Tile(0, 4));

        tileMap.put("playerArmored1", new Tile(28, 0));                     //
        tileMap.put("playerArmored2", new Tile(31, 0));                     //

        tileMap.put("duckD", new Tile(4, 1));
        tileMap.put("duckL", new Tile(4, 2));
        tileMap.put("duckR", new Tile(4, 3));
        tileMap.put("duckU", new Tile(4, 4));

        tileMap.put("skeletonD", new Tile(5, 1));
        tileMap.put("skeletonL", new Tile(5, 2));
        tileMap.put("skeletonR", new Tile(5, 3));
        tileMap.put("skeletonU", new Tile(5, 4));

        tileMap.put("soulStealerD", new Tile(16, 1));
        tileMap.put("soulStealerL", new Tile(16, 2));
        tileMap.put("soulStealerR", new Tile(16, 3));
        tileMap.put("soulStealerU", new Tile(16, 4));

        tileMap.put("guardianD", new Tile(6,1));
        tileMap.put("guardianL", new Tile(6,2));
        tileMap.put("guardianR", new Tile(6,3));
        tileMap.put("guardianU", new Tile(6,4));

        tileMap.put("thethingD", new Tile(7,1));
        tileMap.put("thethingL", new Tile(7,2));
        tileMap.put("thethingR", new Tile(7,3));
        tileMap.put("thethingU", new Tile(7,4));

        tileMap.put("cursedKingD", new Tile(8,1));
        tileMap.put("cursedKingL", new Tile(8,2));
        tileMap.put("cursedKingR", new Tile(8,3));
        tileMap.put("cursedKingU", new Tile(8,4));

        tileMap.put("key", new Tile(1, 7));
        tileMap.put("life", new Tile(9, 1));
        tileMap.put("weapon", new Tile(12, 1));

        tileMap.put("sword1", new Tile(12, 1));
        tileMap.put("sword2", new Tile(12, 2));
        tileMap.put("sword3", new Tile(12, 3));
        tileMap.put("sword4", new Tile(12, 4));

        tileMap.put("axe1", new Tile(13, 1));
        tileMap.put("axe2", new Tile(13, 2));
        tileMap.put("axe3", new Tile(13, 3));
        tileMap.put("axe4", new Tile(13, 4));

        tileMap.put("hammer1", new Tile(14, 1));
        tileMap.put("hammer2", new Tile(14, 2));
        tileMap.put("hammer3", new Tile(14, 3));
        tileMap.put("hammer4", new Tile(14, 4));

        tileMap.put("staff1", new Tile(15, 1));
        tileMap.put("staff2", new Tile(15, 2));
        tileMap.put("staff3", new Tile(15, 3));
        tileMap.put("staff4", new Tile(15, 4));

        tileMap.put("healthPoti", new Tile(14, 6));

    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        if (d.getTileName().equals("spikeboss")) {
            context.drawImage(tileset, tile.x, tile.y, tile.w*2, tile.h*2,x * TILE_WIDTH - 24, y * TILE_WIDTH - 24, TILE_WIDTH*2, TILE_WIDTH*2);
        } else {
            context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
        }
    }

    public static void draw3xTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH - 24, y * TILE_WIDTH -24, TILE_WIDTH*2, TILE_WIDTH*2);
    }

    public static void drawParalaxx(GraphicsContext context, int x, int y) {
        int dx = (((x*2) % 720)  -720);
        int dy = (((y*2) % 720)  -720);
        context.drawImage(parallax, 0, 0, 1440, 1440,
                dx, dy, 1440, 1440);
    }
}

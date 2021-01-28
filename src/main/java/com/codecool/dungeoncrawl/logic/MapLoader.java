package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.actors.items.*;
import com.codecool.dungeoncrawl.logic.actors.monsters.Duck;
import com.codecool.dungeoncrawl.logic.actors.monsters.Guardian;
import com.codecool.dungeoncrawl.logic.actors.monsters.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.monsters.TheThing;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int doorCount = 1;

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setCellType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setCellType(CellType.WALL);
                            break;
                        case '.':
                            cell.setCellType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new Skeleton(cell));
                            break;
                        case '@':
                            cell.setCellType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setCellType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'l':
                            cell.setCellType(CellType.FLOOR);
                            new Life(cell);
                            break;
                        case 'w':
                            cell.setCellType(CellType.FLOOR);
                            new Weapon(cell, "Skelie Choppa", 5);
                            break;
                        case 'D':
                            cell.setCellType(CellType.WALL);
                            map.interactables.add(new LockedDoor(cell));
                            break;
                        case 'f':
                            cell.setCellType(CellType.FIRESTAND);
                            break;
                        case 'b':
                            cell.setCellType(CellType.WALL);
                            map.interactables.add(new Barrel(cell, "barrel"));
                            break;
                        case 'd':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new Duck(cell));
                            break;
                        case 'g':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new Guardian(cell));
                            break;
                        case 'h':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new TheThing(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}

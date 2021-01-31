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
                        case '=':
                            cell.setCellType(CellType.WALL);
                            GateOpenableByASwitch gate = new GateOpenableByASwitch(cell, "gateOpenableByASwitch");
                            cell.setItem(gate);
                            map.interactablesCollection.add(gate);
                            map.GateOpenableByASwitchCollection.add(gate);
                            map.switchablesCollection.add(gate);
                            break;
                        case '%':
                            cell.setCellType(CellType.WALL);
                            LeverSwitch leverSwitch = new LeverSwitch(cell);
                            map.interactablesCollection.add(leverSwitch);
                            map.leverSwitchCollection.add(leverSwitch);
                            break;
                        case 'k':
                            cell.setCellType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'l':
                            cell.setCellType(CellType.FLOOR);
                            new Life(cell, 5);
                            break;
                        case 'w':
                            cell.setCellType(CellType.FLOOR);
                            new Weapon(cell, "Skelie Choppa", 5);
                            break;
                        case 'D':
                            cell.setCellType(CellType.WALL);
                            map.interactablesCollection.add(new LockedDoor(cell));
                            break;
                        case 'O':
                            cell.setCellType(CellType.WALL);
                            DoorSealedFromOtherSide doorSealedFromOtherSide = new DoorSealedFromOtherSide(cell);
                            map.interactablesCollection.add(doorSealedFromOtherSide);
                            map.doorsSealedFromOtherSideCollection.add(doorSealedFromOtherSide);
                            break;
                        case 'C':
                            cell.setCellType(CellType.WALL);
                            Chest chest = new Chest(cell, "chest1");
                            map.interactablesCollection.add(chest);
                            map.chestsCollection.add(chest);
                            break;
                        case 'f':
                            cell.setCellType(CellType.FIRESTAND);
                            break;
                        case 'F':
                            cell.setCellType(CellType.WALL);
                            map.interactablesCollection.add(new TorchPuzzle(cell));
                            break;
                        case 't':
                            cell.setCellType(CellType.FLOOR);
                            map.trapsCollection.add(new TrapPlain(cell, "spikeTrapResting", 2, 2));
                            break;
                        case 'T':
                            cell.setCellType(CellType.FLOOR);
                            map.trapsCollection.add(new TrapPlain(cell, "spikeTrapActive", 1, 2));
                            break;
                        case 'B':
                            cell.setCellType(CellType.FLOOR);
                            map.trapBloodyCollection.add(new TrapBloody(cell, "spikeTrapBloodyActive"));
                            break;
                        case 'b':
                            cell.setCellType(CellType.WALL);
                            Breakable breakable = new Breakable(cell, "breakable");
                            int randomNum = RandomGenerator.nextInt(3);
                            if (randomNum == 0) {
                                breakable.setTileName("crate");
                            } else if (randomNum == 1) {
                                breakable.setTileName("crate2");
                            } else {
                                breakable.setTileName("barrel");
                            }
                            map.interactablesCollection.add(breakable);
                            break;
                        case 'd':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new Duck(cell));
                            break;
                        case 'g':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new Guardian(cell));
                            break;
                        case 'e':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new TheThing(cell));
                            break;
                        case 'W':
                            cell.setCellType(CellType.WALL);
                            SuspiciousWall suspiciousWall = new SuspiciousWall(cell);
                            map.interactablesCollection.add(suspiciousWall);
                            map.suspiciousWallsCollection.add(suspiciousWall);
                            map.switchablesCollection.add(suspiciousWall);
                            break;
                        case 'h':
                            cell.setCellType(CellType.FLOOR);
                            HiddenPassage hiddenPassage = new HiddenPassage(cell, "hiddenPassage");
                            map.interactablesCollection.add(hiddenPassage);
                            map.hiddenPassagesCollection.add(hiddenPassage);
                            map.switchablesCollection.add(hiddenPassage);
                            break;
                        case 'H':
                            cell.setCellType(CellType.FLOOR);
                            HiddenItem hiddenItem = new HiddenItem(cell, "hiddenItem");
                            map.interactablesCollection.add(hiddenItem);
                            map.hiddenItemsCollection.add(hiddenItem);
                            map.switchablesCollection.add(hiddenItem);
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

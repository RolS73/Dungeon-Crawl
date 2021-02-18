package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.boss.SpikeBoss;
import com.codecool.dungeoncrawl.logic.actors.boss.SpikeForBosses;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.DartTurret;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.FlameTrap;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.TrapBloody;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.TrapPlain;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.*;
import com.codecool.dungeoncrawl.logic.actors.items.looting.*;
import com.codecool.dungeoncrawl.logic.actors.items.mapdecoration.Firestand;
import com.codecool.dungeoncrawl.logic.actors.monsters.*;
import com.codecool.dungeoncrawl.logic.actors.npcs.FriendlyWhiteWizard;
import com.codecool.dungeoncrawl.logic.actors.npcs.NonPlayerCharacter;

import java.io.InputStream;
import java.util.Scanner;


public class MapLoader {

    public static GameMap loadMap(int mapNumber) {

        InputStream is = null;

        if (mapNumber == 0) {
            is = MapLoader.class.getResourceAsStream("/map.txt");
        } else if (mapNumber == 1) {
            is = MapLoader.class.getResourceAsStream("/map2.txt");
        } else if (mapNumber == 2) {
            is = MapLoader.class.getResourceAsStream("/map3.txt");
        }

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        map.setMapNumber(mapNumber);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case 'a':
                            cell.setCellType(CellType.BOSSFLOOR);
                            map.boss1 = (new SpikeBoss(cell));
                            break;
                        case 'A':
                            cell.setCellType(CellType.BOSSFLOOR);
                            map.spikeForBossesList.add(new SpikeForBosses(cell));
                            break;
                        case '&':
                            cell.setCellType(CellType.BOSSFLOOR);
                            map.monsters.add(new CursedKing(cell));
                            break;
                        case 'n':
                            cell.setCellType(CellType.STUNNER);
                            break;
                        case 'N':
                            cell.setCellType(CellType.BOSSFLOOR);
                            break;
                        case ' ':
                            cell.setCellType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setCellType(CellType.WALL);
                            break;
                        case ',':
                            cell.setCellType(CellType.FLOORNOMONSTER);
                            break;
                        case '.':
                            cell.setCellType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new Skeleton(cell));
                            break;
                        case '$':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new SoulStealer(cell));
                            break;
                        case '@':
                            cell.setCellType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case '=':
                            cell.setCellType(CellType.OBJECT);
                            GateOpenableByASwitch gate = new GateOpenableByASwitch(cell, "gateOpenableByASwitch");
                            cell.setItem(gate);
                            map.interactablesCollection.add(gate);
                            map.GateOpenableByASwitchCollection.add(gate);
                            map.switchablesCollection.add(gate);
                            break;
                        case '%':
                            cell.setCellType(CellType.OBJECT);
                            LeverSwitch leverSwitch = new LeverSwitch(cell);
                            map.interactablesCollection.add(leverSwitch);
                            map.leverSwitchCollection.add(leverSwitch);
                            break;
                        case '*':
                            cell.setCellType(CellType.WALL);
                            SecretPassage secretPassage = new SecretPassage(cell, 70, 11);
                            map.interactablesCollection.add(secretPassage);
                            map.secretPassagesCollection.add(secretPassage);
                            break;
                        case 'j':
                            cell.setCellType(CellType.FLOOR);
                            MapChangePassage mapPassageDOWN = new MapChangePassage(cell, "MapTraversalPassage", Direction.DOWN);
                            map.interactablesCollection.add(mapPassageDOWN);
                            break;
                        case 'J':
                            cell.setCellType(CellType.FLOOR);
                            MapChangePassage mapPassageUP = new MapChangePassage(cell, "MapTraversalPassage", Direction.UP);
                            if (mapNumber == 1) {
                                mapPassageUP.setAnotherTileName("stairwayUpMap2");
                            }
                            map.interactablesCollection.add(mapPassageUP);
                            break;
                        case 'P':
                            cell.setCellType(CellType.FLOOR);
                            Passage passage = new Passage(cell, "Passage");
                            map.interactablesCollection.add(passage);
                            map.mapQuickTravelPassages.add(passage);
                            break;
                        case 'k':
                            cell.setCellType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'l':
                            cell.setCellType(CellType.FLOOR);
                            new Life(cell, 5);
                            break;
                        case 'L':
                            cell.setCellType(CellType.FLOOR);
                            Item placedItem = new Life(cell, 2);
                            //cell.setItem(placedItem);
                            map.placedItemsCollection.add(placedItem);
                            break;
                        /*case 'w':
                            cell.setCellType(CellType.FLOOR);
                            new Weapon(cell, "Skelie Choppa", 5);
                            break;*/
                        case 'D':
                            cell.setCellType(CellType.OBJECT);
                            LockedDoor lockedDoor = new LockedDoor(cell);
                            map.interactablesCollection.add(lockedDoor);
                            map.lockedDoorsCollection.add(lockedDoor);
                            break;
                        case 'O':
                            cell.setCellType(CellType.OBJECT);
                            DoorSealedFromOtherSide doorSealedFromOtherSide = new DoorSealedFromOtherSide(cell);
                            map.interactablesCollection.add(doorSealedFromOtherSide);
                            map.doorsSealedFromOtherSideCollection.add(doorSealedFromOtherSide);
                            break;
                        case 'C':
                            cell.setCellType(CellType.OBJECT);
                            Chest chest = new Chest(cell, "chest1");
                            map.interactablesCollection.add(chest);
                            map.chestsCollection.add(chest);
                            break;
                        case 'f':
                            cell.setCellType(CellType.OBJECT);
                            cell.setItem(new Firestand(cell, "firestand"));
                            break;
                        case 'F':
                            cell.setCellType(CellType.OBJECT);
                            TorchPuzzle torchPuzzle = new TorchPuzzle(cell);
                            map.interactablesCollection.add(torchPuzzle);
                            map.mapStateSwitchers.add(torchPuzzle);
                            break;
                        case 't':
                            cell.setCellType(CellType.FLOOR);
                            map.endlessCycleTraps.add(new TrapPlain(cell, "spikeTrapResting", 2, 3));
                            break;
                        case 'T':
                            cell.setCellType(CellType.FLOOR);
                            map.endlessCycleTraps.add(new TrapPlain(cell, "spikeTrapActive", 1, 3));
                            break;
                        case '+':
                            cell.setCellType(CellType.OBJECT);
                            map.endlessCycleTraps.add(new DartTurret(cell, "DartTurret", 6, 4, Direction.UP));
                            break;
                        case '!':
                            cell.setCellType(CellType.OBJECT);
                            map.endlessCycleTraps.add(new FlameTrap(cell, "FlameTurret", 8, 3, 5, Direction.LEFT));
                            break;
                        case 'M':
                            cell.setCellType(CellType.FLOOR);
                            FriendlyWhiteWizard friendlyWhiteWizard = new FriendlyWhiteWizard(cell);
                            map.actorsCollection.add(friendlyWhiteWizard);
                            map.interactablesCollection.add(friendlyWhiteWizard);
                            break;
                        /*case '§':
                            cell.setCellType(CellType.FIRESTAND);
                            break;*/
                        case 'm':
                            cell.setCellType(CellType.FLOOR);
                            NonPlayerCharacter dwarf = new NonPlayerCharacter(cell);
                            map.actorsCollection.add(dwarf);
                            map.interactablesCollection.add(dwarf);
                            break;
                        case 'B':
                            cell.setCellType(CellType.FLOOR);
                            new TrapBloody(cell, "spikeTrapBloodyActive", 5);
                            //map.trapBloodyCollection.add(new TrapBloody(cell, "spikeTrapBloodyActive", 5));
                            break;
                        case 'b':
                            cell.setCellType(CellType.OBJECT);
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
                        case 'w':
                            cell.setCellType(CellType.WALL);
                            DoorOpenableByASwitch verySuspiciousWall = new DoorOpenableByASwitch(cell, "sealedWall");
                            map.interactablesCollection.add(verySuspiciousWall);
                            map.doorsOpenableBySwitches.add(verySuspiciousWall);
                            map.switchablesCollection.add(verySuspiciousWall);
                            break;
                        case 'h':
                            cell.setCellType(CellType.EMPTY);
                            HiddenPassage hiddenPassage = new HiddenPassage(cell, "hiddenPassage");
                            map.interactablesCollection.add(hiddenPassage);
                            map.hiddenPassagesCollection.add(hiddenPassage);
                            map.switchablesCollection.add(hiddenPassage);
                            break;
                        case 'H':
                            cell.setCellType(CellType.EMPTY);
                            HiddenItem hiddenItem = new HiddenItem(cell, "hiddenItem");
                            map.interactablesCollection.add(hiddenItem);
                            map.hiddenItemsCollection.add(hiddenItem);
                            map.switchablesCollection.add(hiddenItem);
                            break;
                        case 'S':
                            cell.setCellType(CellType.FLOOR);
                            HiddenEnemySpawner enemySpawner = new HiddenEnemySpawner(cell, "Monster");
                            map.interactablesCollection.add(enemySpawner);
                            map.hiddenEnemySpawnersCollection.add(enemySpawner);
                            map.switchablesCollection.add(enemySpawner);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    /*public void setUpCellType(Cell cell, CellType celltype) {
        cell.setCellType(celltype);
        if (celltype.equals(CellType.FLOOR) && Main.getCurrentMapIndex() == 1) {
            cell.setNewTypeTileName("bossfloor");
        }
    }*/

}

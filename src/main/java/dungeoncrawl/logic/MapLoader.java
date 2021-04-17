package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.boss.SpikeBoss;
import dungeoncrawl.logic.actors.boss.SpikeForBosses;
import dungeoncrawl.logic.actors.items.enviromentalHazards.DartTurret;
import dungeoncrawl.logic.actors.items.enviromentalHazards.FlameTrap;
import dungeoncrawl.logic.actors.items.enviromentalHazards.TrapBloody;
import dungeoncrawl.logic.actors.items.enviromentalHazards.TrapPlain;
import dungeoncrawl.logic.actors.items.interactablilty.*;
import dungeoncrawl.logic.actors.items.looting.*;
import dungeoncrawl.logic.actors.items.looting.loottable.EveryItem;
import dungeoncrawl.logic.actors.items.mapdecoration.Firestand;
import dungeoncrawl.logic.actors.monsters.*;
import dungeoncrawl.logic.actors.npcs.FriendlyWhiteWizard;
import dungeoncrawl.logic.actors.npcs.NonPlayerCharacter;

import java.io.InputStream;
import java.util.Scanner;


public class MapLoader {

    public static GameMap loadMap(int mapNumber) {

        InputStream is = null;
        InputStream is2 = null;
        InputStream is3 = null;

        if (mapNumber == 0) {
            is = MapLoader.class.getResourceAsStream("/map.txt");
            is2 = MapLoader.class.getResourceAsStream("/mapSetup.txt");
            is3 = MapLoader.class.getResourceAsStream("/mapSetupArguments.txt");
        } else if (mapNumber == 1) {
            is = MapLoader.class.getResourceAsStream("/map2.txt");
            is2 = MapLoader.class.getResourceAsStream("/map2Setup.txt");
            is3 = MapLoader.class.getResourceAsStream("/map2SetupArguments.txt");
        } else if (mapNumber == 2) {
            is = MapLoader.class.getResourceAsStream("/map3.txt");
            is2 = MapLoader.class.getResourceAsStream("/map3Setup.txt");
            is3 = MapLoader.class.getResourceAsStream("/map3SetupArguments.txt");
        }

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        Scanner scannerForSetup = new Scanner(is2);
        Scanner scannerForSetupArguments = new Scanner(is3);

        scanner.nextLine(); // empty line
        scannerForSetup.nextLine();
        scannerForSetupArguments.nextLine();

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        map.setMapNumber(mapNumber);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            String lineForSetup = scannerForSetup.nextLine();
            String lineForSetupArguments = scannerForSetupArguments.nextLine();
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
                        case 'p':
                            cell.setCellType(CellType.FLOOR);
                            map.monsters.add(new GoblinPig(cell));
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
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
                            map.interactablesCollection.add(gate);
                            map.GateOpenableByASwitchCollection.add(gate);
                            map.switchablesCollection.add(gate);
                            break;
                        case '%':
                            cell.setCellType(CellType.OBJECT);
                            LeverSwitch leverSwitch = new LeverSwitch(cell);
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
                            map.interactablesCollection.add(leverSwitch);
                            map.leverSwitchCollection.add(leverSwitch);
                            break;
                        case '*':
                            cell.setCellType(CellType.WALL);
                            SecretPassage secretPassage = new SecretPassage(cell, 70, 11);
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
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
                                mapPassageUP.setTileName("stairwayUpMap2");
                            }
                            map.interactablesCollection.add(mapPassageUP);
                            break;
                        case 'P':
                            cell.setCellType(CellType.FLOOR);
                            Passage passage = new Passage(cell, "Passage");
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
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
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
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
                            doorSealedFromOtherSide.setOpenableFromWhatDirection((Direction) getArgumentForCellFromScannerLine(lineForSetupArguments, x));
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
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
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
                            map.endlessCycleTraps.add(new DartTurret(cell, "DartTurret", 6, 4, (Direction) getArgumentForCellFromScannerLine(lineForSetupArguments, x)));
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
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
                            map.interactablesCollection.add(suspiciousWall);
                            map.suspiciousWallsCollection.add(suspiciousWall);
                            map.switchablesCollection.add(suspiciousWall);
                            break;
                        case 'w':
                            cell.setCellType(CellType.WALL);
                            DoorOpenableByASwitch verySuspiciousWall = new DoorOpenableByASwitch(cell, "sealedWall");
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
                            map.interactablesCollection.add(verySuspiciousWall);
                            map.doorsOpenableBySwitches.add(verySuspiciousWall);
                            map.switchablesCollection.add(verySuspiciousWall);
                            break;
                        case 'h':
                            cell.setCellType(CellType.EMPTY);
                            HiddenPassage hiddenPassage = new HiddenPassage(cell, "hiddenPassage");
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
                            map.interactablesCollection.add(hiddenPassage);
                            map.hiddenPassagesCollection.add(hiddenPassage);
                            map.switchablesCollection.add(hiddenPassage);
                            break;
                        case 'H':
                            cell.setCellType(CellType.EMPTY);
                            HiddenItem hiddenItem = new HiddenItem(cell, "hiddenItem");
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
                            map.interactablesCollection.add(hiddenItem);
                            map.hiddenItemsCollection.add(hiddenItem);
                            map.switchablesCollection.add(hiddenItem);
                            break;
                        case 'S':
                            cell.setCellType(CellType.FLOOR);
                            HiddenEnemySpawner enemySpawner = new HiddenEnemySpawner(cell, "Monster");
                            setUpCellSettings(cell, lineForSetup, lineForSetupArguments, x);
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
        setUpTeleportPairs(map);
        return map;
    }

    private static void setUpTeleportPairs(GameMap map) {
        for (int i = 0; i < map.getMapQuickTravelPassages().size(); i++) {
            for (int j = 0; j < map.getMapQuickTravelPassages().size(); j++) {
                Passage teleporter1 = map.getMapQuickTravelPassages().get(i);
                Passage teleporter2 = map.getMapQuickTravelPassages().get(j);
                if (teleporter1.getPairIdentifier()
                        .equals(teleporter2.getPairIdentifier())
                        && teleporter1.getCoordinateX() != teleporter2.getCoordinateX() &&
                teleporter1.getCoordinateY() != teleporter2.getCoordinateX() && !(teleporter1.isPaired()) && !(teleporter2.isPaired())) {
                    teleporter1.assignDestinationCoordinatesOfInput(teleporter2);
                    teleporter2.assignDestinationCoordinatesOfInput(teleporter1);
                }
            }
        }
    }

    public static void setUpCellSettings(Cell cell, String lineForSetup, String lineForSetupArguments, int x) {
        char targetChar = lineForSetup.charAt(x);
        switch (targetChar) {
            case 'D':
                if (cell.getActor() != null) {
                    cell.getActor().setDirection((Direction) getArgumentForCellFromScannerLine(lineForSetupArguments, x));
                }
                break;
            case 'g':
                if (cell.getItem() instanceof Switch) {
                    ((Switch) cell.getItem()).setGroupName("Group0" + lineForSetupArguments.charAt(x));
                }
                break;
            case 'G':
                if (cell.getItem() instanceof Switch) {
                    ((Switch) cell.getItem()).setGroupName("Group1" + lineForSetupArguments.charAt(x));
                }
                break;
            case 'C':
                if (cell.getItem() instanceof Chest) {
                    ((Chest) cell.getItem()).setAnotherTilename("chest" + lineForSetupArguments.charAt(x));
                }
                break;
            case 'i':
                if (lineForSetupArguments.charAt(x) == '2') {
                    cell.setItem(EveryItem.getInstance().getItemRareLoot().get(2));
                } else if (lineForSetupArguments.charAt(x) == '3') {
                    cell.setItem(EveryItem.getInstance().getItemRareLoot().get(3));
                }
                break;
            case 'p':
                if (cell.getItem() instanceof TeleportOnCurrentMap) {
                    ((TeleportOnCurrentMap) cell.getItem()).setPairIdentifier("Passage" + lineForSetupArguments.charAt(x));
                }
                break;
            default:
                //throw new RuntimeException("Unrecognized character: '" + lineForSetup.charAt(x) + "'");
        }
    }

    public static Object getArgumentForCellFromScannerLine(String lineForSetupArguments, int x) {
        switch (lineForSetupArguments.charAt(x)) {
            case 'd':
                return Direction.DOWN;
            case 'u':
                return Direction.UP;
            case 'l':
                return Direction.LEFT;
            case 'r':
                return Direction.RIGHT;
            default:
                //System.out.println("Unrecognized char for argument, setting default position");
                return Direction.DOWN;
        }
    }
}

    /*public void setUpCellType(Cell cell, CellType celltype) {
        cell.setCellType(celltype);
        if (celltype.equals(CellType.FLOOR) && Main.getCurrentMapIndex() == 1) {
            cell.setNewTypeTileName("bossfloor");
        }
    }*/


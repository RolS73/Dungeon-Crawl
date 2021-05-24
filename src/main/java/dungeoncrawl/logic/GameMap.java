package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.boss.SpikeBoss;
import dungeoncrawl.logic.actors.boss.SpikeForBosses;
import dungeoncrawl.logic.actors.items.enviromentalHazards.ProjectileCycle;
import dungeoncrawl.logic.actors.items.enviromentalHazards.TrapCycle;
import dungeoncrawl.logic.actors.items.looting.Chest;
import dungeoncrawl.logic.actors.items.looting.HiddenItem;
import dungeoncrawl.logic.actors.items.looting.Item;
import dungeoncrawl.logic.actors.monsters.HiddenEnemySpawner;
import dungeoncrawl.logic.actors.monsters.Monster;
import dungeoncrawl.logic.actors.npcs.NonPlayerCharacter;
import dungeoncrawl.logic.actors.items.interactablilty.*;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class GameMap implements Serializable {
    private int width;
    private int height;
    private Cell[][] cells;
    private int mapNumber;

    Player player;


    SpikeBoss boss1;
    List<SpikeForBosses> spikeForBossesList = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    List<InteractiveObject> interactablesCollection = new ArrayList<>();
    List<DoorSealedFromOtherSide> doorsSealedFromOtherSideCollection = new ArrayList<>();
    List<Chest> chestsCollection = new ArrayList<>();
    List<TrapCycle> endlessCycleTraps = new ArrayList<>();
    List<ProjectileCycle> projectilesCollection = new ArrayList<>();
    //List<TrapBloody> trapBloodyCollection = new ArrayList<>();
    //List<DartTurret> dartTurretsCollection = new ArrayList<>();
    //List<DartTurretProjectile> dartTurretProjectilesCollection = new ArrayList<>();
    List<LeverSwitch> leverSwitchCollection = new ArrayList<>();
    List<GateOpenableByASwitch> GateOpenableByASwitchCollection = new ArrayList<>();
    List<Switch> switchablesCollection = new ArrayList<>();
    List<SuspiciousWall> suspiciousWallsCollection = new ArrayList<>();
    List<HiddenPassage> hiddenPassagesCollection = new ArrayList<>();
    List<HiddenItem> hiddenItemsCollection = new ArrayList<>();
    List<HiddenEnemySpawner> hiddenEnemySpawnersCollection = new ArrayList<>();
    List<Item> placedItemsCollection = new ArrayList<>();
    List<Switch> mapStateSwitchers = new ArrayList<>();
    List<TeleportOnCurrentMap> mapQuickTravelPassages = new ArrayList<>();
    List<SecretPassage> secretPassagesCollection = new ArrayList<>();
    List<NonPlayerCharacter> actorsCollection = new ArrayList<>();
    List<LockedDoor> lockedDoorsCollection = new ArrayList<>();
    List<DoorOpenableByASwitch> doorsOpenableBySwitches = new ArrayList<>();



    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public List<ProjectileCycle> getProjectilesCollection() {
        return projectilesCollection;
    }

    /*public List<DartTurret> getDartTurretsCollection() {
        return dartTurretsCollection;
    }*/

   /* public List<DartTurretProjectile> getDartTurretProjectilesCollection() {
        return dartTurretProjectilesCollection;
    }*/

    public int getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    public List<LockedDoor> getLockedDoorsCollection() {
        return lockedDoorsCollection;
    }

    public List<DoorOpenableByASwitch> getDoorsOpenableBySwitches() {
        return doorsOpenableBySwitches;
    }

    public List<Switch> getMapStateSwitchers() {
        return mapStateSwitchers;
    }

    public List<TeleportOnCurrentMap> getMapQuickTravelPassages() {
        return mapQuickTravelPassages;
    }

    public List<HiddenPassage> getHiddenPassagesCollection() {
        return hiddenPassagesCollection;
    }

    public List<HiddenItem> getHiddenItemsCollection() {
        return hiddenItemsCollection;
    }

    public Cell getCell(int x, int y) {
        return (x<0 || x >= width || y<0 || y >= height) ? null : cells[x][y];
        // return cells[x][y]; eredeti
    }

    public List<HiddenEnemySpawner> getHiddenEnemySpawnersCollection() {
        return hiddenEnemySpawnersCollection;
    }

    public List<SuspiciousWall> getSuspiciousWallsCollection() {
        return suspiciousWallsCollection;
    }

    public List<DoorSealedFromOtherSide> getDoorsSealedFromOtherSideArray() {
        return doorsSealedFromOtherSideCollection;
    }

    public List<GateOpenableByASwitch> getGateOpenableByASwitchCollection() {
        return GateOpenableByASwitchCollection;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Item> getPlacedItemsCollection() {
        return placedItemsCollection;
    }

    public List<Chest> getChestsCollection() {
        return chestsCollection;
    }

    public List<TrapCycle> getEndlessCycleTraps() {
        return endlessCycleTraps;
    }

    /*public List<TrapBloody> getTrapBloodyCollection() {
        return trapBloodyCollection;
    }*/

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<LeverSwitch> getLeverSwitchCollection() {
        return leverSwitchCollection;
    }

    public List<InteractiveObject> getInteractablesArray() {
        return interactablesCollection;
    }

    public List<Switch> getSwitchablesCollection() {
        return switchablesCollection;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SpikeBoss getBoss1() {
        return boss1;
    }
}

package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.items.*;
import com.codecool.dungeoncrawl.logic.actors.monsters.HiddenEnemySpawner;
import com.codecool.dungeoncrawl.logic.actors.monsters.Monster;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    Player player;

    List<Monster> monsters = new ArrayList<>();
    List<InteractiveObject> interactablesCollection = new ArrayList<>();
    List<DoorSealedFromOtherSide> doorsSealedFromOtherSideCollection = new ArrayList<>();
    List<Chest> chestsCollection = new ArrayList<>();
    List<TrapPlain> trapsCollection = new ArrayList<>();
    List<TrapBloody> trapBloodyCollection = new ArrayList<>();
    List<LeverSwitch> leverSwitchCollection = new ArrayList<>();
    List<GateOpenableByASwitch> GateOpenableByASwitchCollection = new ArrayList<>();
    List<Switch> switchablesCollection = new ArrayList<>();
    List<SuspiciousWall> suspiciousWallsCollection = new ArrayList<>();
    List<HiddenPassage> hiddenPassagesCollection = new ArrayList<>();
    List<HiddenItem> hiddenItemsCollection = new ArrayList<>();
    List<HiddenEnemySpawner> hiddenEnemySpawnersCollection = new ArrayList<>();
    List<Item> placedItemsCollection = new ArrayList<>();


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

    public List<TrapPlain> getTrapsCollection() {
        return trapsCollection;
    }

    public List<TrapBloody> getTrapBloodyCollection() {
        return trapBloodyCollection;
    }

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
}

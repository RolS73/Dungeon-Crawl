package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actors.items.InteractiveObject;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    Player player;

    List<Monster> monsters = new ArrayList<>();
    List<InteractiveObject> interactables = new ArrayList<>();


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

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<InteractiveObject> getInteractables() {
        return interactables;
    }

    public Cell getCell(int x, int y) {
        return (x<0 || x >= width || y<0 || y >= height) ? null : cells[x][y];
        // return cells[x][y]; eredeti
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

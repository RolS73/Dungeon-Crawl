package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;
import java.io.Serializable;

public class Cell implements Drawable, Serializable {
    private CellType type;
    private Actor actor;
    private Actor item;
    private transient GameMap gameMap;
    private int x, y;
    private boolean isTypeTileNameHijacked;
    private String newTypeTileName;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void setMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public CellType getCellType() {
        return type;
    }

    public void setCellType(CellType type) {
        this.type = type;
        if (type.equals(CellType.FLOOR) &&  this.gameMap.getMapNumber() == 1) {
            this.setNewTypeTileName("bossfloor");
        } else if (type.equals(CellType.OBJECT) && this.gameMap.getMapNumber() == 1) {
            this.setNewTypeTileName("bossfloor");
        } else if (type.equals(CellType.WALL) &&  this.gameMap.getMapNumber() == 1) {
            this.setNewTypeTileName("stairwayDown");
        }
    }

   /* public void setCellType(CellType type, int mapNumber) {
        this.type = type;
        if (type.equals(CellType.FLOOR) &&  gameMap.getMapNumber() == 1) {
            this.setNewTypeTileName("bossfloor");
        } else if (type.equals(CellType.OBJECT) &&  gameMap.getMapNumber() == 1) {
            this.setNewTypeTileName("bossfloor");
        }
    }*/

    public void setActor(Actor actor) {
        if (actor instanceof Item) {
            this.item = actor;
        } else {
            this.actor = actor;
        }
    }

    public Actor getActor() {
        return actor;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public Cell getQagbmpoibmCell(int xx, int yy){ return gameMap.getCell(xx,yy);}

    @Override
    public String getTileName() {
        if (isTypeTileNameHijacked) {
            return newTypeTileName;
        } else {
            return type.getTileName();
        }
    }

    public void setNewTypeTileName(String newName) {
        this.isTypeTileNameHijacked = true;
        this.newTypeTileName = newName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Actor getItem() {
        return item;
    }

    public void setItem(Actor item) {
        this.item = item;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

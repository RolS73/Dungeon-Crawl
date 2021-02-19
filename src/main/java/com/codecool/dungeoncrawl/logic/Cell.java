package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Actor item;
    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getCellType() {
        return type;
    }

    public void setCellType(CellType type) {
        this.type = type;
    }

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
        if(gameMap.getCell(x + dx, y + dy)==null){
            return gameMap.getCell(x,y);
        }
        return gameMap.getCell(x + dx, y + dy);
    }

    public Cell getQagbmpoibmCell(int xx, int yy){ return gameMap.getCell(xx,yy);}

    @Override
    public String getTileName() {
        return type.getTileName();
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

package com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.actors.Sounds;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

import java.util.ArrayList;
import java.util.List;

public class FlameTrapProjectile extends Item implements EnvironmentalDamage, ProjectileCycle {

    private String anotherTileName = "spikeTrapBloodyActive";
    private final int environmentalDamageValue;
    private boolean isHit = false;
    private int duration = 4;
    private int range;
    private List<Cell> activeCells = new ArrayList<>();

    public FlameTrapProjectile(Cell cell, String name,int range, int damageValue, Direction projectileDirection) {
        super(cell, name);
        environmentalDamageValue = damageValue;
        this.range = range;
        activeCells.add(this.getCell());

        switch (projectileDirection) {
            case UP:
                for (int i = 1; i < range; i++) {
                    if (!(this.getCell().getNeighbor(0, -i).getCellType().equals(CellType.WALL))
                            && !(this.getCell().getNeighbor(0, -i).getCellType().equals(CellType.OBJECT))) {
                        activeCells.add(this.getCell().getNeighbor(0, -i));
                    } else {
                        break;
                    }
                }
                break;
            case DOWN:
                for (int i = 1; i < range; i++) {
                    if (!(this.getCell().getNeighbor(0, i).getCellType().equals(CellType.WALL))
                            && !(this.getCell().getNeighbor(0, i).getCellType().equals(CellType.OBJECT))) {
                        activeCells.add(this.getCell().getNeighbor(0, i));
                    } else {
                        break;
                    }
                }
                break;
            case LEFT:
                for (int i = 1; i < range; i++) {
                    if (!(this.getCell().getNeighbor(-i, 0).getCellType().equals(CellType.WALL))
                            && !(this.getCell().getNeighbor(-i, 0).getCellType().equals(CellType.OBJECT))) {
                        activeCells.add(this.getCell().getNeighbor(-i, 0));
                    } else {
                        break;
                    }
                }
                break;
            case RIGHT:
                for (int i = 1; i < range; i++) {
                    if (!(this.getCell().getNeighbor(i, 0).getCellType().equals(CellType.WALL))
                            && !(this.getCell().getNeighbor(i, 0).getCellType().equals(CellType.OBJECT))) {
                        activeCells.add(this.getCell().getNeighbor(i, 0));
                    } else {
                        break;
                    }
                }
        }
        for (Cell activeCell : activeCells) {
            activeCell.setItem(this);
        }
    }

    public void projectileCycle() {
        if (duration == 0) {
            for (Cell activeCell : activeCells) {
                activeCell.setItem(null);
            }
            isHit = true;
        } else {
            duration--;
            System.out.println(duration);
        }
    }

    public boolean isHit() {
        return isHit;
    }

    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String newName) {
        this.anotherTileName = newName;
    }

    @Override
    public boolean isEnvironmentalDamageActive() {
        return true;
    }

    @Override
    public void playDamageSound() {
        Sounds.playSound("DSdamage1");
    }

    @Override
    public int getAttackPower() {
        return environmentalDamageValue;
    }

}

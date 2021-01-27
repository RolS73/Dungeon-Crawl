package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int attackPower = 1;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {

        Cell nextCell = cell.getNeighbor(dx, dy); // eredeti


        if (nextCell == null) {
            return;
        }
        if (nextCell.getActor() != null) {
            nextCell.getActor().health = nextCell.getActor().health - attackPower;
            this.health = this.health - nextCell.getActor().getAttackPower();


            if (nextCell.getActor().health < 1) {
                nextCell.setActor(null);
                Sounds.playSound("Damage3");
            } else {
                Sounds.playSound("Sword1");
            }
        }

        if (nextCell.getType() == CellType.FLOOR && nextCell.getActor() == null) {
            Sounds.playSound("Move5b");

            // eredeti
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            // eredeti

        }

    }

    public void monsterMove(int x, int y){}

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int newAttackPower) {
        this.attackPower = newAttackPower;
    }

    public void raiseAttackPower(int attackPowerGrowth) {
        this.attackPower = this.attackPower + attackPowerGrowth;
    }
}

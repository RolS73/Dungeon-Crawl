package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.boss.SpikeForBosses;
import com.codecool.dungeoncrawl.logic.actors.items.Breakable;
import com.codecool.dungeoncrawl.logic.actors.npcs.NonPlayerCharacter;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int attackPower = 1;
    private String tileName = getTileName();
    private boolean thisABossFight = false;
    private boolean wallCheatOn = false;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public Actor() {
    }

    public void move(int dx, int dy) {

        Cell nextCell = cell.getNeighbor(dx, dy); // eredeti


        if (nextCell == null) {
            return;
        }
        if (nextCell.getActor() instanceof SpikeForBosses){
            return;
        }
        if (nextCell.getActor() != null && !(nextCell.getActor() instanceof NonPlayerCharacter)) {
            if (nextCell.getActor() instanceof Player) {
                damageCalculation(nextCell);
            } else {
                nextCell.getActor().health = nextCell.getActor().health - attackPower;
            }

//            this.health = this.health - nextCell.getActor().getAttackPower();

            if (nextCell.getActor().health < 1 ) {
                Sounds.playSound("kill1");
                nextCell.getActor().playDeathSound();
                nextCell.setActor(null);
                return;
            } else {
                Sounds.playSound("Sword1");
            }
        }

        if (wallCheatOn && nextCell.getActor() == null) {
            Sounds.playSound("Move5b");
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            if(this.getCell().getCellType() == CellType.BOSSFLOOR){
                thisABossFight = true;
            }
        } else if ((nextCell.getCellType() == CellType.BOSSFLOOR || nextCell.getCellType() == CellType.STUNNER ||
                nextCell.getCellType() == CellType.FLOOR) && nextCell.getActor() == null) {
            Sounds.playSound("Move5b");

            // eredeti
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            // eredeti

           if(this.getCell().getCellType() == CellType.BOSSFLOOR){
               thisABossFight = true;
           }

        }

        if (nextCell.getItem() instanceof Breakable) {
            ((Breakable) nextCell.getItem()).interact();
        }

    }

    public boolean isWallCheatOn() {
        return wallCheatOn;
    }

    public void setWallCheatOn(boolean wallCheatOn) {
        this.wallCheatOn = wallCheatOn;
    }

    protected void damageCalculation(Cell nextCell) {
        if (attackPower - ((Player) nextCell.getActor()).getArmor() <= 0) {
            nextCell.getActor().health -= 1;
        } else {
            nextCell.getActor().health = nextCell.getActor().health - (attackPower - ((Player) nextCell.getActor()).getArmor());
        }
    }

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

    public boolean isThisABossFight() {
        return thisABossFight;
    }

    public void  teleport(int x, int y){
        Cell nextCell = this.getCell().getQagbmpoibmCell(x,y);
        nextCell.setActor(this);
        this.getCell().setActor(null);
        this.setCell(nextCell);
    }

    public void playDeathSound() {}
}

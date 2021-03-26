package com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class FlameTrap extends Item implements EnvironmentalDamage, TrapCycle {

    private int currentCooldownCount;
    private final int cooldownMax;
    private String anotherTileName = "dartTurret";
    private Direction projectileDirection;
    private int projectileDamage;
    private Cell projectileStartCell;
    private int range;


    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String newName) {
        this.anotherTileName = newName;
    }

    public FlameTrap(Cell cell, String name, int cooldown, int range, int damage, Direction projectileDirection) {
        super(cell, name);
        currentCooldownCount = cooldown;
        cooldownMax = cooldown;
        this.projectileDirection = projectileDirection;
        this.projectileDamage = damage;
        this.range = range;

        switch (projectileDirection) {
            case UP:
                 this.projectileStartCell = this.getCell().getNeighbor(0,-1);
                break;
            case DOWN:
                this.projectileStartCell = this.getCell().getNeighbor(0,1);
                break;
            case LEFT:
                this.projectileStartCell = this.getCell().getNeighbor(-1,0);
                break;
            case RIGHT:
                this.projectileStartCell = this.getCell().getNeighbor(1,0);
                break;
        }
    }

    public void trapCycle() {
        //System.out.println(currentCooldownCount);
        if (currentCooldownCount > 0) {
            currentCooldownCount--;
        } else if (currentCooldownCount == 0) {
            //System.out.println("Shooting");
            if (!(projectileStartCell.getCellType().equals(CellType.WALL)) && !(projectileStartCell.getCellType().equals(CellType.OBJECT))) {
                FlameTrapProjectile flames = new FlameTrapProjectile(projectileStartCell, "Flame", range, projectileDamage, projectileDirection);
                Main.cheatingMapGetter().getProjectilesCollection().add(flames);
            }
            currentCooldownCount = cooldownMax;
        }
    }

    @Override
    public boolean isEnvironmentalDamageActive() {
        return false;
    }

    @Override
    public void playDamageSound() {

    }
}

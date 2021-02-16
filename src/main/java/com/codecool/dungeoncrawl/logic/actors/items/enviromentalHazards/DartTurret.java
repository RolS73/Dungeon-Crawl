package com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class DartTurret extends Item implements TrapCycle {

    private int currentCooldownCount;
    private final int cooldownMax;
    private String anotherTileName = "dartTurret";
    private Direction projectileDirection;
    private int projectileDamage;
    private Cell projectileSpawnCell;


    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String newName) {
        this.anotherTileName = newName;
    }

    public DartTurret(Cell cell, String name, int cooldown, int damage, Direction projectileDirection) {
        super(cell, name);
        currentCooldownCount = cooldown;
        cooldownMax = cooldown;
        this.projectileDirection = projectileDirection;
        this.projectileDamage = damage;

            switch (projectileDirection) {
                case UP:
                    this.projectileSpawnCell = this.getCell().getNeighbor(0,-1);
                    break;
                case DOWN:
                    this.projectileSpawnCell = this.getCell().getNeighbor(0,1);
                    break;
                case LEFT:
                    this.projectileSpawnCell = this.getCell().getNeighbor(-1,0);
                    break;
                case RIGHT:
                    this.projectileSpawnCell = this.getCell().getNeighbor(1,0);
            }
    }

    public void trapCycle() {
        //System.out.println(currentCooldownCount);
        if (currentCooldownCount > 0) {
            currentCooldownCount--;
        } else if (currentCooldownCount == 0) {
            //System.out.println("Shooting");
            DartTurretProjectile dartShot = new DartTurretProjectile(projectileSpawnCell, "DartShot", projectileDamage, projectileDirection);
            Main.cheatingMapGetter().getProjectilesCollection().add(dartShot);
            currentCooldownCount = cooldownMax;
        }
    }
}

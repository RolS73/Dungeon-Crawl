package com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sounds;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class DartTurretProjectile extends Item implements EnvironmentalDamage, ProjectileCycle {

    private String anotherTileName = "key";
    private final int environmentalDamageValue;
    private int moveCoordinateX;
    private int moveCoordinateY;
    private boolean isHit = false;

    public DartTurretProjectile(Cell cell, String name, int damageValue, Direction projectileDirection) {
        super(cell, name);
        environmentalDamageValue = damageValue;
        generateProjectileMovementCoordinates(projectileDirection);
    }

    private void generateProjectileMovementCoordinates(Direction projectileDirection) {
        switch (projectileDirection) {
            case UP:
                this.moveCoordinateX = 0;
                this.moveCoordinateY = -1;
                break;
            case DOWN:
                this.moveCoordinateX = 0;
                this.moveCoordinateY = 1;
                break;
            case LEFT:
                this.moveCoordinateX = -1;
                this.moveCoordinateY = 0;
                break;
            case RIGHT:
                this.moveCoordinateX = 1;
                this.moveCoordinateY = 0;
        }
    }

    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);

        if (nextCell.getActor() instanceof Player) {
            damageCalculation(nextCell);
            this.getCell().setItem(null);
            isHit = true;
        } else if ((nextCell.getCellType() == CellType.FLOOR || nextCell.getCellType() == CellType.FLOORNOMONSTER) && nextCell.getActor() == null) {
            this.getCell().setItem(null);
            nextCell.setItem(this);
            super.setCell(nextCell);
        } else if (nextCell.getCellType().equals(CellType.OBJECT) || nextCell.getCellType().equals(CellType.WALL) || nextCell == null) {
            this.getCell().setItem(null);
            isHit = true;
        }
    }

    public void projectileCycle() {
        if (!isHit) {
            this.move(moveCoordinateX,moveCoordinateY);
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

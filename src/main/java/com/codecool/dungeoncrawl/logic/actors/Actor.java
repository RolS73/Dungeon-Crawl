package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.boss.SpikeForBosses;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Breakable;
import com.codecool.dungeoncrawl.logic.actors.monsters.Monster;
import com.codecool.dungeoncrawl.logic.actors.npcs.NonPlayerCharacter;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int attackPower = 1;
    private String tileName = getTileName();
    private boolean thisABossFight = false;
    private boolean wallCheatOn = false;
    private int armor = 0;
    private String[] attackSoundFiles = new String[] {"Sword1"};
    private String[] hitSoundFiles = new String[] {"DSdamage1"};

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public Actor() {
    }

    public void move(int dx, int dy) {

        Cell nextCell = cell.getNeighbor(dx, dy); // eredeti


        if (nextCell == null || nextCell.getActor() instanceof SpikeForBosses) {
            return;
        }
        if (nextCell.getActor() != null && !(nextCell.getActor() instanceof NonPlayerCharacter)) {
            attack(nextCell);
//            if (nextCell.getActor() instanceof Player) {
//                damageCalculation(nextCell);
//            } else {
//                nextCell.getActor().health = nextCell.getActor().health - attackPower;
//            }

//            this.health = this.health - nextCell.getActor().getAttackPower();

            if (nextCell.getActor().health < 1 ) {
                Sounds.playSound("kill1");
                nextCell.getActor().playDeathSound();
                if (nextCell.getActor() instanceof Monster) {
                    ((Monster) nextCell.getActor()).rollForMonsterLoot();
                }
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
                nextCell.getCellType() == CellType.FLOOR || nextCell.getCellType() == CellType.FLOORNOMONSTER) && nextCell.getActor() == null) {
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

    protected void attack(Cell nextCell) {
        CombatEvent combatEvent = new CombatEvent(this, nextCell.getActor());
        combatEvent.attack();
    }

    public boolean isWallCheatOn() {
        return wallCheatOn;
    }

    public void setWallCheatOn(boolean wallCheatOn) {
        this.wallCheatOn = wallCheatOn;
    }

//    protected void damageCalculation(Cell nextCell) {
//        try {
//            Sounds.playSound("DSdamage1");
////            Player.playHurtSound();
//        } catch (NullPointerException e) {
////            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//        if (attackPower - ((Player) nextCell.getActor()).getArmor() <= 0) {
//            nextCell.getActor().health -= 1;
//        } else {
//            nextCell.getActor().health = nextCell.getActor().health - (attackPower - ((Player) nextCell.getActor()).getArmor());
//        }
//    }

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

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public String setAttackSoundFile(String[] attackSoundFiles) {
        int i = RandomGenerator.nextInt(attackSoundFiles.length);
        return attackSoundFiles[i];
    }

    public String[] getAttackSoundFiles() {
        return attackSoundFiles;
    }

    public String[] getHitSoundFiles() {
        return hitSoundFiles;
    }

    public void setAttackSoundFiles(String[] attackSoundFiles) {
        this.attackSoundFiles = attackSoundFiles;
    }
}

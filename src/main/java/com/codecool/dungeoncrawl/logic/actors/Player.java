package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {

    private String name = "player";
    private int maxHealth = 10;
    private int armor;

    public Player(Cell cell) {
        super(cell);
        this.setAttackPower(4); // ez új
        this.setHealth(maxHealth);
        this.armor = 0;
    }

    public String getTileName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void raiseMaxHealth(int maxHealthRaisedBy) {
        this.maxHealth = this.maxHealth + maxHealthRaisedBy;
    }

    public void setTileName(String newName) {
        this.name = newName;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

//    public void raiseArmor(int armorUpgrade) {
//        this.armor = this.armor + armorUpgrade;
//    }

    public void restoreHealth(int healAmount) {
        super.setHealth(Math.min(getHealth() + healAmount, getMaxHealth()));
    }

    public void lowerHealth(int damageAmount) {
        super.setHealth(getHealth() - damageAmount);
    }
}

package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.RandomGenerator;

public class Player extends Actor {


    private String name = "playerD";
    private int maxHealth = 15;
    private int strength = 1;
    private int armor;

    public Player(Cell cell) {
        super(cell);
        this.setAttackPower(1); // ez új
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


    public int getStrength() {
        return strength;
    }

    public void restoreHealth(int healAmount) {
        super.setHealth(Math.min(getHealth() + healAmount, getMaxHealth()));
    }

    public void lowerHealth(int damageAmount) {
        super.setHealth(getHealth() - damageAmount);
    }


    public static void playHurtSound() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("playerHit1");
        } else {
            Sounds.playSound("playerHit2");
        }
    }
}

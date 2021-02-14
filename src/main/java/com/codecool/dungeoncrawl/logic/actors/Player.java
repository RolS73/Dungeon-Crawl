package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.RandomGenerator;

public class Player extends Actor {


    private String name = "playerD";
    private int maxHealth = 15;
    private int strength = 1;

    public Player(Cell cell) {
        super(cell);
        this.setAttackPower(1); // ez új
        this.setHealth(maxHealth);
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

    @Override
    public void playDeathSound() {
        Sounds.playSound("Hdeath");
    }

    public void playerHit() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("playerHurt1");
        } else {
            Sounds.playSound("playerHurt2");
        }
    }

//    public void playHurtSound() {
//
//    }
}

package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.RandomGenerator;

public class Player extends Actor {


    private String name = "playerD";
    private int maxHealth = 15;
    private int strength = 1;
    private int armor;
    private int moneyAmount;
    private final String[] attackSoundFiles = new String[] {"Sword1"};
    private final String[] hitSoundFiles = new String[] {"Blank"};

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


    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void raiseMoneyAmount(int moneyAmount) {
        this.moneyAmount += moneyAmount;
    }

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
        Sounds.playSound("Hdead");
    }

    public String[] getAttackSoundFiles() {
        return attackSoundFiles;
    }

    public String[] getHitSoundFiles() {
        return hitSoundFiles;
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

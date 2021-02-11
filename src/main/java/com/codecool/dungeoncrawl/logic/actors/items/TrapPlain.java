package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class TrapPlain extends Item implements EnvironmentalDamage {

    private int environmentalDamageValue;
    private int environmentalDamageMax;
    private int currentCooldownCount;
    private final int cooldownMax;
    private String anotherTilename = "spikeTrapResting";

    @Override
    public String getTileName() {
        return anotherTilename;
    }

    public void setAnotherTileName(String newName) {
        this.anotherTilename = newName;
    }

    public TrapPlain(Cell cell, String name, int cooldown, int damage) {
        super(cell, name);
        currentCooldownCount = cooldown;
        cooldownMax = cooldown;
        this.environmentalDamageValue = damage;
        this.environmentalDamageMax = damage;
    }

    public void activate() {
        if (currentCooldownCount > 0) {
            if (anotherTilename.equals("spikeTrapActive")) {
                anotherTilename = "spikeTrapResting";
                this.environmentalDamageValue = 0;
            }
            currentCooldownCount--;
        } else if (currentCooldownCount == 0) {
            setAnotherTileName("spikeTrapActive");
            this.environmentalDamageValue = environmentalDamageMax;
            currentCooldownCount = cooldownMax;
        }
    }

    @Override
    public int getAttackPower() {
        return environmentalDamageValue;
    }

    @Override
    public boolean isEnvironmentalDamageActive() {
        return this.getTileName().equals("spikeTrapActive");
    }

    @Override
    public void playDamageSound() {
        Sounds.playSound("DSdamage1");
    }
}


package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class TrapPlain extends Item implements EnvironmentalDamage {

    private final int environmentalDamageValue;
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
    }

    public void activate() {
        if (currentCooldownCount > 0) {
            if (anotherTilename.equals("spikeTrapActive")) {
                anotherTilename = "spikeTrapResting";
            }
            currentCooldownCount--;
        } else if (currentCooldownCount == 0) {
            setAnotherTileName("spikeTrapActive");
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
}


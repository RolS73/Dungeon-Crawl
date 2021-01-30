package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class TrapPlain extends Item implements EnvironmentalDamageSource {

    //private static boolean isActive;
    private static int currentCooldownCount;
    private static int cooldownMax;
    private static String anotherTilename = "spikeTrapResting";

    @Override
    public String getTileName() {
        return anotherTilename;
    }

    public static void setAnotherTileName(String newName) {
        anotherTilename = newName;
    }

    public TrapPlain(Cell cell, String name, int cooldown) {
        super(cell, name);
        currentCooldownCount = cooldown;
        cooldownMax = cooldown;
    }

    public static void activate() {
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


    public boolean isPlayerBeingAffectedByAnEnvironmentalDamageSource() {
        return false;
    }
}


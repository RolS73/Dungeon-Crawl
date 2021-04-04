package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

import com.codecool.dungeoncrawl.logic.RandomGenerator;

public class LootChanceCalculator {

    private LootChanceCalculator() {
    }

    public static boolean isLootDropped(int lootDropChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        return calculatedChance <= lootDropChance;
    }

    public static lootRarityLevel calculateLootRarityFourRarities(int mythicalOrUniqueChance, int legendaryChance, int rareChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= mythicalOrUniqueChance) {
            return lootRarityLevel.MYTHICAL;
        } else if (calculatedChance <= legendaryChance) {
            return lootRarityLevel.LEGENDARY;
        } else if (calculatedChance <= rareChance) {
            return lootRarityLevel.RARE;
        } else {
            return lootRarityLevel.COMMON;
        }
    }

    public static lootRarityLevel calculateLootRarityRarestIsLegendary(int legendaryChance, int rareChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= legendaryChance) {
            return lootRarityLevel.LEGENDARY;
        } else if (calculatedChance <= rareChance) {
            return lootRarityLevel.RARE;
        } else {
            return lootRarityLevel.COMMON;
        }
    }

    public static lootRarityLevel calculateLootRarityRarestIsRare(int rareChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= rareChance) {
            return lootRarityLevel.RARE;
        } else {
            return lootRarityLevel.COMMON;
        }
    }

}

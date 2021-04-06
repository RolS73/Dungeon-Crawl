package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

import com.codecool.dungeoncrawl.logic.RandomGenerator;

public class LootChanceCalculator {

    private LootChanceCalculator() {
    }

    public static boolean isLootDropped(int lootDropChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        return calculatedChance <= lootDropChance;
    }

    public static LootRarityLevel calculateLootRarityFourRarities(int mythicalOrUniqueChance, int legendaryChance, int rareChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= mythicalOrUniqueChance) {
            System.out.println("mythic");
            return LootRarityLevel.MYTHICAL;
        } else if (calculatedChance <= legendaryChance) {
            System.out.println("legendary");
            return LootRarityLevel.LEGENDARY;
        } else if (calculatedChance <= rareChance) {
            System.out.println("Rare");
            return LootRarityLevel.RARE;
        } else {
            System.out.println("Common");
            return LootRarityLevel.COMMON;
        }
    }

    public static LootRarityLevel calculateLootRarityRarestIsLegendary(int legendaryChance, int rareChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= legendaryChance) {
            return LootRarityLevel.LEGENDARY;
        } else if (calculatedChance <= rareChance) {
            return LootRarityLevel.RARE;
        } else {
            return LootRarityLevel.COMMON;
        }
    }

    public static LootRarityLevel calculateLootRarityRarestIsRare(int rareChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= rareChance) {
            return LootRarityLevel.RARE;
        } else {
            return LootRarityLevel.COMMON;
        }
    }

}

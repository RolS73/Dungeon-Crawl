package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

import com.codecool.dungeoncrawl.logic.RandomGenerator;

public class LootTableCalculator {

    private LootTableCalculator() {
    }

    public static lootRarityLevel calculateLootRarity(int mythicalChanceOrUnique, int legendaryChance, int rareChance, int commonChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= mythicalChanceOrUnique) {
            return lootRarityLevel.MYTHICAL;
        } else if (calculatedChance <= legendaryChance) {
            return lootRarityLevel.LEGENDARY;
        } else if (calculatedChance <= rareChance) {
            return lootRarityLevel.RARE;
        } else if (calculatedChance <= commonChance) {
            return lootRarityLevel.COMMON;
        } else {
            return lootRarityLevel.NOLOOT;
        }
    }


}

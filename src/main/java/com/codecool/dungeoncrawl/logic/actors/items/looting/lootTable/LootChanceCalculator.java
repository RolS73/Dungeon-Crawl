package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

import com.codecool.dungeoncrawl.logic.RandomGenerator;

public class LootChanceCalculator {

    private LootChanceCalculator() {
    }

    public static boolean isLootDropped(int lootDropChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        return calculatedChance <= lootDropChance;
    }

    public static lootType itemOrWeapon(int weaponChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= weaponChance) {
            return lootType.WEAPON;
        } else {
            return lootType.ITEM;
        }
    }

    public static LootRarityLevel calculateLootRarityFourRarities(int mythicalOrUniqueChance, int legendaryChance, int rareChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= mythicalOrUniqueChance) {
            return LootRarityLevel.MYTHICAL;
        } else if (calculatedChance <= legendaryChance) {
            return LootRarityLevel.LEGENDARY;
        } else if (calculatedChance <= rareChance) {
            return LootRarityLevel.RARE;
        } else {
            return LootRarityLevel.COMMON;
        }
    }

    public static LootRarityLevel calculateLootRarityAtleastRare(int mythicalOrUniqueChance, int legendaryChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= mythicalOrUniqueChance) {
            return LootRarityLevel.MYTHICAL;
        } else if (calculatedChance <= legendaryChance) {
            return LootRarityLevel.LEGENDARY;
        } else {
            return LootRarityLevel.RARE;
        }
    }

    public static LootRarityLevel calculateLootRarityAtleastLegendary(int mythicalOrUniqueChance) {
        int calculatedChance = RandomGenerator.nextInt(100);

        if (calculatedChance <= mythicalOrUniqueChance) {
            return LootRarityLevel.MYTHICAL;
        } else {
            return LootRarityLevel.LEGENDARY;
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

package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

import com.codecool.dungeoncrawl.logic.RandomGenerator;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

import java.util.ArrayList;
import java.util.List;

public class MonsterLootList {
    private List<Item> commonLootList = new ArrayList<>();
    private List<Item> rareLootList = new ArrayList<>();
    private List<Item> legendaryLootList = new ArrayList<>();
    private List<Item> mythicalLootList = new ArrayList<>();
    private List<Item> uniqueLootList = new ArrayList<>();

    public MonsterLootList() {

    }

    public Item getItemFromLootList(List<Item> lootList, int itemIndex) {
        return lootList.get(itemIndex);
    }

    public Item getRandomItemFromLootList(List<Item> lootList) {
        return lootList.get(RandomGenerator.nextInt(lootList.size()));
    }

    public Item getRandomItemFromLootListByRarity(lootRarityLevel lootRarity) {
        if (lootRarity == lootRarityLevel.COMMON) {
            return commonLootList.get(RandomGenerator.nextInt(commonLootList.size()));
        } else if (lootRarity == lootRarityLevel.RARE) {
            return rareLootList.get(RandomGenerator.nextInt(rareLootList.size()));
        } else if (lootRarity == lootRarityLevel.LEGENDARY) {
            return legendaryLootList.get(RandomGenerator.nextInt(legendaryLootList.size()));
        } else if (lootRarity == lootRarityLevel.MYTHICAL) {
            return mythicalLootList.get(RandomGenerator.nextInt(mythicalLootList.size()));
        } else {
            throw new IllegalArgumentException("Invalid lootRarity input!");
        }
    }

    public List<Item> getCommonLootList() {
        return commonLootList;
    }

    public List<Item> getRareLootList() {
        return rareLootList;
    }

    public List<Item> getLegendaryLootList() {
        return legendaryLootList;
    }

    public List<Item> getMythicalLootList() {
        return mythicalLootList;
    }

    public List<Item> getUniqueLootList() {
        return uniqueLootList;
    }
}

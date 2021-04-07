package dungeoncrawl.logic.actors.items.looting.loottable;

import dungeoncrawl.logic.RandomGenerator;
import dungeoncrawl.logic.actors.items.looting.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MonsterLootList implements Serializable {

    private String monsterName;
    private List<Item> commonLootList = new ArrayList<>();
    private List<Item> rareLootList = new ArrayList<>();
    private List<Item> legendaryLootList = new ArrayList<>();
    private List<Item> mythicalLootList = new ArrayList<>();

    public MonsterLootList(String monsterName) {
        this.monsterName = monsterName;
    }

    public Item getItemFromLootList(List<Item> lootList, int itemIndex) {
        return lootList.get(itemIndex);
    }

    public Item getRandomItemFromLootListByRarity(LootRarityLevel lootRarity) {
        if (lootRarity == LootRarityLevel.COMMON) {
            return commonLootList.get(RandomGenerator.nextInt(commonLootList.size()));
        } else if (lootRarity == LootRarityLevel.RARE) {
            return rareLootList.get(RandomGenerator.nextInt(rareLootList.size()));
        } else if (lootRarity == LootRarityLevel.LEGENDARY) {
            return legendaryLootList.get(RandomGenerator.nextInt(legendaryLootList.size()));
        } else if (lootRarity == LootRarityLevel.MYTHICAL) {
            return mythicalLootList.get(RandomGenerator.nextInt(mythicalLootList.size()));
        } else {
            throw new IllegalArgumentException("Invalid lootRarity input!");
        }
    }

    public void printOutSelectedLootListSizes() {
        MonsterLootList processedLootTable = this;
        System.out.println(this.getMonsterName() + " table sizes:");
        System.out.println("Common: " + processedLootTable.getCommonLootList().size());
        System.out.println("Rare: " + processedLootTable.getRareLootList().size());
        System.out.println("Legendary: " + processedLootTable.getLegendaryLootList().size());
        System.out.println("Unique: " + processedLootTable.getMythicalLootList().size());
        System.out.println("\n");
    }

    public void printOutSelectedLootListContents() {
        MonsterLootList processedLootTable = this;

        System.out.println(this.getMonsterName() + " potential loot:");
        if (processedLootTable.getCommonLootList() == null) {
            System.out.println("Selected Common Table for " + processedLootTable.getMonsterName() + " is null.");
        } else {
            for (int i = 0; i < processedLootTable.getCommonLootList().size(); i++) {
                System.out.println("Common: " + processedLootTable.getCommonLootList().get(i));
            }
        }
        if (processedLootTable.getRareLootList() == null) {
            System.out.println("Selected Rare Table for " + processedLootTable.getMonsterName() + " is null.");
        } else {
            for (int i = 0; i < processedLootTable.getRareLootList().size(); i++) {
                System.out.println("Rare: " + processedLootTable.getRareLootList().get(i));
            }
        }
        if (processedLootTable.getLegendaryLootList() == null) {
            System.out.println("Selected Legendary Table for " + processedLootTable.getMonsterName() + " is null.");
        } else {
            for (int i = 0; i < processedLootTable.getLegendaryLootList().size(); i++) {
                System.out.println("Legendary: " + processedLootTable.getLegendaryLootList().get(i));
            }
        }
        if (processedLootTable.getMythicalLootList() == null) {
            System.out.println("Selected Legendary Table for " + processedLootTable.getMonsterName() + " is null.");
        } else {
            for (int i = 0; i < processedLootTable.getMythicalLootList().size(); i++) {
                System.out.println("Unique: " + processedLootTable.getLegendaryLootList().get(i));
            }
        }
        System.out.println("\n");
    }

    public String getMonsterName() {
        return monsterName;
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
}

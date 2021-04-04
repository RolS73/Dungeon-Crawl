package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

public class InitMonsterLootLists {

    private static InitMonsterLootLists single_instance = null;

    public static InitMonsterLootLists getInstance(){
        if (single_instance == null) {
            single_instance = new InitMonsterLootLists();
        }
        return single_instance;
    }

    public static void initAllLists() {
        for (MonsterLootList lootList : AllMonsterLootList.getListOfMonsterLootLists()) {
            addItemsToLootListBasedOnMonsterName(lootList);
        }
    }

    private static void addItemsToLootListBasedOnMonsterName(MonsterLootList lootList) {
        System.out.println("Lootlist " + lootList.getMonsterName() + " is initialized.");
        switch (lootList.getMonsterName()) {

            case "goblinPig":
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(0));
                lootList.getRareLootList().add(EveryItem.monsterCommonLoot.get(1));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(2));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(3));
                break;

            case "skeleton":
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(0));
                lootList.getRareLootList().add(EveryItem.monsterCommonLoot.get(1));
                lootList.getLegendaryLootList().add(EveryItem.monsterCommonLoot.get(2));
                lootList.getMythicalLootList().add(EveryItem.monsterCommonLoot.get(3));
                break;

            case "guardian":
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(0));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(1));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(2));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(3));
                break;

            case "theThing":
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(0));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(1));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(2));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(3));
                break;

            default:
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(0));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(1));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(2));
                lootList.getCommonLootList().add(EveryItem.monsterCommonLoot.get(3));
        }
    }
}

package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

import java.util.ArrayList;
import java.util.List;

public class AllMonsterLootList {

    private static AllMonsterLootList single_instance = null;

    private List<MonsterLootList> listOfMonsterLootLists = new ArrayList<>();
    private MonsterLootList genericLootList = new MonsterLootList("generic");

    private AllMonsterLootList() {
        fillListOfMonsterLootLists();
        initAllLists();
    }

    public static AllMonsterLootList getInstance() {
        if (single_instance == null) {
            single_instance = new AllMonsterLootList();
        }
        return single_instance;
    }

    public List<MonsterLootList> getListOfMonsterLootLists() {
        return listOfMonsterLootLists;
    }

    private void fillListOfMonsterLootLists() {
        listOfMonsterLootLists.add(new MonsterLootList("goblinPig"));
        listOfMonsterLootLists.add(new MonsterLootList("skeleton"));
        listOfMonsterLootLists.add(new MonsterLootList("guardian"));
        listOfMonsterLootLists.add(new MonsterLootList("theThing"));
        listOfMonsterLootLists.add(new MonsterLootList("duck"));
    }

    public void initAllLists() {
        for (MonsterLootList lootList : listOfMonsterLootLists) {
            addItemsToLootListBasedOnMonsterName(lootList);
        }
    }

    public void printAllLootListContents() {
        for(MonsterLootList lootList : listOfMonsterLootLists) {
            lootList.printOutSelectedLootListContents();
        }
    }

    public void printAllLootListSizes() {
        for(MonsterLootList lootList : listOfMonsterLootLists) {
            lootList.printOutSelectedLootListSizes();
        }
    }

    public MonsterLootList getIndividualLootListBasedOnName(String name) {
        for (MonsterLootList lootList : listOfMonsterLootLists) {
            if (lootList.getMonsterName().equals(name)) {
                return lootList;
            }
        }
        return genericLootList;
    }

    private void addItemsToLootListBasedOnMonsterName(MonsterLootList lootList) {
        switch (lootList.getMonsterName()) {

            case "goblinPig":
                lootList.getCommonLootList().add(EveryItem.getInstance().monsterCommonLoot.get(0));
                lootList.getRareLootList().add(EveryItem.getInstance().monsterCommonLoot.get(1));
                lootList.getCommonLootList().add(EveryItem.getInstance().monsterCommonLoot.get(2));
                lootList.getCommonLootList().add(EveryItem.getInstance().monsterCommonLoot.get(3));
                break;

            case "skeleton":
                lootList.getCommonLootList().add(EveryItem.getInstance().monsterCommonLoot.get(0));
                lootList.getRareLootList().add(EveryItem.getInstance().monsterCommonLoot.get(3));
                lootList.getLegendaryLootList().add(EveryItem.getInstance().itemRareLoot.get(4));
                lootList.getMythicalLootList().add(EveryItem.getInstance().monsterUniqueLoot.get(0));
                break;

            case "guardian":
                lootList.getLegendaryLootList().add(EveryItem.getInstance().monsterUniqueLoot.get(0));
                break;

            case "theThing":
                lootList.getLegendaryLootList().add(EveryItem.getInstance().monsterUniqueLoot.get(0));
                break;

            case "duck":
                lootList.getLegendaryLootList().add(EveryItem.getInstance().monsterUniqueLoot.get(0));
                break;

            default:
                lootList.getCommonLootList().add(EveryItem.getInstance().monsterCommonLoot.get(0));
                lootList.getCommonLootList().add(EveryItem.getInstance().monsterCommonLoot.get(1));
                lootList.getCommonLootList().add(EveryItem.getInstance().monsterCommonLoot.get(2));
                lootList.getCommonLootList().add(EveryItem.getInstance().monsterCommonLoot.get(3));
        }
    }

}

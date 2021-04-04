package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

import java.util.ArrayList;
import java.util.List;

public class AllMonsterLootList {

    private static AllMonsterLootList single_instance = null;

    private static List<MonsterLootList> listOfMonsterLootLists = new ArrayList<>();
    private static MonsterLootList genericLootList = new MonsterLootList("generic");

    private AllMonsterLootList() {
        listOfMonsterLootLists.add(new MonsterLootList("goblinPig"));
        listOfMonsterLootLists.add(new MonsterLootList("skeleton"));
        listOfMonsterLootLists.add(new MonsterLootList("guardian"));
        listOfMonsterLootLists.add(new MonsterLootList("theThing"));
        System.out.println("Loot Lists generated");
    }

   public static AllMonsterLootList getInstance(){
        if (single_instance == null) {
            single_instance = new AllMonsterLootList();
        }
        return single_instance;
    }

    public static List<MonsterLootList> getListOfMonsterLootLists() {
        return listOfMonsterLootLists;
    }

    public static MonsterLootList getIndividualLootListBasedOnName(String name) {
        for (MonsterLootList lootList : listOfMonsterLootLists) {
            if (lootList.getMonsterName().equals(name)) {
                return lootList;
            }
        }
        return genericLootList;
    }
}

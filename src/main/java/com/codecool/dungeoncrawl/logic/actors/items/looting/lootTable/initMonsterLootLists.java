package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

public class initMonsterLootLists {

    public static void initMonsterLists() {
        MonsterLootList goblinPigLootList = new MonsterLootList();
        goblinPigLootList.getCommonLootList().add(EveryItem.itemCommonLoot.get(0));
        
    }

}


package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.RandomGenerator;

import java.util.ArrayList;
import java.util.List;


public class LootTable {

    private  List<Item> lootTable = new ArrayList<>();
    private List<Item> commonLoot = new ArrayList<>();
    {
        commonLoot.add(new Weapon("Toothpick", 2));
        commonLoot.add(new Weapon("Stick of Truth", 3));
        commonLoot.add(new Weapon("A sword", 4));
        commonLoot.add(new Weapon("Smiling Hammer", 5));

        commonLoot.get(0).setTileName("dagger1");
        commonLoot.get(1).setTileName("staff1");
        commonLoot.get(2).setTileName("sword1");
        commonLoot.get(3).setTileName("hammer1");
    }
    private List<Item> rareLoot = new ArrayList<>();
    {
        rareLoot.add(new Weapon("Scepter of Silverport", 6));
        rareLoot.add(new Weapon("A polished sword", 8));
        rareLoot.add(new Weapon("The Hungry Axe", 10));
        rareLoot.add(new Weapon("Smile Crusher", 12));

        rareLoot.get(0).setTileName("staff2");
        rareLoot.get(1).setTileName("sword2");
        rareLoot.get(2).setTileName("axe2");
        rareLoot.get(3).setTileName("hammer2");
    }
    private List<Item> legendaryLoot = new ArrayList<>();
    {
        legendaryLoot.add(new Weapon("THE Sword", 10));
        legendaryLoot.add(new Weapon("Thunder", 12));
        legendaryLoot.add(new Weapon("Lance of the Gods", 20));
        legendaryLoot.add(new Weapon("Flail of Endless Elemental Winds", 15));

        legendaryLoot.get(0).setTileName("weapon");
        legendaryLoot.get(1).setTileName("weapon");
        legendaryLoot.get(2).setTileName("weapon");
        legendaryLoot.get(3).setTileName("weapon");
    }


    public LootTable() {
        this.lootTable = calculateRoll();
    }

    public LootTable(String chosenLootTable) {
        switch (chosenLootTable) {
            case "common":
            case "Common":
                this.lootTable = commonLoot;
                break;
            case "rare":
            case "Rare":
                this.lootTable = rareLoot;
                break;
            case "legendary":
            case "Legendary":
                this.lootTable = legendaryLoot;
                break;
            default:
                this.lootTable = commonLoot;
        }
    }

    private List<Item> calculateRoll() {
        int randomNumber = RandomGenerator.RANDOM.nextInt(100);
        if (randomNumber < 27) {
            return rareLoot;
        } else if (randomNumber < 7) {
            return legendaryLoot;
        } else {
            return commonLoot;
        }
    }


    public int generateNumberForLootTable() {
        return RandomGenerator.RANDOM.nextInt(3);
    }

    public Item getItemFromTable() {

        int rolledItemNumber = generateNumberForLootTable();
        return lootTable.get(generateNumberForLootTable());
//        int rolledItemNumber = generateNumberForLootTable() + 1;
//        if (lootTable.equals(commonLoot) && rolledItemNumber == 1) {
//            return new Key(cell);
//        } else if (rolledTable.equals(commonLoot) && rolledItemNumber == 2) {
//            return new Life(cell);
//        } else if (rolledTable.equals(commonLoot) && rolledItemNumber == 3) {
//            return new Weapon(cell);
//        } else return null;
    }


//    public enum commonItems {
//        KEY,
//        LIFE,
//        WEAPON
//    }

}


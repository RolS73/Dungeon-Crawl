
package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.RandomGenerator;
import java.util.ArrayList;
import java.util.List;


public class LootTable {

    private  List<Item> lootTable = new ArrayList<>();
    private List<Item> commonLoot = new ArrayList<>();
    {
        commonLoot.add(new Weapon("Stick of Truth", 2));
        commonLoot.add(new Weapon("Smiling Hammer", 5));
        commonLoot.add(new Weapon("A sword", 4));
    }
    private List<Item> rareLoot = new ArrayList<>();
    {
        rareLoot.add(new Weapon("A polished sword", 8));
        rareLoot.add(new Weapon("Scepter of Silverport", 9));
        rareLoot.add(new Weapon("The Hungry Axe", 10));
    }
    private List<Item> legendaryLoot = new ArrayList<>();
    {
        legendaryLoot.add(new Weapon("The Sword", 10));
        legendaryLoot.add(new Weapon("Thunder", 12));
        legendaryLoot.add(new Weapon("Lance of the Gods", 20));
        legendaryLoot.add(new Weapon("Flail of the Elemental Winds", 15));
    }

//    int[] rolledTable;
//    int[] commonLoot = new int[]{1,2,3};
//    int[] rareLoot = new int[]{1,2,3};
//    int[] legendaryLoot = new int[]{1,2,3};

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
        if (randomNumber > 10) {
            return rareLoot;
        } else if (randomNumber > 5) {
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


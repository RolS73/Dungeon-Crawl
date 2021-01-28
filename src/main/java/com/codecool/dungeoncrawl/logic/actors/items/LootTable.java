/*
package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Random;

public class LootTable {

    int[] rolledTable;
    int[] commonLoot = new int[]{1,2,3};
    int[] rareLoot = new int[]{1,2,3};
    int[] legendaryLoot = new int[]{1,2,3};

    public LootTable() {
        this.rolledTable = calculateRoll();
    }

    public LootTable(String chosenLootTable) {
        switch (chosenLootTable) {
            case "common":
            case "Common":
                this.rolledTable = commonLoot;
                break;
            case "rare":
            case "Rare":
                this.rolledTable = rareLoot;
                break;
            case "legendary":
            case "Legendary":
                this.rolledTable = legendaryLoot;
                break;
            default:
                this.rolledTable = commonLoot;
        }


    }

    private ArrayList<Item> calculateRoll() {
        Random randomNumberGenerator = new Random();
        int randomNumber = randomNumberGenerator.nextInt(100);
        if (randomNumber > 10) {
            return rareLoot;
        } else if (randomNumber > 5) {
            return legendaryLoot;
        } else {
            return commonLoot;
        }
    }


    public int generateNumberForLootTable() {
        Random randomNumberGenerator = new Random();
        return randomNumberGenerator.nextInt(rolledTable.);
    }

    public Item getItemFromTable(Cell cell) {
        int rolledItemNumber = generateNumberForLootTable() + 1;
        if (rolledTable.equals(commonLoot) && rolledItemNumber == 1) {
            return new Key(cell);
        } else if (rolledTable.equals(commonLoot) && rolledItemNumber == 2) {
            return new Life(cell);
        } else if (rolledTable.equals(commonLoot) && rolledItemNumber == 3) {
            return new Weapon(cell);
        } else return null;
    }

    public enum commonItems {
        KEY,
        LIFE,
        WEAPON
    }

}
*/

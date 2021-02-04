
package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.RandomGenerator;

import java.util.ArrayList;
import java.util.List;


public class LootTable {

    private  List<Item> lootTable = new ArrayList<>();
    private List<Item> itemCommonLoot = new ArrayList<>();
    {
        itemCommonLoot.add(new Life("Dusty Apple", 2));
        itemCommonLoot.add(new Life("Stale Bread", 3));
        itemCommonLoot.add(new LifeUpgrade("Broken Heart", 2));
        itemCommonLoot.add(new ArmorUpgrade("Shoddy Leather Vest", 1));

        itemCommonLoot.get(0).setTileName("apple");
        itemCommonLoot.get(1).setTileName("bread");
        itemCommonLoot.get(2).setTileName("lifeUpgrade1");
        itemCommonLoot.get(3).setTileName("armorUpgrade1");
    }
    private List<Item> itemRareLoot = new ArrayList<>();
    {
        itemRareLoot.add(new Life("Fresh Fish", 4));
        itemRareLoot.add(new Life("Hearty Cheese", 5));
        itemRareLoot.add(new LifeUpgrade("Yearning Heart", 3));
        itemRareLoot.add(new ArmorUpgrade("Iron Mail", 2));

        itemRareLoot.get(0).setTileName("fish");
        itemRareLoot.get(1).setTileName("cheese");
        itemRareLoot.get(2).setTileName("lifeUpgrade2");
        itemRareLoot.get(3).setTileName("armorUpgrade2");
    }
    private List<Item> itemLegendaryLoot = new ArrayList<>();
    {
        itemLegendaryLoot.add(new Life("Juicy Steak", 10));
        itemLegendaryLoot.add(new Life("Hunk of Ham", 12));
        itemLegendaryLoot.add(new LifeUpgrade("Wholesome Heart", 4));
        itemLegendaryLoot.add(new ArmorUpgrade("Mithril Plate Shirt", 3));

        itemLegendaryLoot.get(0).setTileName("steak");
        itemLegendaryLoot.get(1).setTileName("ham");
        itemLegendaryLoot.get(2).setTileName("lifeUpgrade3");
        itemLegendaryLoot.get(3).setTileName("armorUpgrade3");
    }

    private List<Item> weaponCommonLoot = new ArrayList<>();
    {
        weaponCommonLoot.add(new Weapon("Backscratcher", 2));
        weaponCommonLoot.add(new Weapon("Stick of Truth", 3));
        weaponCommonLoot.add(new Weapon("Rustbringer", 4));
        weaponCommonLoot.add(new Weapon("Smiling Hammer", 5));

        weaponCommonLoot.get(0).setTileName("axe1");
        weaponCommonLoot.get(1).setTileName("staff1");
        weaponCommonLoot.get(2).setTileName("sword1");
        weaponCommonLoot.get(3).setTileName("hammer1");
    }
    private List<Item> weaponRareLoot = new ArrayList<>();
    {
        weaponRareLoot.add(new Weapon("Scepter of Silverport", 6));
        weaponRareLoot.add(new Weapon("Shining Sword", 8));
        weaponRareLoot.add(new Weapon("The Hungry Axe", 10));
        weaponRareLoot.add(new Weapon("Smile Crusher", 11));

        weaponRareLoot.get(0).setTileName("staff2");
        weaponRareLoot.get(1).setTileName("sword2");
        weaponRareLoot.get(2).setTileName("axe2");
        weaponRareLoot.get(3).setTileName("hammer2");
    }
    private List<Item> weaponLegendaryLoot = new ArrayList<>();
    {
        weaponLegendaryLoot.add(new Weapon("Sword of Reckoning", 16));
        weaponLegendaryLoot.add(new Weapon("Solarsong", 17));
        weaponLegendaryLoot.add(new Weapon("Hydra's Cry", 18));
        weaponLegendaryLoot.add(new Weapon("Judgement", 20));

        weaponLegendaryLoot.get(0).setTileName("sword3");
        weaponLegendaryLoot.get(1).setTileName("staff3");
        weaponLegendaryLoot.get(2).setTileName("axe3");
        weaponLegendaryLoot.get(3).setTileName("hammer3");
    }


    public LootTable() {
        int tableRoll = RandomGenerator.nextInt(100);
        if (tableRoll < 32) {
            this.lootTable = calculateRollForWeaponLootTable();
        } else {
            this.lootTable = calculateRollForItemLootTable();
        }
    }

    public LootTable(String lootTableType, String chosenLootTableRarity) {
        if (lootTableType.equals("Item") || lootTableType.equals("item")) {
                switch (chosenLootTableRarity) {
                    case "common":
                    case "Common":
                        this.lootTable = itemCommonLoot;
                        break;
                    case "rare":
                    case "Rare":
                        this.lootTable = itemRareLoot;
                        break;
                    case "legendary":
                    case "Legendary":
                        this.lootTable = itemLegendaryLoot;
                        break;
                    default:
                        this.lootTable = itemCommonLoot;
                }
        } else {
            switch (chosenLootTableRarity) {
                case "common":
                case "Common":
                    this.lootTable = weaponCommonLoot;
                    break;
                case "rare":
                case "Rare":
                    this.lootTable = weaponRareLoot;
                    break;
                case "legendary":
                case "Legendary":
                    this.lootTable = weaponLegendaryLoot;
                    break;
                default:
                    this.lootTable = weaponCommonLoot;
            }
        }
    }

    private List<Item> calculateRollForWeaponLootTable() {
        int randomNumber = RandomGenerator.RANDOM.nextInt(100);
        if (randomNumber > 7 && randomNumber < 27) {
            return weaponRareLoot;
        } else if (randomNumber < 7) {
            return weaponLegendaryLoot;
        } else {
            return weaponCommonLoot;
        }
    }

    private List<Item> calculateRollForItemLootTable() {
        int randomNumber = RandomGenerator.RANDOM.nextInt(100);
        if (randomNumber > 10 && randomNumber < 30) {
            return itemRareLoot;
        } else if (randomNumber < 10) {
            return itemLegendaryLoot;
        } else {
            return itemCommonLoot;
        }
    }


    public int generateNumberForLootTable() {
        return RandomGenerator.RANDOM.nextInt(lootTable.size());
    }

    public Item getItemFromTable() {

        //int rolledItemNumber = generateNumberForLootTable();
        return lootTable.get(generateNumberForLootTable());
    }

    public Item overwriteLoot(int LootNumber) {
        return lootTable.get(LootNumber);
    }


//    public enum commonItems {
//        KEY,
//        LIFE,
//        WEAPON
//    }

}


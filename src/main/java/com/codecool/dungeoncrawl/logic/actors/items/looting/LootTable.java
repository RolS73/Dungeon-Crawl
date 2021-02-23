
package com.codecool.dungeoncrawl.logic.actors.items.looting;

import com.codecool.dungeoncrawl.logic.RandomGenerator;
import com.codecool.dungeoncrawl.logic.actors.items.Weapon;

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
        itemCommonLoot.get(3).setTileName("armorUpgrade0");
    }
    private List<Item> itemRareLoot = new ArrayList<>();
    {
        itemRareLoot.add(new Life("Fresh Fish", 4));
        itemRareLoot.add(new Life("Hearty Cheese", 5));
        itemRareLoot.add(new LifeUpgrade("Yearning Heart", 3));
        itemRareLoot.add(new ArmorUpgrade("Iron Mail", 2));
        itemRareLoot.add(new HealthPotion("Potion of Life"));

        itemRareLoot.get(0).setTileName("fish");
        itemRareLoot.get(1).setTileName("cheese");
        itemRareLoot.get(2).setTileName("lifeUpgrade2");
        itemRareLoot.get(3).setTileName("armorUpgrade1");
        itemRareLoot.get(4).setTileName("healthPoti");
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
        itemLegendaryLoot.get(3).setTileName("armorUpgrade2");
    }

    private List<Item> weaponCommonLoot = new ArrayList<>();
    {
        weaponCommonLoot.add(new Weapon("Stick of Truth", 3));
        weaponCommonLoot.add(new Weapon("Rustbringer", 4));
        weaponCommonLoot.add(new Weapon("Backscratcher", 5));
        weaponCommonLoot.add(new Weapon("Smiling Hammer", 6));

        weaponCommonLoot.get(0).setTileName("staff1");
        weaponCommonLoot.get(1).setTileName("sword1");
        weaponCommonLoot.get(2).setTileName("axe1");
        weaponCommonLoot.get(3).setTileName("hammer1");
    }
    private List<Item> weaponRareLoot = new ArrayList<>();
    {
        weaponRareLoot.add(new Weapon("Scepter of Silverport", 7));
        weaponRareLoot.add(new Weapon("Shining Sword", 8));
        weaponRareLoot.add(new Weapon("The Hungry Axe", 9));
        weaponRareLoot.add(new Weapon("Smile Crusher", 10));

        weaponRareLoot.get(0).setTileName("staff2");
        weaponRareLoot.get(1).setTileName("sword2");
        weaponRareLoot.get(2).setTileName("axe2");
        weaponRareLoot.get(3).setTileName("hammer2");
    }
    private List<Item> weaponLegendaryLoot = new ArrayList<>();
    {
        weaponLegendaryLoot.add(new Weapon("Solarsong", 15));
        weaponLegendaryLoot.add(new Weapon("Reckoning", 17));
        weaponLegendaryLoot.add(new Weapon("Hydra's Cry", 19));
        weaponLegendaryLoot.add(new Weapon("The Judge", 20));

        weaponLegendaryLoot.get(0).setTileName("staff3");
        weaponLegendaryLoot.get(1).setTileName("sword3");
        weaponLegendaryLoot.get(2).setTileName("axe3");
        weaponLegendaryLoot.get(3).setTileName("hammer3");
    }

    private List<Item> weaponMythicalLoot = new ArrayList<>();
    {
        weaponMythicalLoot.add(new Weapon("Arhat's Legacy", 25));
        weaponMythicalLoot.add(new Weapon("Claíomh Solais", 27));
        weaponMythicalLoot.add(new Weapon("Wrath of Nul", 29));
        weaponMythicalLoot.add(new Weapon("Ragnarok", 30));

        weaponMythicalLoot.get(0).setTileName("staff4");
        weaponMythicalLoot.get(1).setTileName("sword4");
        weaponMythicalLoot.get(2).setTileName("axe4");
        weaponMythicalLoot.get(3).setTileName("hammer4");

    }
    private List<Item> monsterCommonLoot = new ArrayList<>();
    {
        monsterCommonLoot.add(new Money("Money Pouch", RandomGenerator.nextInt(5, 8)));
    }

    private List<Item> monsterUniqueLoot = new ArrayList<>();
    {
        monsterUniqueLoot.add(new Weapon("Skelie Choppa", 15));

        monsterUniqueLoot.get(0).setTileName("sword2");
    }

    private List<Item> uniqueLoot = new ArrayList<>();
    {
        uniqueLoot.add(new LifeUpgrade("Radiant Heart", 5));

        uniqueLoot.get(0).setTileName("lifeUpgrade4");
    }





    public LootTable() {
        int tableRoll = RandomGenerator.nextInt(100);
        if (tableRoll < 32) {
            this.lootTable = calculateRollForWeaponLootTable();
        } else {
            this.lootTable = calculateRollForItemLootTable();
        }
    }

    public LootTable(lootRarityLevel minimumLootRarity) {
        int tableRoll = RandomGenerator.nextInt(100);
        if (minimumLootRarity.equals(lootRarityLevel.RARE)) {
            if (tableRoll < 32) {
                this.lootTable = calculateRollForWeaponLootTableAtleastRare();
            } else {
                this.lootTable = calculateRollForItemLootTableAtleastRare();
            }
        } else if (minimumLootRarity.equals(lootRarityLevel.LEGENDARY)) {
            if (tableRoll < 32) {
                this.lootTable = calculateRollForWeaponLootTableAtleastLegendary();
            } else {
                this.lootTable = calculateRollForItemLootTableAtleastLegendary();
            }
        } else if (minimumLootRarity.equals(lootRarityLevel.COMMON)) {
                if (tableRoll < 32) {
                    this.lootTable = calculateRollForWeaponLootTable();
                } else {
                    this.lootTable = calculateRollForItemLootTable();
                }
        }
    }

    public LootTable(lootRarityLevel minimumLootRarity, lootType lootType) {
        if (minimumLootRarity.equals(lootRarityLevel.COMMON)) {
                if (lootType.equals(LootTable.lootType.ITEM)) {
                    this.lootTable = calculateRollForItemLootTable();
                } else if (lootType.equals(LootTable.lootType.WEAPON)) {
                    this.lootTable = calculateRollForWeaponLootTable();
                }
        }
        if (minimumLootRarity.equals(lootRarityLevel.RARE)) {
            if (lootType.equals(LootTable.lootType.ITEM)) {
                this.lootTable = calculateRollForItemLootTableAtleastRare();
            } else if (lootType.equals(LootTable.lootType.WEAPON)) {
                this.lootTable = calculateRollForWeaponLootTableAtleastRare();
            }
        } else if (minimumLootRarity.equals(lootRarityLevel.LEGENDARY)) {
                if (lootType.equals(LootTable.lootType.ITEM)) {
                    this.lootTable = calculateRollForItemLootTableAtleastLegendary();
                } else if (lootType.equals(LootTable.lootType.WEAPON)) {
                    this.lootTable = calculateRollForWeaponLootTableAtleastLegendary();
                }
            }
        }

    private List<Item> calculateRollForWeaponLootTable() {
        int randomNumber = RandomGenerator.RANDOM.nextInt(100);
        if (randomNumber > 8 && randomNumber < 28) {
            return weaponRareLoot;
        } else if (randomNumber < 8 && randomNumber > 0) {
            return weaponLegendaryLoot;
        } else if (randomNumber == 0) {
            return weaponMythicalLoot;
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

    private List<Item> calculateRollForItemLootTableAtleastRare() {
        int randomNumber = RandomGenerator.RANDOM.nextInt(100);
        if (randomNumber < 10) {
            return itemLegendaryLoot;
        } else {
            return itemRareLoot;
        }
    }

    private List<Item> calculateRollForWeaponLootTableAtleastRare() {
        int randomNumber = RandomGenerator.RANDOM.nextInt(100);
        if (randomNumber < 8 && randomNumber > 0) {
            return weaponLegendaryLoot;
        } else if (randomNumber == 0) {
            return weaponMythicalLoot;
        }
        else {
            return weaponRareLoot;
        }
    }

    private List<Item> calculateRollForItemLootTableAtleastLegendary() {
/*        int randomNumber = RandomGenerator.RANDOM.nextInt(100);
        if (randomNumber < 3) {
            return itemMythicalLoot;
        } else {*/
            return itemLegendaryLoot;
        //}
    }

    private List<Item> calculateRollForWeaponLootTableAtleastLegendary() {
        /*int randomNumber = RandomGenerator.RANDOM.nextInt(100);
        if (randomNumber < 1) {
            return weaponMythicalLoot;
        } else {*/
            return weaponLegendaryLoot;
        //}
    }


    public List<Item> getMonsterUniqueLoot() {
        return monsterUniqueLoot;
    }

    public int generateNumberForLootTable() {
        return RandomGenerator.RANDOM.nextInt(lootTable.size());
    }

    public Item getItemFromTable() {
        return lootTable.get(generateNumberForLootTable());
    }

    public List<Item> getMonsterCommonLoot() {
        return monsterCommonLoot;
    }



    public List<Item> getItemCommonLoot() {
        return itemCommonLoot;
    }

    public List<Item> getItemRareLoot() {
        return itemRareLoot;
    }

    public List<Item> getItemLegendaryLoot() {
        return itemLegendaryLoot;
    }

    public List<Item> getWeaponCommonLoot() {
        return weaponCommonLoot;
    }

    public List<Item> getWeaponRareLoot() {
        return weaponRareLoot;
    }

    public List<Item> getWeaponLegendaryLoot() {
        return weaponLegendaryLoot;
    }



    public enum lootType {
        ITEM,
        WEAPON
    }

    public enum lootRarityLevel {
        COMMON,
        RARE,
        LEGENDARY
    }

}


package com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable;

import com.codecool.dungeoncrawl.logic.actors.items.Weapon;
import com.codecool.dungeoncrawl.logic.actors.items.looting.*;

import java.util.ArrayList;
import java.util.List;

public class EveryItem {

    private static EveryItem single_instance = null;

    public static EveryItem getInstance() {
        if (single_instance == null) {
            single_instance = new EveryItem();
        }
        return single_instance;
    }

    protected List<Item> itemCommonLoot = new ArrayList<>();
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
    protected List<Item> itemRareLoot = new ArrayList<>();
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
    protected List<Item> itemLegendaryLoot = new ArrayList<>();
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

    protected List<Item> weaponCommonLoot = new ArrayList<>();
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
    protected List<Item> weaponRareLoot = new ArrayList<>();
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
    protected List<Item> weaponLegendaryLoot = new ArrayList<>();
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

    protected List<Item> weaponMythicalLoot = new ArrayList<>();
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

    protected List<Item> monsterCommonLoot = new ArrayList<>();
    {
        monsterCommonLoot.add(new Money("Money Pouch1", 5));
        monsterCommonLoot.add(new Money("Money Pouch2", 6));
        monsterCommonLoot.add(new Money("Money Pouch2", 7));
        monsterCommonLoot.add(new Money("Money Pouch2", 8));
    }

    protected List<Item> monsterUniqueLoot = new ArrayList<>();
    {
        monsterUniqueLoot.add(new Weapon("Skelie Choppa", 15));
        //monsterUniqueLoot.add(new Material("Clunky Bone"));

        monsterUniqueLoot.get(0).setTileName("sword2");
    }

    protected List<Item> uniqueLoot = new ArrayList<>();
    {
        uniqueLoot.add(new LifeUpgrade("Radiant Heart", 5));

        uniqueLoot.get(0).setTileName("lifeUpgrade4");
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

    public List<Item> getWeaponMythicalLoot() {
        return weaponMythicalLoot;
    }

    public List<Item> getMonsterCommonLoot() {
        return monsterCommonLoot;
    }

    public List<Item> getMonsterUniqueLoot() {
        return monsterUniqueLoot;
    }

    public List<Item> getUniqueLoot() {
        return uniqueLoot;
    }
}

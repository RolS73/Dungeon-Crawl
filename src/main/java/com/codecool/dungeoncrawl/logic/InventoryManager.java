package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Sounds;
import com.codecool.dungeoncrawl.logic.actors.items.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.NoSuchElementException;

public class InventoryManager {

    public static ObservableList<Item> inventory = FXCollections.observableArrayList();

    public void pickUpItem(Item item, GameMap map) {
        if (item instanceof Weapon) {
            if (inventory.stream().anyMatch(i -> i instanceof Weapon)) {
                compareWithCurrentWeapon((Weapon) item, map);
            } else {
                equipWeapon((Weapon) item, map);
            }
        } else if (item instanceof Life) {
            if (!(map.getPlayer().getHealth() == map.getPlayer().getMaxHealth())) {
                pickUpLife(map);
                removeItemFromGround(map);
            }
        } else if (item instanceof Key) {
            inventory.add(item);
            Sounds.playSound("Coins");
            removeItemFromGround(map);
        } else if (item instanceof LifeUpgrade) {
            pickUpLifeUpgrade(item, map);
            Sounds.playSound("healthUp");
            removeItemFromGround(map);
        } else if (item instanceof ArmorUpgrade) {
            if (inventory.stream().anyMatch(i -> i instanceof ArmorUpgrade)) {
                compareWithCurrentArmor((ArmorUpgrade) item, map);
            } else {
                equipArmor((ArmorUpgrade) item, map);
            }
        }
    }

    private void removeItemFromGround(GameMap map) {
        if (map.getPlayer().getCell().getItem() != null) {
            map.getPlayer().getCell().setItem(null);
        }
    }

    private void pickUpLife(GameMap map) {
        map.getPlayer().restoreHealth(map.getPlayer().getCell().getItem().getHealth());
        Sounds.playSound("Chewing");
    }

    private void pickUpLifeUpgrade(Item item, GameMap map) {
        map.getPlayer().raiseMaxHealth(map.getPlayer().getCell().getItem().getHealth());
        if (map.getPlayer().getMaxHealth() == map.getPlayer().getHealth()) {
            map.getPlayer().restoreHealth(map.getPlayer().getCell().getItem().getHealth());
        }
        if (item.getHealth() >= 5) {
            map.getPlayer().setHealth(map.getPlayer().getMaxHealth());
        } else {
            map.getPlayer().setHealth(map.getPlayer().getHealth() + item.getHealth());
        }
    }

    public Weapon getCurrentWeapon() {
        return inventory.stream().filter(Weapon.class::isInstance)
                .map(Weapon.class::cast)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No Weapon found"));
    }

    private void compareWithCurrentArmor(ArmorUpgrade newArmor, GameMap map) {
        ArmorUpgrade currentArmor = getCurrentArmor();
        if (currentArmor.getArmorUpgradeAmount() < newArmor.getArmorUpgradeAmount()) {
            unequipArmor(currentArmor, map);
            equipArmor(newArmor, map);
        }
    }

    private void unequipArmor(ArmorUpgrade currentArmor, GameMap map) {
        inventory.remove(currentArmor);
        map.getPlayer().setArmor(0);
    }

    private void equipArmor(ArmorUpgrade newArmor, GameMap map) {
        inventory.add(newArmor);
        map.getPlayer().setArmor(newArmor.getArmorUpgradeAmount());
//        map.getPlayer().setArmor(map.getPlayer().getCell().getItem().getHealth());
        Sounds.playSound("armorEquip");
        removeItemFromGround(map);
    }

    private ArmorUpgrade getCurrentArmor() {
        return inventory.stream().filter(ArmorUpgrade.class::isInstance)
                .map(ArmorUpgrade.class::cast)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No Armor found"));
    }

    private void compareWithCurrentWeapon(Weapon weapon, GameMap map) {
        Weapon currentWeapon = getCurrentWeapon();
        if (currentWeapon.getAttackpowerIncrease() < weapon.getAttackpowerIncrease()) {
            unequipCurrentWeapon(currentWeapon, map);
            equipWeapon(weapon, map);
        }
    }

    private void unequipCurrentWeapon(Weapon currentWeapon, GameMap map) {
        inventory.remove(currentWeapon);
        map.getPlayer().setAttackPower(map.getPlayer().getAttackPower() - currentWeapon.getAttackpowerIncrease());
    }

    private void equipWeapon(Weapon weapon, GameMap map) {
        map.getPlayer().raiseAttackPower(weapon.getAttackpowerIncrease());
        inventory.add(weapon);
        Sounds.playSound("swordBling");
        removeItemFromGround(map);
    }
}

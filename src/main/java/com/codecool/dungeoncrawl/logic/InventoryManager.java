package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.items.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.NoSuchElementException;

public class InventoryManager {

    public static ObservableList<Item> inventory = FXCollections.observableArrayList();

    public void pickUpItem(Item item, GameMap map) {
        if (item instanceof Weapon) {
            if (inventory.stream().anyMatch(i -> i instanceof Weapon)) {
                compareWithCurrentWeapon(item, map);
            } else {
                equipWeapon(item, map);
            }
        } else if (item instanceof Life) {
            if (!(map.getPlayer().getHealth() == map.getPlayer().getMaxHealth())) {
                pickUpLife(map);
                removeItemFromGround(map);
            }
        } else if (item instanceof Key) {
            inventory.add(item);
            removeItemFromGround(map);
        } else if (item instanceof LifeUpgrade) {
            pickUpLifeUpgrade(item, map);
            removeItemFromGround(map);
        } else if (item instanceof ArmorUpgrade) {
            if (inventory.stream().anyMatch(i -> i instanceof ArmorUpgrade)) {
                compareWithCurrentArmor(item, map);
            } else {
                equipArmor(item, map);
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

    private void compareWithCurrentArmor(Item item, GameMap map) {
        ArmorUpgrade currentArmor = getCurrentArmor();
        if (currentArmor.getHealth() < item.getHealth()) {
            unequipArmor(currentArmor, map);
            equipArmor(item, map);
        }
    }

    private void unequipArmor(ArmorUpgrade currentArmor, GameMap map) {
        inventory.remove(currentArmor);
        map.getPlayer().setArmor(0);
    }

    private void equipArmor(Item item, GameMap map) {
        inventory.add(item);
        map.getPlayer().setArmor(item.getHealth());
//        map.getPlayer().setArmor(map.getPlayer().getCell().getItem().getHealth());
        removeItemFromGround(map);
    }

    private ArmorUpgrade getCurrentArmor() {
        return inventory.stream().filter(ArmorUpgrade.class::isInstance)
                .map(ArmorUpgrade.class::cast)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No Armor found"));
    }

    private void compareWithCurrentWeapon(Item item, GameMap map) {
        Weapon currentWeapon = getCurrentWeapon();
        if (currentWeapon.getAttackpowerIncrease() < ((Weapon) item).getAttackpowerIncrease()) {
            unequipCurrentWeapon(currentWeapon, map);
            equipWeapon(item, map);
        }
    }

    private void unequipCurrentWeapon(Weapon currentWeapon, GameMap map) {
        inventory.remove(currentWeapon);
        map.getPlayer().setAttackPower(map.getPlayer().getAttackPower() - currentWeapon.getAttackpowerIncrease());
    }

    private void equipWeapon(Item item, GameMap map) {
        map.getPlayer().raiseAttackPower(((Weapon) item).getAttackpowerIncrease());
        inventory.add(item);
        removeItemFromGround(map);
    }
}

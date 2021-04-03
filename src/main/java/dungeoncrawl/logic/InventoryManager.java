package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.Weapon;
import dungeoncrawl.logic.actors.items.looting.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.NoSuchElementException;

public class InventoryManager {

    public static ObservableMap<Item, Integer> inventory = FXCollections.observableHashMap();

    public static ObservableList<Item> keys = FXCollections.observableArrayList();

    public void pickUpItem(Item item, GameMap map) {
        if (item instanceof Weapon) {
            if (inventory.keySet().stream().anyMatch(i -> i instanceof Weapon)) {
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
            addItemToInventory(item);
            Sounds.playSound("keyGet");
            removeItemFromGround(map);
        } else if (item instanceof Money) {
            map.getPlayer().raiseMoneyAmount(((Money) item).getMoneyAmount());
            Sounds.playSound("Coins");
            System.out.println(map.getPlayer().getMoneyAmount());
            removeItemFromGround(map);
        }
        else if (item instanceof LifeUpgrade) {
            pickUpLifeUpgrade(item, map);
            Sounds.playSound("healthUp");
            removeItemFromGround(map);
        } else if (item instanceof ArmorUpgrade) {
            if (inventory.keySet().stream().anyMatch(i -> i instanceof ArmorUpgrade)) {
                compareWithCurrentArmor((ArmorUpgrade) item, map);
            } else {
                equipArmor((ArmorUpgrade) item, map);
            }
        } else if (item instanceof HealthPotion) {
            if (!inventory.containsKey(item) || inventory.get(item) < HealthPotion.LIMIT) {
                addItemToInventory(item);
                removeItemFromGround(map);
            }
        }
    }

    public HealthPotion getPotion() {
        return inventory.keySet().stream().filter(HealthPotion.class::isInstance)
                .map(HealthPotion.class::cast)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No Potion found"));
    }

    private void addItemToInventory(Item item) {
        if (!inventory.containsKey(item)) {
            inventory.put(item, 1);
        } else {
            int newAmount = inventory.get(item) + 1;
            inventory.put(item, newAmount);
        }
    }

    public void removeItemFromInventory(Item item) {
        if (inventory.get(item) > 1) {
            int newAmount = inventory.get(item) - 1;
            inventory.put(item, newAmount);
        } else {
            inventory.remove(item);
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
        return inventory.keySet().stream().filter(Weapon.class::isInstance)
                .map(Weapon.class::cast)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No Weapon found"));
    }

    private void unequipArmor(ArmorUpgrade currentArmor, GameMap map) {
//        inventory.remove(currentArmor);
        removeItemFromInventory(currentArmor);
        map.getPlayer().setArmor(0);
    }

    private void equipArmor(ArmorUpgrade newArmor, GameMap map) {
//        inventory.add(newArmor);
        addItemToInventory(newArmor);
        map.getPlayer().setArmor(newArmor.getArmorUpgradeAmount());
        Sounds.playSound("armorEquip");
        removeItemFromGround(map);
    }

    private ArmorUpgrade getCurrentArmor() {
        return inventory.keySet().stream().filter(ArmorUpgrade.class::isInstance)
                .map(ArmorUpgrade.class::cast)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No Armor found"));
    }

    private void compareWithCurrentArmor(ArmorUpgrade newArmor, GameMap map) {
        ArmorUpgrade currentArmor = getCurrentArmor();
        if (currentArmor.getArmorUpgradeAmount() < newArmor.getArmorUpgradeAmount()) {
            unequipArmor(currentArmor, map);
            equipArmor(newArmor, map);
        }
    }

    private void compareWithCurrentWeapon(Weapon weapon, GameMap map) {
        Weapon currentWeapon = getCurrentWeapon();
        if (currentWeapon.getAttackpowerIncrease() < weapon.getAttackpowerIncrease()) {
            unequipCurrentWeapon(currentWeapon, map);
            equipWeapon(weapon, map);
        }
    }

    private void unequipCurrentWeapon(Weapon currentWeapon, GameMap map) {
//        inventory.remove(currentWeapon);
        removeItemFromInventory(currentWeapon);
        map.getPlayer().setAttackPower(map.getPlayer().getAttackPower() - currentWeapon.getAttackpowerIncrease());
    }

    private void equipWeapon(Weapon weapon, GameMap map) {
        map.getPlayer().raiseAttackPower(weapon.getAttackpowerIncrease());
        addItemToInventory(weapon);
//        inventory.add(weapon);
        Sounds.playSound("swordBling");
        removeItemFromGround(map);
    }
}

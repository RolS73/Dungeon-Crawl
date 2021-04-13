package dungeoncrawl.cheats;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.GameMap;
import dungeoncrawl.logic.InventoryManager;
import dungeoncrawl.logic.MapLoader;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.items.interactablilty.InteractiveObject;
import dungeoncrawl.logic.actors.items.interactablilty.Switch;
import dungeoncrawl.logic.actors.items.interactablilty.TorchPuzzle;
import dungeoncrawl.logic.actors.items.looting.loottable.EveryItem;
import dungeoncrawl.logic.maps.Maps;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;

public class Cheats {

    @Getter private boolean developerGearReceived = false;
    @Getter private final List<String> wallCheatNameList = Arrays.asList("Laci", "Ricsi", "Roland", "Szabolcs", "George");
    private final InventoryManager inventoryManager;
    private final Maps maps;

    public Cheats(InventoryManager inventoryManager, Maps maps) {
        this.inventoryManager = inventoryManager;
        this.maps = maps;
    }

    public boolean getRearGear() {
        if (!developerGearReceived) {
            Player player = getPlayer();
            GameMap currentMap = getCurrentMap();
            player.raiseMaxHealth(17);
            player.setHealth(player.getMaxHealth());
            inventoryManager.pickUpItem(EveryItem.getInstance().getWeaponRareLoot().get(1), currentMap);
            inventoryManager.pickUpItem(EveryItem.getInstance().getItemRareLoot().get(3), currentMap);
            developerGearReceived = true;
            return true;
        }
        return false;
    }

    public boolean getLegendaryGear() {
        if (!developerGearReceived) {
            Player player = getPlayer();
            GameMap currentMap = getCurrentMap();
            player.raiseMaxHealth(35);
            player.setHealth(player.getMaxHealth());
            inventoryManager.pickUpItem(EveryItem.getInstance().getItemLegendaryLoot().get(3), currentMap);
            inventoryManager.pickUpItem(EveryItem.getInstance().getWeaponLegendaryLoot().get(1), currentMap);
            developerGearReceived = true;
            return true;
        }
        return false;
    }

    public void teleportPlayer(int x, int y) {
        getPlayer().teleport(x, y);
    }

    public void getCellInfoFrontOfPlayer() {
        Cell cell = getPlayer().getCellInFrontOfActor();
        System.out.println(cell.getCellType());
        if (cell.getItem() instanceof Switch) {
            System.out.println(((Switch) cell.getItem()).getGroupName());
        }
    }

    public void spawnRareLootWithIndexOf(int index) {
        getPlayer().getCellInFrontOfActor().setItem(EveryItem.getInstance().getItemRareLoot().get(index));
    }

    public void setWallCheat() {
        Player player = getPlayer();
        player.setWallCheatOn(!player.isWallCheatOn());
    }

    public void getToNextMap() {
        Player player = getPlayer();
        player.saveStats();
        maps.incrementCurrentMapIndex();
        MapLoader.loadMap(maps.getCurrentMapIndex());
        player.loadStats();
//        player.setNameGivenByPlayer(menu.getPlayerName().getText());
    }

    public void getToPreviousMap() {
        Player player = getPlayer();
        player.saveStats();
        maps.decrementCurrentMapIndex();
        MapLoader.loadMap(maps.getCurrentMapIndex());
        player.loadStats();
//        player.setNameGivenByPlayer(menu.getPlayerName().getText());
    }

    public void igniteAllTorches() {
        getCurrentMap().getMapStateSwitchers().stream().filter(x -> x instanceof TorchPuzzle).forEach(InteractiveObject::interact);
    }

    private Player getPlayer() {
        return getCurrentMap().getPlayer();
    }

    private GameMap getCurrentMap() {
        return maps.getCurrentMap();
    }
    
}

package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.items.LootTable;

public class SetInteractableItems {

    public static void setStuff(GameMap map) {
        map.getPlacedItemsCollection().get(0).getCell().setItem(new LootTable().getItemRareLoot().get(2));
        map.getPlacedItemsCollection().get(1).getCell().setItem(new LootTable().getItemRareLoot().get(3));
        map.getPlacedItemsCollection().get(2).getCell().setItem(new LootTable().getItemRareLoot().get(2));
        map.getPlacedItemsCollection().get(3).getCell().setItem(new LootTable().getItemRareLoot().get(2));

        map.getDoorsSealedFromOtherSideArray().get(0).setOpenableFromWhatDirection("Up");
        map.getDoorsSealedFromOtherSideArray().get(1).setOpenableFromWhatDirection("Left");
        map.getDoorsSealedFromOtherSideArray().get(2).setOpenableFromWhatDirection("Right");

        map.getChestsCollection().get(0).setAnotherTilename("chest1");
        map.getChestsCollection().get(1).setAnotherTilename("chest2");
        map.getChestsCollection().get(2).setAnotherTilename("chest2");

        map.getMapStateSwitchers().get(0).setGroupName("hiddenEnemyGroup1");
        for (int i = 0; i < 3; i++) {
            map.getHiddenEnemySpawnersCollection().get(i).setGroupName("hiddenEnemyGroup1");
        }

        map.getLeverSwitchCollection().get(0).setGroupName("GateGroup1");
        map.getGateOpenableByASwitchCollection().get(0).setGroupName("GateGroup1");
        map.getGateOpenableByASwitchCollection().get(1).setGroupName("GateGroup1");
        map.getGateOpenableByASwitchCollection().get(2).setGroupName("GateGroup1");
        map.getGateOpenableByASwitchCollection().get(3).setGroupName("GateGroup1");

        map.getSuspiciousWallsCollection().get(0).setGroupName("SuspiciousWallGroup1");  // Under the first puzzle torch
        map.getHiddenPassagesCollection().get(0).setGroupName("SuspiciousWallGroup1");
        map.getHiddenItemsCollection().get(0).setGroupName("SuspiciousWallGroup1");

        map.getSuspiciousWallsCollection().get(1).setGroupName("SuspiciousWallGroup2");  // Under the first SusWall
        map.getHiddenItemsCollection().get(1).setGroupName("SuspiciousWallGroup2");
        map.getSuspiciousWallsCollection().get(1).setTileName("empty");

        map.getSuspiciousWallsCollection().get(3).setGroupName("SuspiciousWallGroup3");  // Main Hall
        map.getHiddenPassagesCollection().get(2).setGroupName("SuspiciousWallGroup3");
        map.getHiddenItemsCollection().get(3).setGroupName("SuspiciousWallGroup3");

        for (int i = 3; i < 15; i++) {
            map.getHiddenEnemySpawnersCollection().get(i).setGroupName("FriendlyWhiteWizardGroup1");
        }

        map.getSuspiciousWallsCollection().get(2).setGroupName("SuspiciousWallGroup4"); // Under spawn room
        map.getHiddenPassagesCollection().get(1).setGroupName("SuspiciousWallGroup4");
        map.getHiddenItemsCollection().get(2).setGroupName("SuspiciousWallGroup4");
        map.getHiddenEnemySpawnersCollection().get(16).setGroupName("SuspiciousWallGroup4");
        map.getHiddenEnemySpawnersCollection().get(15).setGroupName("SuspiciousWallGroup4");

        map.getSuspiciousWallsCollection().get(4).setGroupName("SuspiciousWallGroup5");  // Hidden passage between gates
        map.getSuspiciousWallsCollection().get(5).setGroupName("SuspiciousWallGroup5");
        for (int i = 3; i < 14; i++) {
            map.getHiddenPassagesCollection().get(i).setGroupName("SuspiciousWallGroup5");
        }

        map.getSuspiciousWallsCollection().get(6).setGroupName("SuspiciousWallGroup6");  // Left optional room
        map.getHiddenItemsCollection().get(4).setGroupName("SuspiciousWallGroup6");

        map.getSuspiciousWallsCollection().get(8).setGroupName("SuspiciousWallGroup7");  // Right optional room
        map.getHiddenItemsCollection().get(5).setGroupName("SuspiciousWallGroup7");

        map.getMapQuickTravelPassages().get(0).setDestinationX(3);
        map.getMapQuickTravelPassages().get(0).setDestinationY(15);

        map.getMapQuickTravelPassages().get(1).setDestinationX(62);
        map.getMapQuickTravelPassages().get(1).setDestinationY(38);
        map.getMapQuickTravelPassages().get(2).setDestinationX(63);
        map.getMapQuickTravelPassages().get(2).setDestinationY(38);

        map.getMapQuickTravelPassages().get(3).setDestinationX(20);
        map.getMapQuickTravelPassages().get(3).setDestinationY(20);
        map.getMapQuickTravelPassages().get(4).setDestinationX(21);
        map.getMapQuickTravelPassages().get(4).setDestinationY(20);

        /*map.getHiddenEnemySpawnersCollection().get(5).setEnemyType("soulStealer");*/
    }
}

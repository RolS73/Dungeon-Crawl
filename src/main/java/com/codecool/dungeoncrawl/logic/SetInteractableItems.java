package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.LootTable;

public class SetInteractableItems {

    public static void setStuff(int mapNumber) {
        if (mapNumber == 0) {
            Main.cheatingMapGetter().getPlacedItemsCollection().get(0).getCell().setItem(new LootTable().getItemRareLoot().get(2));
            Main.cheatingMapGetter().getPlacedItemsCollection().get(1).getCell().setItem(new LootTable().getItemRareLoot().get(3));
            Main.cheatingMapGetter().getPlacedItemsCollection().get(2).getCell().setItem(new LootTable().getItemRareLoot().get(2));
            Main.cheatingMapGetter().getPlacedItemsCollection().get(3).getCell().setItem(new LootTable().getItemRareLoot().get(2));

            Main.cheatingMapGetter().getDoorsSealedFromOtherSideArray().get(0).setOpenableFromWhatDirection("Up");
            Main.cheatingMapGetter().getDoorsSealedFromOtherSideArray().get(1).setOpenableFromWhatDirection("Left");
            Main.cheatingMapGetter().getDoorsSealedFromOtherSideArray().get(2).setOpenableFromWhatDirection("Right");

            Main.cheatingMapGetter().getChestsCollection().get(0).setAnotherTilename("chest1");
            Main.cheatingMapGetter().getChestsCollection().get(1).setAnotherTilename("chest2");
            Main.cheatingMapGetter().getChestsCollection().get(2).setAnotherTilename("chest2");

            Main.cheatingMapGetter().getMapStateSwitchers().get(0).setGroupName("hiddenEnemyGroup1");
            for (int i = 0; i < 3; i++) {
                Main.cheatingMapGetter().getHiddenEnemySpawnersCollection().get(i).setGroupName("hiddenEnemyGroup1");
            }

            Main.cheatingMapGetter().getLeverSwitchCollection().get(0).setGroupName("GateGroup1");
            Main.cheatingMapGetter().getGateOpenableByASwitchCollection().get(0).setGroupName("GateGroup1");
            Main.cheatingMapGetter().getGateOpenableByASwitchCollection().get(1).setGroupName("GateGroup1");
            Main.cheatingMapGetter().getGateOpenableByASwitchCollection().get(2).setGroupName("GateGroup1");
            Main.cheatingMapGetter().getGateOpenableByASwitchCollection().get(3).setGroupName("GateGroup1");

            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(0).setGroupName("SuspiciousWallGroup1");  // Under the first puzzle torch
            Main.cheatingMapGetter().getHiddenPassagesCollection().get(0).setGroupName("SuspiciousWallGroup1");
            Main.cheatingMapGetter().getHiddenItemsCollection().get(0).setGroupName("SuspiciousWallGroup1");

            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(1).setGroupName("SuspiciousWallGroup2");  // Under the first SusWall
            Main.cheatingMapGetter().getHiddenItemsCollection().get(1).setGroupName("SuspiciousWallGroup2");
            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(1).setTileName("empty");

            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(3).setGroupName("SuspiciousWallGroup3");  // Main Hall
            Main.cheatingMapGetter().getHiddenPassagesCollection().get(2).setGroupName("SuspiciousWallGroup3");
            Main.cheatingMapGetter().getHiddenItemsCollection().get(3).setGroupName("SuspiciousWallGroup3");

            for (int i = 3; i < 15; i++) {
                Main.cheatingMapGetter().getHiddenEnemySpawnersCollection().get(i).setGroupName("FriendlyWhiteWizardGroup1");
            }

            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(2).setGroupName("SuspiciousWallGroup4"); // Under spawn room
            Main.cheatingMapGetter().getHiddenPassagesCollection().get(1).setGroupName("SuspiciousWallGroup4");
            Main.cheatingMapGetter().getHiddenItemsCollection().get(2).setGroupName("SuspiciousWallGroup4");
            Main.cheatingMapGetter().getHiddenEnemySpawnersCollection().get(16).setGroupName("SuspiciousWallGroup4");
            Main.cheatingMapGetter().getHiddenEnemySpawnersCollection().get(15).setGroupName("SuspiciousWallGroup4");

            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(4).setGroupName("SuspiciousWallGroup5");  // Hidden passage between gates
            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(5).setGroupName("SuspiciousWallGroup5");
            for (int i = 3; i < 14; i++) {
                Main.cheatingMapGetter().getHiddenPassagesCollection().get(i).setGroupName("SuspiciousWallGroup5");
            }

            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(6).setGroupName("SuspiciousWallGroup6");  // Left optional room
            Main.cheatingMapGetter().getHiddenItemsCollection().get(4).setGroupName("SuspiciousWallGroup6");

            Main.cheatingMapGetter().getSuspiciousWallsCollection().get(8).setGroupName("SuspiciousWallGroup7");  // Right optional room
            Main.cheatingMapGetter().getHiddenItemsCollection().get(5).setGroupName("SuspiciousWallGroup7");

            Main.cheatingMapGetter().getMapQuickTravelPassages().get(0).setDestinationX(3);
            Main.cheatingMapGetter().getMapQuickTravelPassages().get(0).setDestinationY(15);

            Main.cheatingMapGetter().getMapQuickTravelPassages().get(1).setDestinationX(62);
            Main.cheatingMapGetter().getMapQuickTravelPassages().get(1).setDestinationY(38);
            Main.cheatingMapGetter().getMapQuickTravelPassages().get(2).setDestinationX(63);
            Main.cheatingMapGetter().getMapQuickTravelPassages().get(2).setDestinationY(38);

            Main.cheatingMapGetter().getMapQuickTravelPassages().get(3).setDestinationX(20);
            Main.cheatingMapGetter().getMapQuickTravelPassages().get(3).setDestinationY(20);
            Main.cheatingMapGetter().getMapQuickTravelPassages().get(4).setDestinationX(21);
            Main.cheatingMapGetter().getMapQuickTravelPassages().get(4).setDestinationY(20);

            Main.cheatingMapGetter().getDoorsOpenableBySwitches().get(0).setGroupName("hiddenRoomGroup");
            Main.cheatingMapGetter().getDoorsOpenableBySwitches().get(1).setGroupName("hiddenRoomGroup");

            for (int i = 14; i < 26; i++) {
                Main.cheatingMapGetter().getHiddenPassagesCollection().get(i).setGroupName("hiddenRoomGroup");
            }

            Main.cheatingMapGetter().getChestsCollection().get(3).setAnotherTilename("chest2");
            Main.cheatingMapGetter().getChestsCollection().get(4).setAnotherTilename("chest2");
        }


/*map.getHiddenEnemySpawnersCollection().get(5).setEnemyType("soulStealer");*/

    }
}

package dungeoncrawl.logic;

import dungeoncrawl.Main;
import dungeoncrawl.logic.actors.items.looting.loottable.EveryItem;

public class SetInteractableItems {

    public static void setStuff(int mapNumber) {
        if (mapNumber == 0) {
            Main.getCurrentMap().getPlacedItemsCollection().get(0).getCell().setItem(EveryItem.getInstance().getItemRareLoot().get(2));
            Main.getCurrentMap().getPlacedItemsCollection().get(1).getCell().setItem(EveryItem.getInstance().getItemRareLoot().get(3));
            Main.getCurrentMap().getPlacedItemsCollection().get(2).getCell().setItem(EveryItem.getInstance().getItemRareLoot().get(2));
            Main.getCurrentMap().getPlacedItemsCollection().get(3).getCell().setItem(EveryItem.getInstance().getItemRareLoot().get(2));

            Main.getCurrentMap().getDoorsSealedFromOtherSideArray().get(0).setOpenableFromWhatDirection("Up");
            Main.getCurrentMap().getDoorsSealedFromOtherSideArray().get(1).setOpenableFromWhatDirection("Left");
            Main.getCurrentMap().getDoorsSealedFromOtherSideArray().get(2).setOpenableFromWhatDirection("Right");

            Main.getCurrentMap().getChestsCollection().get(0).setAnotherTilename("chest1");
            Main.getCurrentMap().getChestsCollection().get(1).setAnotherTilename("chest2");
            Main.getCurrentMap().getChestsCollection().get(2).setAnotherTilename("chest2");

            Main.getCurrentMap().getMapStateSwitchers().get(0).setGroupName("hiddenEnemyGroup1");
            for (int i = 0; i < 3; i++) {
                Main.getCurrentMap().getHiddenEnemySpawnersCollection().get(i).setGroupName("hiddenEnemyGroup1");
            }

            Main.getCurrentMap().getLeverSwitchCollection().get(0).setGroupName("GateGroup1");
            Main.getCurrentMap().getGateOpenableByASwitchCollection().get(0).setGroupName("GateGroup1");
            Main.getCurrentMap().getGateOpenableByASwitchCollection().get(1).setGroupName("GateGroup1");
            Main.getCurrentMap().getGateOpenableByASwitchCollection().get(2).setGroupName("GateGroup1");
            Main.getCurrentMap().getGateOpenableByASwitchCollection().get(3).setGroupName("GateGroup1");

            Main.getCurrentMap().getSuspiciousWallsCollection().get(0).setGroupName("SuspiciousWallGroup1");  // Under the first puzzle torch
            Main.getCurrentMap().getHiddenPassagesCollection().get(0).setGroupName("SuspiciousWallGroup1");
            Main.getCurrentMap().getHiddenItemsCollection().get(0).setGroupName("SuspiciousWallGroup1");

            Main.getCurrentMap().getSuspiciousWallsCollection().get(1).setGroupName("SuspiciousWallGroup2");  // Under the first SusWall
            Main.getCurrentMap().getHiddenItemsCollection().get(1).setGroupName("SuspiciousWallGroup2");
            Main.getCurrentMap().getSuspiciousWallsCollection().get(1).setTileName("empty");

            Main.getCurrentMap().getSuspiciousWallsCollection().get(3).setGroupName("SuspiciousWallGroup3");  // Main Hall
            Main.getCurrentMap().getHiddenPassagesCollection().get(2).setGroupName("SuspiciousWallGroup3");
            Main.getCurrentMap().getHiddenItemsCollection().get(3).setGroupName("SuspiciousWallGroup3");

            for (int i = 3; i < 15; i++) {
                Main.getCurrentMap().getHiddenEnemySpawnersCollection().get(i).setGroupName("FriendlyWhiteWizardGroup1");
            }

            Main.getCurrentMap().getSuspiciousWallsCollection().get(2).setGroupName("SuspiciousWallGroup4"); // Under spawn room
            Main.getCurrentMap().getHiddenPassagesCollection().get(1).setGroupName("SuspiciousWallGroup4");
            Main.getCurrentMap().getHiddenItemsCollection().get(2).setGroupName("SuspiciousWallGroup4");
            Main.getCurrentMap().getHiddenEnemySpawnersCollection().get(16).setGroupName("SuspiciousWallGroup4");
            Main.getCurrentMap().getHiddenEnemySpawnersCollection().get(15).setGroupName("SuspiciousWallGroup4");

            /*Main.getCurrentMap().getSuspiciousWallsCollection().get(4).setGroupName("SuspiciousWallGroup5");  // Hidden passage between gates
            Main.getCurrentMap().getSuspiciousWallsCollection().get(5).setGroupName("SuspiciousWallGroup5");*/
            /*for (int i = 3; i < 14; i++) {
                Main.getCurrentMap().getHiddenPassagesCollection().get(i).setGroupName("SuspiciousWallGroup5");
            }*/

            Main.getCurrentMap().getSuspiciousWallsCollection().get(6).setGroupName("SuspiciousWallGroup6");  // Left optional room
            Main.getCurrentMap().getHiddenItemsCollection().get(4).setGroupName("SuspiciousWallGroup6");

            Main.getCurrentMap().getSuspiciousWallsCollection().get(8).setGroupName("SuspiciousWallGroup7");  // Right optional room
            Main.getCurrentMap().getHiddenItemsCollection().get(5).setGroupName("SuspiciousWallGroup7");

            Main.getCurrentMap().getMapQuickTravelPassages().get(0).setDestinationX(3);
            Main.getCurrentMap().getMapQuickTravelPassages().get(0).setDestinationY(15);

            Main.getCurrentMap().getMapQuickTravelPassages().get(1).setDestinationX(62);
            Main.getCurrentMap().getMapQuickTravelPassages().get(1).setDestinationY(38);
            Main.getCurrentMap().getMapQuickTravelPassages().get(2).setDestinationX(63);
            Main.getCurrentMap().getMapQuickTravelPassages().get(2).setDestinationY(38);

            Main.getCurrentMap().getMapQuickTravelPassages().get(3).setDestinationX(20);
            Main.getCurrentMap().getMapQuickTravelPassages().get(3).setDestinationY(20);
            Main.getCurrentMap().getMapQuickTravelPassages().get(4).setDestinationX(21);
            Main.getCurrentMap().getMapQuickTravelPassages().get(4).setDestinationY(20);

            Main.getCurrentMap().getDoorsOpenableBySwitches().get(0).setGroupName("hiddenRoomGroup");
            Main.getCurrentMap().getDoorsOpenableBySwitches().get(1).setGroupName("hiddenRoomGroup");

            for (int i = 14; i < 26; i++) {
                Main.getCurrentMap().getHiddenPassagesCollection().get(i).setGroupName("hiddenRoomGroup");
            }

            Main.getCurrentMap().getChestsCollection().get(3).setAnotherTilename("chest2");
            Main.getCurrentMap().getChestsCollection().get(4).setAnotherTilename("chest2");
        }


/*map.getHiddenEnemySpawnersCollection().get(5).setEnemyType("soulStealer");*/

    }
}

package dungeoncrawl.logic;

import dungeoncrawl.Main;

public class SetInteractableItems {

    public static void setStuff(int mapNumber) {
        if (mapNumber == 0) {

            Main.getCurrentMap().getSuspiciousWallsCollection().get(1).setTileName("empty");

            /*Main.getCurrentMap().getMapQuickTravelPassages().get(0).setDestinationX(3);
            Main.getCurrentMap().getMapQuickTravelPassages().get(0).setDestinationY(15);

            Main.getCurrentMap().getMapQuickTravelPassages().get(1).setDestinationX(62);
            Main.getCurrentMap().getMapQuickTravelPassages().get(1).setDestinationY(38);
            Main.getCurrentMap().getMapQuickTravelPassages().get(2).setDestinationX(63);
            Main.getCurrentMap().getMapQuickTravelPassages().get(2).setDestinationY(38);

            Main.getCurrentMap().getMapQuickTravelPassages().get(3).setDestinationX(20);
            Main.getCurrentMap().getMapQuickTravelPassages().get(3).setDestinationY(20);
            Main.getCurrentMap().getMapQuickTravelPassages().get(4).setDestinationX(21);
            Main.getCurrentMap().getMapQuickTravelPassages().get(4).setDestinationY(20);*/
        }
    }
}

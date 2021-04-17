package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.logic.Coordinates;

public interface TeleportOnCurrentMap extends Coordinates {

    int destinationX = 0;
    int destinationY = 0;

    int x = 0;
    int y = 0;

    String getPairIdentifier();

    void setPairIdentifier(String pairIdentifier);

    void setDestinationX(int destinationX);

    void setDestinationY(int destinationY);

    void setDestinationXY (int x, int y);

    int getDestinationX();

    int getDestinationY();

    void assignDestinationCoordinatesOfInput(TeleportOnCurrentMap destination);

    boolean paired = false;

    //boolean isThisFromTheSamePair(String pairIdentifier);

    //boolean isThisNotTheSameCell(Cell cell);

    //Cell getTeleporterCellPair(Cell cell);
}

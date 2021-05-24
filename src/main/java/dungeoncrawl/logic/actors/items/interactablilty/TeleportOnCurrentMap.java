package dungeoncrawl.logic.actors.items.interactablilty;

public interface TeleportOnCurrentMap {

    String getPairIdentifier();

    default boolean isInboundHasSamePairId(String inboundPairIndentifier) {
        return false;
    }

    default boolean isInboundNotSameCoordinates(TeleportOnCurrentMap inbound) {
        return false;
    }

    default boolean isPair(TeleportOnCurrentMap inbound) {
        return this.getPairIdentifier().equals(inbound.getPairIdentifier());
    }

    void setPairIdentifier(String pairIdentifier);

    void setDestinationX(int destinationX);

    void setDestinationY(int destinationY);

    void setDestinationXY (int x, int y);

    int getDestinationX();

    int getDestinationY();

    int getCoordinateX();

    int getCoordinateY();

    boolean paired = false;
}

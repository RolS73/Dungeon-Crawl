


package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.Main;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.actors.items.looting.Item;

public class Passage extends Item implements InteractiveObject, StepOnActivatable, TeleportOnCurrentMap {

    private String anotherTileName = "passage";
    private String pairIdentifier;
    private int destinationX;
    private int destinationY;
    private boolean paired = false;

    private int coordinateX;
    private int coordinateY;

    public Passage(Cell cell, String name) {
        super(cell, name);
        coordinateX = cell.getX();
        coordinateY = cell.getY();
    }

    @Override
    public void interact() {
        Main.getCurrentMap().getPlayer().teleport(destinationX, destinationY);
    }

    @Override
    public void activate() {
        Main.getCurrentMap().getPlayer().teleport(destinationX, destinationY);
    }

    @Override
    public String getTileName() {
        return this.anotherTileName;
    }

    @Override
    public boolean isThisObjectInteractive() {
        return false;
    }

    @Override
    public boolean isMoveOnPossibleAfterInteraction() {
        return false;
    }

    @Override
    public boolean isPlayerInteractingFromLegalDirection(Cell cell) {
        return true;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell() == cell;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(int destinationX) {
        this.destinationX = destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(int destinationY) {
        this.destinationY = destinationY;
    }

    public boolean isPaired() {
        return paired;
    }

    public String getPairIdentifier() {
        return this.pairIdentifier;
    }

    @Override
    public void setPairIdentifier(String pairIdentifier) {
        this.pairIdentifier = pairIdentifier;
    }

    @Override
    public void setDestinationXY(int x, int y) {
        this.setDestinationX(x);
        this.setDestinationY(y);
    }
}




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

    private int coordinateX = this.getCell().getX();
    private int coordinateY = this.getCell().getY();


    public Passage(String name) {
        super(name);
    }

    public Passage(Cell cell, String name) {
        super(cell, name);
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

    @Override
    public String getPairIdentifier() {
        return pairIdentifier;
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

    @Override
    public void assignDestinationCoordinatesOfInput(TeleportOnCurrentMap teleporter) {
        teleporter.setDestinationXY(super.getX(), super.getY());
        this.paired = true;
    }

    /*@Override
    public boolean isThisFromTheSamePair(String pairIdentifier) {
        return this.pairIdentifier.equals(pairIdentifier);
    }

    @Override
    public boolean isThisNotTheSameCell(Cell cell) {
        return this.getY() != cell.getY() && this.getX() != cell.getX();
    }*/

    /*@Override
    public Cell getTeleporterCellPair(Cell cell) {
            for (int y = 0; y < Main.getCurrentMap().getHeight(); y++) {
                for (int x = 0; x < Main.getCurrentMap().getWidth(); x++) {
                    if (Main.getCurrentMap().getCell(x, y).getItem() instanceof TeleportOnCurrentMap &&
                            isThisNotTheSameCell(cell) &&
                            ((TeleportOnCurrentMap) Main.getCurrentMap()
                                    .getCell(x, y).getItem())
                                    .isThisFromTheSamePair(((TeleportOnCurrentMap) cell.getItem()).getPairIdentifier())) {
                        return Main.getCurrentMap().getCell(x, y);
                    }
                }
            }
        return null;
    }*/
}

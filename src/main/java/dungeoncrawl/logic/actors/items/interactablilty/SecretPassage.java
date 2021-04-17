
package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.Main;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.Item;

public class SecretPassage extends Item implements InteractiveObject, StepOnActivatable, TeleportOnCurrentMap {

    private String anotherTileName = this.getCell().getTileName();
    private boolean isAlreadyOpened;
    private int destinationX;
    private int destinationY;
    private String pairIdentifier;
    private boolean paired = false;

    public SecretPassage(Cell cell, int destinationX, int destinationY) {
        super(cell, "Path to Secrets");
        isAlreadyOpened = false;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    @Override
    public void interact() {
      if (!isAlreadyOpened) {
          this.anotherTileName = "secretPassageDown";
          Sounds.playSound("IllusioryWall");
          this.getCell().setCellType(CellType.FLOOR);
          isAlreadyOpened = true;
      }
    }

    public boolean isAlreadyOpened() {
        return isAlreadyOpened;
    }

    public void setAlreadyOpened(boolean alreadyOpened) {
        isAlreadyOpened = alreadyOpened;
    }

    @Override
    public String getTileName() {
        return this.anotherTileName;
    }

    public void setTileName(String newName) {
        anotherTileName = newName;
    }

    @Override
    public boolean isMoveOnPossibleAfterInteraction() {
        return false;
    }

    @Override
    public boolean isThisObjectInteractive() {
        return true;
    }

    @Override
    public boolean isPlayerInteractingFromLegalDirection(Cell cell) {
        return true;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell().equals(cell);
    }

    @Override
    public void activate() {
        Sounds.playSound("Move5");
        Main.getCurrentMap().getPlayer().teleport(destinationX, destinationY);

    }

    public void setDestinationX(int destinationX) {
        this.destinationX = destinationX;
    }

    public void setDestinationY(int destinationY) {
        this.destinationY = destinationY;
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
    public int getDestinationX() {
        return destinationX;
    }

    @Override
    public int getDestinationY() {
        return destinationY;
    }

    @Override
    public void assignDestinationCoordinatesOfInput(TeleportOnCurrentMap destination) {
        destination.setDestinationXY(super.getX(), super.getY());
        this.paired = true;
    }
}


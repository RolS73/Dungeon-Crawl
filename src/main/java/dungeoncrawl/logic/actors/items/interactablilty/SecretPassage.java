
package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.Main;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.Item;

public class SecretPassage extends Item implements InteractiveObject, StepOnActivatable {

    private String anotherTileName = this.getCell().getTileName();
    private boolean isAlreadyOpened;
    private int destinationX;
    private int destinationY;

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
        Main.cheatingMapGetter().getPlayer().teleport(destinationX, destinationY);

    }
}


package dungeoncrawl.logic.actors.npcs;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.actors.Actor;
import dungeoncrawl.logic.actors.items.interactablilty.InteractiveObject;

public class NonPlayerCharacter extends Actor implements Shopkeeper, InteractiveObject {

    String anotherTileName = "friendlyDwarf";

    public NonPlayerCharacter(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return anotherTileName;
    }

    @Override
    public void interact() {
        System.out.println("Talk");
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
        return this.getCell().equals(cell);
    }
}

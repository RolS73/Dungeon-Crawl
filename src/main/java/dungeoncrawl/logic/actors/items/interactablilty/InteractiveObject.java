package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.logic.Cell;

public interface InteractiveObject {

    void interact();

    boolean isThisObjectInteractive();

    boolean isMoveOnPossibleAfterInteraction();

    boolean isPlayerInteractingFromLegalDirection(Cell cell);

    boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell);
}

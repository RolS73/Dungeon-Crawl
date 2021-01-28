package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public interface InteractiveObject {

    void interact();

    boolean isThisObjectInteractive();

    boolean isMoveOnPossibleAfterInteraction();

    boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell);
}

package com.codecool.dungeoncrawl.logic.actors.items;

public interface InteractiveObject {

    void interact();

    boolean isThisObjectInteractive();

    boolean isMoveOnPossibleAfterInteraction();
}

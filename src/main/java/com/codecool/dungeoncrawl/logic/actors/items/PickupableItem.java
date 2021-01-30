package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.logic.Cell;

public interface PickupableItem {

    void Pickup();

    boolean isThisObjectPickupable();

    boolean isThisPickupableItemCurrentlyBeingFocusedOn(Cell cell);
}

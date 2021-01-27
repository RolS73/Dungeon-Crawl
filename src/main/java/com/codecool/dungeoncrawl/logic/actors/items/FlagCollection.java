package com.codecool.dungeoncrawl.logic.actors.items;

import com.codecool.dungeoncrawl.Main;

public class FlagCollection {

    private boolean isPlayerInPossessionOfKeyOfWisdom = Main.inventory.contains("Key of Wisdom");

    public boolean getIsPlayerInPossessionOfKeyOfWisdom() {
        return isPlayerInPossessionOfKeyOfWisdom;
    }
}

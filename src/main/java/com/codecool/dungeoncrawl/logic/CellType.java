package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    OBJECT("floor"),
    WALL("wall"),
    FIRESTAND("firestand"),
    BOSSFLOOR("bossfloor"),
    STUNNER("stunner");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}

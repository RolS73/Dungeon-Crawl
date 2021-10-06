package dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    FLOORNOMONSTER("floor"),
    OBJECT("floor"),
    WALL("wall"),
    //FIRESTAND("firestand"),
    BOSSFLOOR("bossfloor"),
    STUNNER("stunner");

    private String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}

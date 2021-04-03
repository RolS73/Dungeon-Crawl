package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.Main;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.MapLoader;
import dungeoncrawl.logic.actors.items.looting.Item;

public class MapChangePassage extends Item implements InteractiveObject, StepOnActivatable {

    private String anotherTileName = "stairwayUp";
    private Direction traversalDirection;
    private String groupName;

    /*private int destinationX;
    private int destinationY;
    private int coordinateX = this.getCell().getX();
    private int coordinateY = this.getCell().getY();*/

    public MapChangePassage(String name) {
        super(name);
    }

    public MapChangePassage(Cell cell, String name, Direction direction) {
        super(cell, name);
        this.traversalDirection = direction;
        if (this.traversalDirection.equals(Direction.DOWN)) {
            setAnotherTileName("stairwayDown");
        } else {
            setAnotherTileName("stairwayUp");
        }
    }

    @Override
    public void interact() {
        if (Main.getCurrentMapIndex() < 2 && this.anotherTileName.equals("stairwayUp")
                || Main.getCurrentMapIndex() < 2 && this.anotherTileName.equals("stairwayUpMap2")) {
            Main.cheatingMapGetter().getPlayer().saveStats();
            Main.setCurrentMapIndex(Main.getCurrentMapIndex() + 1);
            MapLoader.loadMap(Main.getCurrentMapIndex());
            Main.cheatingMapGetter().getPlayer().loadStats();
        } else if (Main.getCurrentMapIndex() > 0 && this.anotherTileName.equals("stairwayDown")) {
            Main.cheatingMapGetter().getPlayer().saveStats();
            Main.setCurrentMapIndex(Main.getCurrentMapIndex() - 1);
            MapLoader.loadMap(Main.getCurrentMapIndex());
            Main.cheatingMapGetter().getPlayer().loadStats();
        }
    }

    @Override
    public void activate() {
        if (Main.getCurrentMapIndex() < 2 && this.anotherTileName.equals("stairwayUp")) {
            Main.cheatingMapGetter().getPlayer().saveStats();
            Main.setCurrentMapIndex(Main.getCurrentMapIndex() + 1);
            MapLoader.loadMap(Main.getCurrentMapIndex());
            Main.cheatingMapGetter().getPlayer().loadStats();
        } else if (Main.getCurrentMapIndex() > 0 && this.anotherTileName.equals("stairwayDown")) {
            Main.cheatingMapGetter().getPlayer().saveStats();
            Main.setCurrentMapIndex(Main.getCurrentMapIndex() - 1);
            MapLoader.loadMap(Main.getCurrentMapIndex());
            Main.cheatingMapGetter().getPlayer().loadStats();
        }
    }

    @Override
    public String getTileName() {
        return this.anotherTileName;
    }

    public void setAnotherTileName(String anotherTileName) {
        this.anotherTileName = anotherTileName;
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
        return this.getCell() == cell;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

/*    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(int destinationX) {
        this.destinationX = destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(int destinationY) {
        this.destinationY = destinationY;
    }

    @Override
    public void activate() {
        Main.cheatingMapGetter().getPlayer().teleport(destinationX, destinationY);
    }*/
}

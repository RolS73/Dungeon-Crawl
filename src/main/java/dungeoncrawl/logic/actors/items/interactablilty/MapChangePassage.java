package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.Main;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.MapLoader;
import dungeoncrawl.logic.actors.items.looting.Item;

public class MapChangePassage extends Item implements InteractiveObject, StepOnActivatable {

    private String tileName = "stairwayUp";
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
            setTileName("stairwayDown");
        } else {
            setTileName("stairwayUp");
        }
    }

    @Override
    public void interact() {
        if (Main.getMaps().getCurrentMapIndex() < 2 && this.tileName.equals("stairwayUp")
                || Main.getMaps().getCurrentMapIndex() < 2 && this.tileName.equals("stairwayUpMap2")) {
            Main.getCurrentMap().getPlayer().saveStats();
            Main.getMaps().incrementCurrentMapIndex();
            MapLoader.loadMap(Main.getMaps().getCurrentMapIndex());
            Main.getCurrentMap().getPlayer().loadStats();
        } else if (Main.getMaps().getCurrentMapIndex() > 0 && this.tileName.equals("stairwayDown")) {
            Main.getCurrentMap().getPlayer().saveStats();
            Main.getMaps().decrementCurrentMapIndex();
            MapLoader.loadMap(Main.getMaps().getCurrentMapIndex());
            Main.getCurrentMap().getPlayer().loadStats();
        }
    }

    @Override
    public void activate() {
        if (Main.getMaps().getCurrentMapIndex() < 2 && this.tileName.equals("stairwayUp")) {
            Main.getCurrentMap().getPlayer().saveStats();
            Main.getMaps().incrementCurrentMapIndex();
            MapLoader.loadMap(Main.getMaps().getCurrentMapIndex());
            Main.getCurrentMap().getPlayer().loadStats();
        } else if (Main.getMaps().getCurrentMapIndex() > 0 && this.tileName.equals("stairwayDown")) {
            Main.getCurrentMap().getPlayer().saveStats();
            Main.getMaps().decrementCurrentMapIndex();
            MapLoader.loadMap(Main.getMaps().getCurrentMapIndex());
            Main.getCurrentMap().getPlayer().loadStats();
        }
    }

    @Override
    public String getTileName() {
        return this.tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
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

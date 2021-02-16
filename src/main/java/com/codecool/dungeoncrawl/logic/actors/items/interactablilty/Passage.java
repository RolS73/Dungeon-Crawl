


package com.codecool.dungeoncrawl.logic.actors.items.interactablilty;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class Passage extends Item implements InteractiveObject, StepOnActivatable {

    private String anotherTileName = "stairwayUp";
    private String groupName;
    private int destinationX;
    private int destinationY;

    private int coordinateX = this.getCell().getX();
    private int coordinateY = this.getCell().getY();


    public Passage(String name) {
        super(name);
    }

    public Passage(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public void interact() {
        Main.cheatingMapGetter().getPlayer().teleport(destinationX, destinationY);
    }

    @Override
    public String getTileName() {
        return this.anotherTileName;
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

    public int getCoordinateX() {
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
    }
}

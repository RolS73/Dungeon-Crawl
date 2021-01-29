package com.codecool.dungeoncrawl.logic.actors.items;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

    public class DoorLockedFromOtherSide extends Item implements InteractiveObject {

        private String name = "lockedFromOtherSideDoor";
        private Cell openableFromWhatSide;

        int[] coordinates;

        public DoorLockedFromOtherSide(Cell cell) {
            super(cell, "Door from Doom");
            this.coordinates = new int[]{cell.getX(), cell.getY()};
            this.setName("Door from Doom");


            }

        public void setOpenableFromWhatDirection(String openableFromWhatDirection) {
            switch (openableFromWhatDirection) {
                case "Up":
                case "up":
                    this.openableFromWhatSide = this.getCell().getNeighbor(0, -1);
                    break;
                case "Right":
                case "right":
                    this.openableFromWhatSide = this.getCell().getNeighbor(1, 0);
                    break;
                case "Down":
                case "down":
                    this.openableFromWhatSide = this.getCell().getNeighbor(0, 1);
                    break;
                case "Left":
                case "left":
                    this.openableFromWhatSide = this.getCell().getNeighbor(-1, 0);
                    break;
            }
        }

        @Override
        public boolean isThisObjectInteractive() {
            return true;
        }

        @Override
        public void interact() {
            if (isThisObjectInteractive()) {
                OpenedDoor openDoor = new OpenedDoor(this.getCell());
                openDoor.setAnotherTileName("lockedFromOtherSideDoorOpened");
                this.getCell().setItem(openDoor);
                this.getCell().setCellType(CellType.FLOOR);
            }
        }

        @Override
        public String getTileName() {
            return this.name;
        }

        @Override
        public boolean isMoveOnPossibleAfterInteraction() {
            return true;
        }

        @Override
        public boolean isPlayerInteractingFromLegalDirection(Cell cell) {
            return openableFromWhatSide == cell;
        }

        @Override
        public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
            return this.getCell().equals(cell);
        }

        public int[] getCoordinates() {
            return coordinates;
        }

    }

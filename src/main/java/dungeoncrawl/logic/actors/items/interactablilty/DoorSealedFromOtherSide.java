package dungeoncrawl.logic.actors.items.interactablilty;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.Item;

public class DoorSealedFromOtherSide extends Item implements InteractiveObject {

        private String name = "sealedFromOtherSideDoor";
        private Cell openableFromWhatSide;

        int[] coordinates;

        public DoorSealedFromOtherSide(Cell cell) {
            super(cell, "Door from Doom");
            this.coordinates = new int[]{cell.getX(), cell.getY()};
            this.setName("Door from Doom");


            }

        public void setOpenableFromWhatDirection(Direction openableFromWhatDirection) {
            switch (openableFromWhatDirection) {
                case UP:
                    this.openableFromWhatSide = this.getCell().getNeighbor(0, -1);
                    break;
                case RIGHT:
                    this.openableFromWhatSide = this.getCell().getNeighbor(1, 0);
                    break;
                case DOWN:
                    this.openableFromWhatSide = this.getCell().getNeighbor(0, 1);
                    break;
                case LEFT:
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
                openDoor.setAnotherTileName("sealedFromOtherSideDoorOpened");
                this.getCell().setItem(openDoor);
                Sounds.playSound("Door5b");
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

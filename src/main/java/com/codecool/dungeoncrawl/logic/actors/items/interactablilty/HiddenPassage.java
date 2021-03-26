package com.codecool.dungeoncrawl.logic.actors.items.interactablilty;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;

public class HiddenPassage extends Item implements InteractiveObject, Switch {

        private String anotherTileName = "empty";
        private String groupName;


        public HiddenPassage(String name) {
            super(name);
        }

        public HiddenPassage(Cell cell, String name) {
            super(cell, name);
        }

        @Override
        public void interact() {
            this.setGroupName("activated");
            this.getCell().setCellType(CellType.FLOOR);
            this.anotherTileName = this.getCell().getTileName();
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

        public String getGroupName() {
            return groupName;
        }

        @Override
        public boolean isThisFromTheSameGroup(String groupName) {
            return this.groupName.equals(groupName);
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

    }

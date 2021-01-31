package com.codecool.dungeoncrawl.logic.actors.items;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

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
            this.anotherTileName = "floor";
            this.setGroupName("activated");
            this.getCell().setCellType(CellType.FLOOR);
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

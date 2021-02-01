


package com.codecool.dungeoncrawl.logic.actors.items;
import com.codecool.dungeoncrawl.logic.Cell;

    public class Passage extends Item implements InteractiveObject {

        private String anotherTileName = "stairwayUp";
        private String groupName;


        public Passage(String name) {
            super(name);
        }

        public Passage(Cell cell, String name) {
            super(cell, name);
        }

        @Override
        public void interact() {

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

    }

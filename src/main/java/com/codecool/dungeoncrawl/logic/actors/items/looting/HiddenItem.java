package com.codecool.dungeoncrawl.logic.actors.items.looting;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.InteractiveObject;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.Switch;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.LootTable;


public class HiddenItem extends Item implements InteractiveObject, Switch {

        private String anotherTileName = "empty";
        private String groupName;

        public HiddenItem(Cell cell, String name) {
            super(cell, name);
        }

        @Override
        public void interact() {
            this.anotherTileName = "floor";
            this.getCell().setCellType(CellType.FLOOR);
            this.getCell().setItem(new LootTable(LootTable.lootRarityLevel.RARE).getItemFromTable());
            this.setGroupName("activated");
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
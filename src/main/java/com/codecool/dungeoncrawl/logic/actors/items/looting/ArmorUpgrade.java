package com.codecool.dungeoncrawl.logic.actors.items.looting;

import com.codecool.dungeoncrawl.logic.Cell;

public class ArmorUpgrade extends Item implements PickupableItem {

        private String tileName = "armor";
        private final int armorUpgradeAmount;

        public ArmorUpgrade(Cell cell, int armorUpgradeAmount) {
            super(cell, "ArmorPiece");
            this.armorUpgradeAmount = armorUpgradeAmount;
        }

        public ArmorUpgrade(String nameInput, int armorUpgradeAmount) {
            super(nameInput);
            this.armorUpgradeAmount = armorUpgradeAmount;
        }

        @Override
        public int getHealth() {
            return armorUpgradeAmount;
        }

        @Override
        public String getTileName() {
            return tileName;
        }

        public void setTileName(String tileName) {
            this.tileName = tileName;
        }
    }


package com.codecool.dungeoncrawl.logic.actors.items.looting;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.InteractiveObject;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.EveryItem;
import com.codecool.dungeoncrawl.logic.actors.items.looting.lootTable.LootChanceCalculator;

public class Chest extends Item implements InteractiveObject {
    private String anotherTilename;

    public Chest(Cell cell, String name) {
        super(cell, name);
        setAttackPower(0);
        this.anotherTilename = name;
    }

    public void setAnotherTilename(String anotherTilename) {
        this.anotherTilename = anotherTilename;
    }

    @Override
    public String getTileName() {
        return anotherTilename;
    }

    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            switch (this.getTileName()) {
                case "chest1":
                    this.getCell()
                            .setItem(EveryItem.getInstance().getRandomWeaponOrItemFromListBasedOnRarity(
                                    LootChanceCalculator.itemOrWeapon(30),
                                    LootChanceCalculator.calculateLootRarityFourRarities(0, 8, 29)
                            ));
                    this.anotherTilename = "chest1Opened";
                    this.getCell().setCellType(CellType.FLOOR);
                    break;
                case "chest2":
                    this.getCell()
                            .setItem(EveryItem.getInstance().getRandomWeaponOrItemFromListBasedOnRarity(
                                    LootChanceCalculator.itemOrWeapon(30),
                                    LootChanceCalculator.calculateLootRarityAtleastRare(0, 8)
                            ));
                    this.anotherTilename = "chest2Opened";
                    this.getCell().setCellType(CellType.FLOOR);
                    break;
                case "chest3":
                    this.getCell()
                            .setItem(EveryItem.getInstance().getRandomWeaponOrItemFromListBasedOnRarity(
                                    LootChanceCalculator.itemOrWeapon(30),
                                    LootChanceCalculator.calculateLootRarityAtleastLegendary(0)
                            ));
                    this.anotherTilename = "chest3Opened";
                    this.getCell().setCellType(CellType.FLOOR);
                    break;
            }
        }
    }

    @Override
    public boolean isThisObjectInteractive() {
        return true;
    }

    @Override
    public boolean isMoveOnPossibleAfterInteraction() {
        return true;
    }

    @Override
    public boolean isPlayerInteractingFromLegalDirection(Cell cell) {
        return true;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell().equals(cell);
    }
}


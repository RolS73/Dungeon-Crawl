package dungeoncrawl.logic.actors.items.looting;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.interactablilty.InteractiveObject;

public class Breakable extends Item implements InteractiveObject {
    private String tileName = "breakable";

    public Breakable(Cell cell, String name) {
        super(cell, name);
        setAttackPower(0);
    }

    public String getName() {
        return this.tileName;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            this.getCell().setItem(new LootTable().getItemFromTable());
            this.getCell().setCellType(CellType.FLOOR);
            if (tileName.equals("barrel")) {
                Sounds.playSound("lightBreak");
            } else if (tileName.equals("crate")) {
                Sounds.playSound("heavyBreak");
            } else if (tileName.equals("crate2")) {
                Sounds.playSound("potBreak");
            }
        }
    }

    @Override
    public boolean isThisObjectInteractive() {
        return true;
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
        return this.getCell().equals(cell);
    }
}

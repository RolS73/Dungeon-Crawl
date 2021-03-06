package dungeoncrawl.logic.actors.items.interactablilty;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.InventoryManager;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.Item;

public class LockedDoor extends Item implements InteractiveObject {

    private String name = "lockedDoor";
    /*private String groupName;
    //private boolean isActivatedByASwitch;*/

    public LockedDoor(Cell cell) {
        super(cell, "Door to Doom");
        this.setName("Door to Doom");
    }


    @Override
    public boolean isThisObjectInteractive() {
        for (Item item : InventoryManager.inventory.keySet()) {
            if (item.getName().equals("Key of Wisdom")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void interact() {
        if (isThisObjectInteractive()) {
            this.getCell().setItem(new OpenedDoor(getCell()));
            InventoryManager.inventory.keySet().removeIf(item -> item.getName().equals("Key of Wisdom"));
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
        return true;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return this.getCell().equals(cell);
    }

    /*@Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public void setGroupName(String newGroupName) {
        this.groupName = newGroupName;
    }

    @Override
    public boolean isThisFromTheSameGroup(String groupName) {
        return this.groupName.equals(groupName);
    }*/
}

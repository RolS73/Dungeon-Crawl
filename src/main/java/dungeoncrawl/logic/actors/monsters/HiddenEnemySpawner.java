package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.Main;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.interactablilty.InteractiveObject;
import dungeoncrawl.logic.actors.items.looting.Item;
import dungeoncrawl.logic.actors.items.interactablilty.Switch;

public class HiddenEnemySpawner extends Item implements InteractiveObject, Switch {

    private String groupName;
    private String enemyType;
    private String anotherTileName = "floor";

    public HiddenEnemySpawner(Cell cell, String name) {
        super(cell, name);
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean isThisFromTheSameGroup(String groupName) {
        return this.groupName.equals(groupName);
    }

    @Override
    public void interact() {
        if (enemyType == null || enemyType.equals("skeleton")) {
            Sounds.playSound("skeletonSummoned");
            Main.cheatingMapGetter().getMonsters().add(new Skeleton(this.getCell()));
        } else if (enemyType.equals("soulStealer")) {
            Main.cheatingMapGetter().getMonsters().add(new SoulStealer(this.getCell()));
        }
    }

    public void interact(Monster monster) {
        Main.cheatingMapGetter().getMonsters().add(monster);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;
    }

    public String getTileName() {
        if (enemyType != null) {
            return enemyType;
        } else {
            return anotherTileName;
        }
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
        return false;
    }

    @Override
    public boolean isThisInteractiveObjectCurrentlyBeingFocusedOn(Cell cell) {
        return false;
    }

    @Override
    public void playDeathSound() {

    }
}

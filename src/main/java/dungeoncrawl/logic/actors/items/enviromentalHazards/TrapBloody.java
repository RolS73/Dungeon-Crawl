package dungeoncrawl.logic.actors.items.enviromentalHazards;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.Item;

public class TrapBloody extends Item implements EnvironmentalDamage {

    private String anotherTileName = "spikeTrapBloodyActive";
    private int environmentalDamageValue;

    public TrapBloody(Cell cell, String name, int damageValue) {
        super(cell, name);
        environmentalDamageValue = damageValue;
    }

    @Override
    public String getTileName() {
        return anotherTileName;
    }

    public void setAnotherTileName(String newName) {
        this.anotherTileName = newName;
    }

    @Override
    public boolean isEnvironmentalDamageActive() {
        return true;
    }

    @Override
    public void playDamageSound() {
        Sounds.playSound("DSdamage1");
    }

    @Override
    public int getAttackPower() {
        return environmentalDamageValue;
    }
}

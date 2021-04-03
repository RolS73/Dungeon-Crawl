package dungeoncrawl.logic.actors.items.mapdecoration;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.actors.items.looting.Item;

public class StatueEvil extends Item {

    // Ez egy random előforduló buff/debuff station lehet (amire ha hp-t áldozol növel egy random statot, vagy nem csinál semmit)

    private String name = "statueEvil";

    public StatueEvil(Cell cell, String name) {
        super(cell, name);
    }

}

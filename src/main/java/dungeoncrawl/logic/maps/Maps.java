package dungeoncrawl.logic.maps;

import dungeoncrawl.logic.AiMovement;
import dungeoncrawl.logic.GameMap;
import dungeoncrawl.logic.MapLoader;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Maps {

    private static final int NUMBER_OF_MAPS = 3;

    @Getter
    private int currentMapIndex = 0;

    @Getter
    private final List<GameMap> mapList = new ArrayList<>();

    @Getter
    private final List<AiMovement> aiList = new ArrayList<>();

    public Maps() {
        for (int i = 0; i < NUMBER_OF_MAPS; i++) {
            mapList.add(MapLoader.loadMap(i));
            aiList.add(new AiMovement(mapList.get(i)));
        }
    }

    public Maps(List<GameMap> maps) {
        mapList.addAll(maps);
        for (GameMap map : maps) {
            aiList.add(new AiMovement(map));
        }
    }

    public GameMap getCurrentMap() {
        return mapList.get(currentMapIndex);
    }

    public AiMovement getCurrentAi() {
        return aiList.get(currentMapIndex);
    }

    public void incrementCurrentMapIndex() {
        if (currentMapIndex < mapList.size() - 1) {
            currentMapIndex++;
        }
    }

    public void decrementCurrentMapIndex() {
        if (currentMapIndex > 0) {
            currentMapIndex--;
        }
    }
}

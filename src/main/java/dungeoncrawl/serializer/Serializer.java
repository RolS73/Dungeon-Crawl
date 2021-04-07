package dungeoncrawl.serializer;

import dungeoncrawl.Main;
import dungeoncrawl.logic.GameMap;
import dungeoncrawl.logic.maps.Maps;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class Serializer {

    private Stage stage;

    public Serializer(Stage stage) {
        this.stage = stage;
    }
    public void exportMap() {
        System.out.println("export game");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Saved game file (*.sre)", "*.sre"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            String fileName = file.getAbsolutePath();
            System.out.println(fileName);
            try {
                FileOutputStream fileOut = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(Main.getMaps().getMapList());
                out.close();
                fileOut.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    public Maps importMap() {
        System.out.println("import game");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Saved game file (*.sre)", "*.sre"));
        File file = fileChooser.showOpenDialog(stage);
        Maps maps = null;
        if (file != null) {
            String fileName = file.getAbsolutePath();
            System.out.println(fileName);
            try (FileInputStream fileIn = new FileInputStream(fileName);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                maps = new  Maps((List<GameMap>) in.readObject());
            } catch (IOException | ClassNotFoundException i) {
                i.printStackTrace();
            }
        }

        // fix actors,items
        for (GameMap map : maps.getMapList()) {
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    if (map.getCell(x, y).getActor() != null) {
                        map.getCell(x, y).getActor().setCell(map.getCell(x, y));
                    }
                    if (map.getCell(x, y).getItem() != null) {
                        map.getCell(x, y).getItem().setCell(map.getCell(x, y));
                    }
                }
            }
        }
        // fix cells
        for (GameMap map : maps.getMapList()) {
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    map.getCell(x, y).setMap(map);
                }
            }
        }
        return maps;
        // fix AI
        /* This is done in Maps' constructor
        }*/
    }

    public String maptoString(GameMap[] maps) {
        System.out.println("map2string");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject(maps);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException i) {
            i.printStackTrace();
        }
        return null;
    }

    public GameMap[] stringtoMap(String mapString) {
        System.out.println("string2str");
        try {
            byte [] mapData = Base64.getDecoder().decode( mapString );
            ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream(  mapData ) );
            GameMap[] maps  = (GameMap[]) ois.readObject();
            ois.close();
            return maps;
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.items.Item;
import com.codecool.dungeoncrawl.logic.actors.items.Key;
import com.codecool.dungeoncrawl.logic.actors.items.Life;
import com.codecool.dungeoncrawl.logic.actors.items.Weapon;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Array;
import java.util.Arrays;

public class Main extends Application {

    public static ObservableList<String> inventory = FXCollections.observableArrayList();

    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        HBox lifeStatus = new HBox();
        lifeStatus.getChildren().addAll(new Label("Health: "), healthLabel);

//        ui.add(new Label("Health: "), 1, 0);
//        ui.add(healthLabel, 2, 0);
        ui.add(lifeStatus, 0, 0);

//        ObservableList<String> inventory = FXCollections.observableArrayList(map.getPlayer().getPlayersInventory());
//        inventory.add("weapon");
//        inventory.add("key");
        TableView<String> inventoryTable = new TableView<>(inventory);
        TableColumn<String, String> itemnames = new TableColumn<>("Inventory");

        itemnames.setCellValueFactory(items -> new ReadOnlyStringWrapper(items.getValue()));
        inventoryTable.getColumns().add(itemnames);
        inventoryTable.setMaxWidth(75);
        inventoryTable.setMaxHeight(150);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ui.add(inventoryTable, 0, 2);
        inventoryTable.setFocusTraversable(false);



        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                refresh();
                break;
            case SPACE:
                map.getPlayer().move(0,0);
                refresh();
                break;
        }
        if (map.getPlayer().getCell().getItem() != null) {
            if (map.getPlayer().getCell().getItem() instanceof Weapon) {
                map.getPlayer().setAttackPower(9);
                map.getPlayer().getPlayersInventory().add("Bone Chopper");
                inventory.add("Bone Chopper");
                map.getPlayer().getCell().setItem(null);
                refresh();
            } else if (map.getPlayer().getCell().getItem() instanceof Life) {
                map.getPlayer().setHealth(10);
                map.getPlayer().getCell().setItem(null);
                refresh();
            } else if (map.getPlayer().getCell().getItem() instanceof Key) {
                map.getPlayer().getPlayersInventory().add("Key of Knowledge");
                inventory.add("Key of Knowledge");
                map.getPlayer().getCell().setItem(null);
                refresh();
            }
//            System.out.println(map.getPlayer().getCell().getItem().getClass().getSimpleName());
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                Tiles.drawTile(context, cell, x, y);
                if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                }
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }
}

package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
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

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        ObservableList<String> inventory = FXCollections.observableArrayList();
        inventory.add("weapon");
        inventory.add("key");
        TableView<String> trialInventory = new TableView<>(inventory);
        TableColumn<String, String> itemnames = new TableColumn<>("Inventory");

        itemnames.setCellValueFactory(items -> new ReadOnlyStringWrapper(items.getValue()));
        trialInventory.getColumns().add(itemnames);
        trialInventory.setMaxWidth(75);
        trialInventory.setMaxHeight(200);
        trialInventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ui.add(trialInventory, 0, 1);
        trialInventory.setFocusTraversable(false);




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
            case W:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
            case S:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
            case A:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
            case D:
                map.getPlayer().move(1,0);
                refresh();
                break;
            case SPACE:
                map.getPlayer().move(0,0);
                refresh();
                break;
        }
        if (map.getPlayer().getCell().getItem() != null) {
            System.out.println(map.getPlayer().getCell().getItem().getClass().getSimpleName());
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

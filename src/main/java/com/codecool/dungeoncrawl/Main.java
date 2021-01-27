package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.items.*;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.stream.Stream;

public class Main extends Application {

    public static ObservableList<String> inventory = FXCollections.observableArrayList();
    public static ObservableList<Integer> commonItems = Stream.of(1,10).collect()

    GameMap map = MapLoader.loadMap();
    AiMovement AI = new AiMovement(map);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();

    Button pickUpButton = new Button("Pick up!");
    Button dontPickUp = new Button("Leave it..");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AI.monsterMover();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        HBox lifeStatus = new HBox();
        lifeStatus.getChildren().addAll(new Label("Health: "), healthLabel);

//        ui.add(new Label("Health: "), 1, 0);
//        ui.add(healthLabel, 2, 0);
        ui.setHgap(10);
        ui.setVgap(10);
        ui.setPadding(new Insets(10, 10, 10, 10));
        ui.add(lifeStatus, 0, 0);

        TableView<String> inventoryTable = new TableView<>(inventory);
        TableColumn<String, String> itemnames = new TableColumn<>("Inventory");

        itemnames.setCellValueFactory(items -> new ReadOnlyStringWrapper(items.getValue()));
        inventoryTable.getColumns().add(itemnames);
        inventoryTable.setMaxWidth(100);
        inventoryTable.setMaxHeight(150);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ui.add(inventoryTable, 0, 2);
        inventoryTable.setFocusTraversable(false);

        pickUpButton.setDisable(true);
        pickUpButton.setOnAction(pickUp -> {
            if (map.getPlayer().getCell().getItem() instanceof Weapon) {
                map.getPlayer().setAttackPower(9);
                inventory.add("Bone Chopper");
                map.getPlayer().getCell().setItem(null);
            } else if (map.getPlayer().getCell().getItem() instanceof Life) {
                map.getPlayer().raiseMaxHealth(5);
                map.getPlayer().setHealth(map.getPlayer().getMaxHealth());
                map.getPlayer().getCell().setItem(null);
            } else if (map.getPlayer().getCell().getItem() instanceof Key) {
                inventory.add("Key of Wisdom");
                map.getPlayer().getCell().setItem(null);
            }
            refresh();
            pickUpButton.setDisable(true);
            dontPickUp.setDisable(true);
        });

        dontPickUp.setDisable(true);
        dontPickUp.setOnAction(leave -> {
            pickUpButton.setDisable(true);
            dontPickUp.setDisable(true);
        });

        HBox lootButtons = new HBox();
        lootButtons.setSpacing(10);
        lootButtons.getChildren().addAll(pickUpButton, dontPickUp);
        ui.add(lootButtons, 0, 1);

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
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case SPACE:
//                map.getPlayer().move(0,0);
                refresh();
                break;
            case E:
                if (map.getPlayer().getCell().getItem() instanceof Weapon) {
                    map.getPlayer().raiseAttackPower(5);
                    inventory.add("Bone Chopper");
                    map.getPlayer().getCell().setItem(null);
                } else if (map.getPlayer().getCell().getItem() instanceof Life) {
                    map.getPlayer().raiseMaxHealth(5);
                    map.getPlayer().setHealth(map.getPlayer().getMaxHealth());
                    map.getPlayer().getCell().setItem(null);
                } else if (map.getPlayer().getCell().getItem() instanceof Key) {
                    inventory.add("Key of Wisdom");
                    map.getPlayer().getCell().setItem(null);

                } else if (isInteractableObjectAroundPlayer()) {
                    int[] interactableDirection = getInteractableDirection();
                    int interactablesIndex = 0;
                    while (map.getInteractables().iterator().hasNext()) {
                        if (map.getInteractables().get(interactablesIndex).isThisObjectInteractive()) {
                            map.getInteractables().get(interactablesIndex).interact();
                            if (map.getInteractables().get(interactablesIndex).isMoveOnPossibleAfterInteraction()) {
                                map.getPlayer().getCell().getNeighbor(interactableDirection[0],interactableDirection[1]).setType(CellType.FLOOR);
                            }
                            refresh();
                            return;
                        }
                        interactablesIndex++;
                    }
                }
                refresh();
                break;
        }
        if (map.getPlayer().getCell().getItem() instanceof OpenedDoor) {
            pickUpButton.setDisable(true);
            dontPickUp.setDisable(true);
        } else {
            if (map.getPlayer().getCell().getItem() != null) {
                pickUpButton.setDisable(false);
                dontPickUp.setDisable(false);
            } else {
                pickUpButton.setDisable(true);
                dontPickUp.setDisable(true);
            }
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
                if (!(map.getPlayer().getTileName().equals("playerArmored2")) && map.getPlayer().getMaxHealth() > 10) {
                    map.getPlayer().setTileName("playerArmored1");
                }
                if (map.getPlayer().getMaxHealth() >= 20) {
                    map.getPlayer().setTileName("playerArmored2");
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        AI.monsterMover();
    }

    private boolean isInteractableObjectAroundPlayer() {
        if (map.getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject ||
                map.getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject ||
                map.getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject ||
                map.getPlayer().getCell().getNeighbor(0, -1).getItem() instanceof InteractiveObject) {
            return true;
        } else {
            return false;
        }
    }

    private int[] getInteractableDirection() {
        if (map.getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{1, 0};
        } else if (map.getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{-1, 0};
        } else if (map.getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject) {
            return new int[]{0, 1};
        } else {
            return new int[]{0, -1};
        }
    }

}

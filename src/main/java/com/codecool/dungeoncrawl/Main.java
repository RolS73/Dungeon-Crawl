package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.items.Weapon;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.EnvironmentalDamage;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.ProjectileCycle;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.TrapCycle;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.*;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;
import com.codecool.dungeoncrawl.logic.actors.items.looting.LootTable;
import com.codecool.dungeoncrawl.logic.actors.items.looting.PickupableItem;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

public class Main extends Application {

    boolean isDeveloperStartingGearEnabled;
    private static int currentMapIndex = 0;
    static GameMap map1 = MapLoader.loadMap(0);
    static GameMap map2 = MapLoader.loadMap(1);
    static GameMap map3 = MapLoader.loadMap(2);
    static GameMap[] mapsArray = new GameMap[]{map1, map2, map3};

    private static int currentAiIndex = 0;
    static AiMovement AI1 = new AiMovement(mapsArray[0]);
    static AiMovement AI2 = new AiMovement(mapsArray[1]);
    static AiMovement AI3 = new AiMovement(mapsArray[2]);
    static AiMovement[] AiArray = new AiMovement[]{AI1, AI2, AI3};

    Canvas canvas = new Canvas(
            15 * Tiles.TILE_WIDTH,
            15 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackPwLabel = new Label();
    Label armorLabel = new Label();
    Label moneyLabel = new Label("0");
    Menu menu = new Menu();
    private Label name = new Label("");
    Button pickUpButton = new Button("Pick up!");
    GameOver gameOver = new GameOver();
    private Stage stage;
    private final List<String> wallCheat = Arrays.asList("Laci", "Ricsi", "Roland", "Szabolcs", "George");
    InventoryManager inventoryManager = new InventoryManager();
    public static ObservableList<CombatEvent> combatEvents = FXCollections.observableArrayList();
//    Label combatLog = new Label("Combat Log: \n");
    TextArea combatLog = new TextArea();
    GameDatabaseManager dbManager; //Sprint 2-ből

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
//        GridPane ui = new GridPane();
        VBox ui = new VBox();
        ui.setStyle("-fx-background-color: black;");

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setSpacing(10);

//        ui.add(name, 0, 0);

        HBox lifeStatus = new HBox();
        lifeStatus.setSpacing(5);
        lifeStatus.getChildren().addAll(new Label("Health:"), healthLabel, new Label("Armor:"), armorLabel);

        HBox attackPwStatus = new HBox();
        attackPwStatus.getChildren().addAll(new Label("Attackpw: "), attackPwLabel);

        HBox fiancialStatus = new HBox();
        fiancialStatus.getChildren().addAll(new Label("Coins: "), moneyLabel);

//        ui.setHgap(10);
//        ui.setVgap(10);
        ui.setPadding(new Insets(10, 10, 10, 10));
        ui.getStylesheets().add("Main.css");

//        ui.add(lifeStatus, 0, 1);
//        ui.add(attackPwStatus, 0, 2);


        Label instructions = new Label();
        instructions.setText("Move with arrow keys or WASD.\nInteract: E key.\nPick up items with E key.");
//        ui.add(instructions, 0, 5);

        InventoryManager.inventory.addListener((MapChangeListener.Change<? extends Item, ? extends Integer> change) -> {
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                // no put for existing key
                if (removed) {
                    InventoryManager.keys.remove(change.getKey());
                } else {
                    InventoryManager.keys.add(change.getKey());
                }
            }
        });

        TableView<Item> inventoryTable = new TableView<>(InventoryManager.keys);
        TableColumn<Item, String> inventoryLabel = new TableColumn<>("Inventory");
        TableColumn<Item, String> itemNames = new TableColumn<>("Items");
        TableColumn<Item, Integer> itemAmount = new TableColumn<>("Amt");

        itemAmount.setStyle("-fx-alignment: CENTER;");

        itemNames.setCellValueFactory(names -> Bindings.createStringBinding(() -> names.getValue().getName()));
        itemAmount.setCellValueFactory(amount -> Bindings.valueAt(InventoryManager.inventory, amount.getValue()));

        itemNames.prefWidthProperty().bind(inventoryTable.widthProperty().multiply(0.75));
        itemAmount.prefWidthProperty().bind(inventoryTable.widthProperty().multiply(0.238));
        itemNames.setResizable(false);
        itemAmount.setResizable(false);

        inventoryLabel.getColumns().addAll(itemNames, itemAmount);
        inventoryTable.getColumns().add(inventoryLabel);

        inventoryTable.setMaxWidth(180);
        inventoryTable.setMaxHeight(150);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        ui.add(inventoryTable, 0, 4);
        inventoryTable.setFocusTraversable(false);
        inventoryTable.setPlaceholder(new Label("Inventory is empty!"));

        if (currentMapIndex == 0) {
            SetInteractableItems.setStuff(0); //Map1 interactables
        }


        pickUpButton.setMaxWidth(90);
        pickUpButton.setDisable(true);
        pickUpButton.setOnAction(pickUp -> {
            Item item = (Item) mapsArray[currentMapIndex].getPlayer().getCell().getItem();
            inventoryManager.pickUpItem(item, mapsArray[currentMapIndex]);
            refresh();
            pickUpButton.setDisable(true);
        });
        HBox pickUpAndMoney = new HBox();
        pickUpAndMoney.setSpacing(10);
        pickUpButton.setFocusTraversable(false);
        pickUpButton.setPrefWidth(130);

        pickUpAndMoney.getChildren().addAll(pickUpButton, fiancialStatus);

        Region r = new Region();

        Label combatLogLabel = new Label("Combat Log");
        combatLog.setFocusTraversable(false);

        BorderPane borderPane = new BorderPane();

        Scene scene = new Scene(borderPane);

        menu.getImportButton().setOnAction(i -> {
            importMap();
            primaryStage.setScene(scene);
        });

//        ui.add(lootButtons, 0, 3);
        ui.getChildren().addAll(name, lifeStatus, attackPwStatus, pickUpAndMoney, inventoryTable, r, combatLogLabel, combatLog /*fiancialStatus, instructions*/);
        setupDbManager(); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< THIS IS NEW!




        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        primaryStage.setScene(menu.getMenuScreen());



        menu.getPlayButton().setOnAction(play -> {
            primaryStage.setScene(scene);
            name.setText(menu.getPlayerName().getText());
            mapsArray[currentMapIndex].getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
            if (wallCheat.contains(name.getText())) {
                mapsArray[currentMapIndex].getPlayer().setWallCheatOn(true);
            }
        });

        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    public static void installFont(String fontName) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                mapsArray[currentMapIndex].getPlayer().setTileName("playerU");
                mapsArray[currentMapIndex].getPlayer().move(0, -1);
                mapsArray[currentMapIndex].getPlayer().updateActorOrientation();
                AiArray[currentAiIndex].monsterMover();
                mapsArray[currentMapIndex].getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) mapsArray[currentMapIndex].getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case DOWN:
            case S:
                if (!keyEvent.isControlDown()) {
                    mapsArray[currentMapIndex].getPlayer().setTileName("playerD");
                    mapsArray[currentMapIndex].getPlayer().move(0, 1);
                    mapsArray[currentMapIndex].getPlayer().updateActorOrientation();
                    AiArray[currentAiIndex].monsterMover();
                    mapsArray[currentMapIndex].getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                    mapsArray[currentMapIndex].getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                    mapsArray[currentMapIndex].getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                    if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                        playerSuffersEnvironmentalDamage();
                    }
                    if (mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                        ((StepOnActivatable) mapsArray[currentMapIndex].getPlayer().getCell().getItem()).activate();
                    }
                    refresh();
                } else {
                    stage.setOpacity(0.5);
                    BorderPane root = new BorderPane();
                    Stage saveDialog = new Stage();
                    saveDialog.setTitle("Save game");
                    saveDialog.setAlwaysOnTop(true);
                    saveDialog.initStyle(StageStyle.UNDECORATED);
                    saveDialog.setResizable(false);

                    TableView<String> saveTable = new TableView<>();
                    TableColumn<String, String> col = new TableColumn<>("Saved games");
                    col.setMinWidth(320);
                    saveTable.getColumns().add(col);
                    saveTable.setPlaceholder(new Label("No saved game"));
                    root.setCenter(saveTable);

                    Button saveOkButton = new Button("Save");
                    saveOkButton.setMinWidth(160);
                    Button saveCancelButton = new Button("Cancel");
                    saveCancelButton.setOnAction((e) -> {
                        stage.setOpacity(1);
                        saveDialog.close();
                    });
                    saveCancelButton.setMinWidth(160);

                    HBox saveButtons = new HBox();
                    saveButtons.getChildren().addAll(saveOkButton, saveCancelButton);
                    VBox inputField = new VBox();
                    TextField saveInput = new TextField();
                    inputField.getChildren().addAll(saveInput, saveButtons);
                    root.setBottom(inputField);

                    Scene saveScene = new Scene(root, 320, 640);
                    saveDialog.setScene(saveScene);
                    saveDialog.initModality(Modality.APPLICATION_MODAL);
                    saveDialog.initOwner(stage);
                    saveDialog.show();
                }
                break;
            case L:
                if (keyEvent.isControlDown()) {
                    stage.setOpacity(0.5);
                    BorderPane root = new BorderPane();
                    Stage loadDialog = new Stage();
                    loadDialog.setTitle("Load game");
                    loadDialog.setAlwaysOnTop(true);
                    loadDialog.setResizable(false);
                    loadDialog.initStyle(StageStyle.UNDECORATED);

                    TableView<List<String>> loadTable = new TableView<>();
                    TableColumn<List<String>, String> col = new TableColumn<>("Saved games");
                    col.setMinWidth(320);
                    loadTable.getColumns().add(col);
                    loadTable.setPlaceholder(new Label("No saved game"));
                    root.setCenter(loadTable);

                    Button loadOkButton = new Button("Load");
                    loadOkButton.setMinWidth(160);
                    Button loadCancelButton = new Button("Cancel");
                    loadCancelButton.setOnAction((e) -> {
                        stage.setOpacity(1);
                        loadDialog.close();
                    });
                    loadCancelButton.setMinWidth(160);
                    HBox loadButtons = new HBox();
                    loadButtons.getChildren().addAll(loadOkButton, loadCancelButton);
                    root.setBottom(loadButtons);

                    Scene loadScene = new Scene(root, 320, 640);
                    loadDialog.setScene(loadScene);
                    loadDialog.initModality(Modality.APPLICATION_MODAL);
                    loadDialog.initOwner(stage);
                    loadDialog.show();
                }
                break;
            case LEFT:
            case A:
                mapsArray[currentMapIndex].getPlayer().setTileName("playerL");
                mapsArray[currentMapIndex].getPlayer().move(-1, 0);
                mapsArray[currentMapIndex].getPlayer().updateActorOrientation();
                AiArray[currentAiIndex].monsterMover();
                mapsArray[currentMapIndex].getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) mapsArray[currentMapIndex].getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case RIGHT:
            case D:
                mapsArray[currentMapIndex].getPlayer().setTileName("playerR");
                mapsArray[currentMapIndex].getPlayer().move(1, 0);
                mapsArray[currentMapIndex].getPlayer().updateActorOrientation();
                AiArray[currentAiIndex].monsterMover();
                mapsArray[currentMapIndex].getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) mapsArray[currentMapIndex].getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case SPACE:
                AiArray[currentAiIndex].monsterMover();
                mapsArray[currentMapIndex].getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) mapsArray[currentMapIndex].getPlayer().getCell().getItem()).activate();
                }
                refresh();
                //System.out.println("Player X Coordinate: " + mapsArray[currentMapIndex].getPlayer().getX() + "\n" + "Player Y Coordinate: " + mapsArray[currentMapIndex].getPlayer().getY());
                break;
            case Q:
                try {
                    if (InventoryManager.inventory.containsKey(inventoryManager.getPotion()) &&
                            !(mapsArray[currentMapIndex].getPlayer().getHealth() == mapsArray[currentMapIndex].getPlayer().getMaxHealth())) {
                        mapsArray[currentMapIndex].getPlayer().setHealth(mapsArray[currentMapIndex].getPlayer().getHealth() +
                                (mapsArray[currentMapIndex].getPlayer().getMaxHealth() / 2));
                        if (mapsArray[currentMapIndex].getPlayer().getHealth() > mapsArray[currentMapIndex].getPlayer().getMaxHealth()) {
                            mapsArray[currentMapIndex].getPlayer().setHealth(mapsArray[currentMapIndex].getPlayer().getMaxHealth());
                        }
                        inventoryManager.removeItemFromInventory(inventoryManager.getPotion());
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("You have no potion!");
                }
                refresh();
                break;
            case NUMPAD0:
                exportMap();
                break;
            case NUMPAD1:
                importMap();
                break;
            case F4:
                mapsArray[currentMapIndex].getPlayer().teleport(94, 20);
                refresh();
                break;
            case F9:
                mapsArray[currentMapIndex].getPlayer().teleport(62, 38);
                refresh();
                break;
            case E:
                mapsArray[currentMapIndex].getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                mapsArray[currentMapIndex].getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (mapsArray[currentMapIndex].getPlayer().getCellInFrontOfActor().getItem() instanceof InteractiveObject) {
                    int interactablesArrayCurrentIndex = 0;
                    Cell currentlyFocusedCell = mapsArray[currentMapIndex].getPlayer().getCellInFrontOfActor();
                    while (mapsArray[currentMapIndex].getInteractablesArray().size() > interactablesArrayCurrentIndex) {
                        InteractiveObject currentlyProcessedInteractable = mapsArray[currentMapIndex].getInteractablesArray().get(interactablesArrayCurrentIndex);
                        if (currentlyProcessedInteractable.isThisObjectInteractive() &&
                                currentlyProcessedInteractable.isThisInteractiveObjectCurrentlyBeingFocusedOn(currentlyFocusedCell) &&
                                currentlyProcessedInteractable.isPlayerInteractingFromLegalDirection(mapsArray[currentMapIndex].getPlayer().getCell())) {
                            currentlyProcessedInteractable.interact();
                            if (currentlyProcessedInteractable instanceof Switch && ((Switch) currentlyProcessedInteractable).getGroupName() != null) {
                                mapsArray[currentMapIndex].getSwitchablesCollection()
                                        .stream()
                                        .filter(x -> x.getGroupName() != null)
                                        .filter(x -> x.isThisFromTheSameGroup(((Switch) currentlyProcessedInteractable).getGroupName()))
                                        .forEach(InteractiveObject::interact);
                                //System.out.println(((Switch) currentlyProcessedInteractable).getGroupName());
                            }
                            if (currentlyProcessedInteractable.isMoveOnPossibleAfterInteraction() && !(currentlyProcessedInteractable instanceof Switch)) {
                                currentlyFocusedCell.setCellType(CellType.FLOOR);
                            }
                            refresh();
                            return;
                        } else {
                            interactablesArrayCurrentIndex++;
                        }
                    }
                } else if (mapsArray[currentMapIndex].getPlayer().getCell().getItem() != null && mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof PickupableItem) {
                    Item item = (Item) mapsArray[currentMapIndex].getPlayer().getCell().getItem();
                    inventoryManager.pickUpItem(item, mapsArray[currentMapIndex]);

                }
                if (mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) mapsArray[currentMapIndex].getPlayer().getCell().getItem()).activate();
                }
                /*else if (isThereAPickupableItemUnderThePlayer()) {
                    map.getPlayer().getCell().getItem().
                }*/
                refresh();
                break;
            case C:
                System.out.println(mapsArray[currentMapIndex].getPlayer().getCellInFrontOfActor().getCellType());
                if (mapsArray[currentMapIndex].getPlayer().getCellInFrontOfActor().getItem() instanceof Switch) {
                    System.out.println(((Switch) mapsArray[currentMapIndex].getPlayer().getCellInFrontOfActor().getItem()).getGroupName());
                }
                break;
            case N:
                mapsArray[currentMapIndex].getPlayer().getCellInFrontOfActor().setItem(new LootTable().getItemRareLoot().get(4));/*getMonsterCommonLoot().get(0));*/
                refresh();
                break;
            case F5:
                if (!isDeveloperStartingGearEnabled) {
                    mapsArray[currentMapIndex].getPlayer().raiseMaxHealth(17);
                    mapsArray[currentMapIndex].getPlayer().setHealth(mapsArray[currentMapIndex].getPlayer().getMaxHealth());
                    inventoryManager.pickUpItem(new LootTable().getWeaponRareLoot().get(1), mapsArray[currentMapIndex]);
                    inventoryManager.pickUpItem(new LootTable().getItemRareLoot().get(3), mapsArray[currentMapIndex]);
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
            case F6:
                if (!isDeveloperStartingGearEnabled) {
                    mapsArray[currentMapIndex].getPlayer().raiseMaxHealth(35);
                    mapsArray[currentMapIndex].getPlayer().setHealth(mapsArray[currentMapIndex].getPlayer().getMaxHealth());
                    inventoryManager.pickUpItem(new LootTable().getItemLegendaryLoot().get(3), mapsArray[currentMapIndex]);
                    inventoryManager.pickUpItem(new LootTable().getWeaponLegendaryLoot().get(1), mapsArray[currentMapIndex]);
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
//            case S: //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<NEW!
//                Player player = map.getPlayer();
//                dbManager.savePlayer(player);
//                break;

            case F12:
                mapsArray[currentMapIndex].getPlayer().setWallCheatOn(!mapsArray[currentMapIndex].getPlayer().isWallCheatOn());
                break;
            case F11:
                if (currentMapIndex == 2) {
                    break;
                } else {
                    mapsArray[currentMapIndex].getPlayer().saveStats();
                    currentMapIndex++;
                    currentAiIndex++;
                    MapLoader.loadMap(currentMapIndex);
                    mapsArray[currentMapIndex].getPlayer().loadStats();
                    mapsArray[currentMapIndex].getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
                    refresh();
                    break;
                }
            case F10:
                if (currentMapIndex == 0) {
                    break;
                } else {
                    mapsArray[currentMapIndex].getPlayer().saveStats();
                    currentMapIndex--;
                    currentAiIndex--;
                    MapLoader.loadMap(currentMapIndex);
                    mapsArray[currentMapIndex].getPlayer().loadStats();
                    mapsArray[currentMapIndex].getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
                    refresh();
                    break;
                }
            case F2:
                mapsArray[currentMapIndex].getMapStateSwitchers().stream().filter(x -> x instanceof TorchPuzzle).forEach(InteractiveObject::interact);
                refresh();
                break;
        }


        if (mapsArray[currentMapIndex].getPlayer().getHealth() <= 0) {
//            Sounds.playSound("Hdead");

            stage.setScene(gameOver.getGameOverScene());
        }
        if (mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof OpenedDoor || mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof Switch
                || mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof InteractiveObject || mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof EnvironmentalDamage) {
            pickUpButton.setDisable(true);
        } else {
            pickUpButton.setDisable(mapsArray[currentMapIndex].getPlayer().getCell().getItem() == null);
        }
        /*if (map.getBoss1() == null) {
            Sounds.playSound("Odead");
            gameOver.setVictory();
            stage.setScene(gameOver.getGameOverScene());
        }*/
        /*int soundChance = RandomGenerator.nextInt(100);
        if (soundChance < 1) {
            Sounds.playSound("Skeleton Move");
        } else if (soundChance < 2) {
            Sounds.playSound("Griffon1");
        } else if (soundChance < 3) {
            Sounds.playSound("Drready");
        }*/

    }

    private void refresh() {
        // context.setFill(Color.BLACK);
        // context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Tiles.drawParalaxx(context, mapsArray[currentMapIndex].getPlayer().getX(), mapsArray[currentMapIndex].getPlayer().getY());


        int dx = 7 - mapsArray[currentMapIndex].getPlayer().getX(); // 0;
        int dy = 7 - mapsArray[currentMapIndex].getPlayer().getY(); // 0;
/*
        if (map.getWidth()<16) {
            dx = (15-map.getWidth())/2;
        } else {
            dx = Math.min(0, 7 - map.getPlayer().getX());
            dx = Math.max(15 - map.getWidth()  , dx);
        }
        if (map.getHeight()<16) {
            dy = (15-map.getHeight())/2;
        } else {
            dy = Math.min(0, 7 - map.getPlayer().getY());
            dy = Math.max(15 - map.getHeight() , dy);
        }
*/
        for (int x = 0; x < mapsArray[currentMapIndex].getWidth(); x++) {
            for (int y = 0; y < mapsArray[currentMapIndex].getHeight(); y++) {
                Cell cell = mapsArray[currentMapIndex].getCell(x, y);
                Tiles.drawTile(context, cell, x + dx, y + dy);

            }
        }
        for (int x = 0; x < mapsArray[currentMapIndex].getWidth(); x++) {
            for (int y = 0; y < mapsArray[currentMapIndex].getHeight(); y++) {
                Cell cell = mapsArray[currentMapIndex].getCell(x, y);
                if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x + dx, y + dy);
                }
                if (cell.getActor() != null) {
                    if (cell.getActor().getTileName().contains("duck")) {
                        Tiles.draw3xTile(context, cell.getActor(), x + dx, y + dy);
                    } else {
                        Tiles.drawTile(context, cell.getActor(), x + dx, y + dy);
                    }
                }


                if (!(mapsArray[currentMapIndex].getPlayer().getTileName().equals("playerArmored2")) && mapsArray[currentMapIndex].getPlayer().getArmor() > 6) {
                    mapsArray[currentMapIndex].getPlayer().setTileName("playerArmored1");
                }
                if (mapsArray[currentMapIndex].getPlayer().getArmor() >= 13) {
                    mapsArray[currentMapIndex].getPlayer().setTileName("playerArmored2");
                }


            }
        }
        // Tiles.drawTile(context, map.getPlayer().getCell().getActor(), map.getPlayer().getX() + dx, map.getPlayer().getY() + dy);
        managePlayerStatistics();
    }

    private void managePlayerStatistics() {
        manageAttackPw();
        healthLabel.setText("" + mapsArray[currentMapIndex].getPlayer().getHealth() + "/" +
                mapsArray[currentMapIndex].getPlayer().getMaxHealth());
        armorLabel.setText("" + mapsArray[currentMapIndex].getPlayer().getArmor());
        moneyLabel.setText("" + mapsArray[currentMapIndex].getPlayer().getMoneyAmount());
        manageCombatLog();
    }

    private void manageAttackPw() {
        if (InventoryManager.inventory.keySet().stream().anyMatch(item -> item instanceof Weapon)) {
            attackPwLabel.setText((mapsArray[currentMapIndex].getPlayer().getAttackPower()
                    - inventoryManager.getCurrentWeapon().getAttackpowerIncrease()) + "+"
                    + inventoryManager.getCurrentWeapon().getAttackpowerIncrease());
        } else {
            attackPwLabel.setText(String.valueOf(mapsArray[currentMapIndex].getPlayer().getAttackPower()));
        }
    }

    private void manageCombatLog() {
//        combatLog.setText("Combat Log:\n");
        for (CombatEvent combatEvent : combatEvents) {
            combatLog.setText(combatLog.getText() + combatEvent.getLog().toString());
            combatLog.positionCaret(combatLog.getText().length());
        }
        combatEvents.clear();
    }

/*    private boolean isThereAnInteractiveObjectAroundThePlayer() {
        if (mapsArray[currentMapIndex].getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject ||
                mapsArray[currentMapIndex].getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject ||
                mapsArray[currentMapIndex].getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject ||
                mapsArray[currentMapIndex].getPlayer().getCell().getNeighbor(0, -1).getItem() instanceof InteractiveObject) {
            return true;
        } else {
            return false;
        }
    }

    private int[] getTheInteractiveEntityDirection() {
        if (mapsArray[currentMapIndex].getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{1, 0};
        } else if (mapsArray[currentMapIndex].getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{-1, 0};
        } else if (mapsArray[currentMapIndex].getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject) {
            return new int[]{0, 1};
        } else {
            return new int[]{0, -1};
        }
    }*/

    /*private boolean isThereAPickupableItemUnderThePlayer() {
        return map.getPlayer().getCell().getItem() instanceof PickupableItem;
    }*/

    private void playerSuffersEnvironmentalDamage() {
//        Player.playHurtSound();
        mapsArray[currentMapIndex].getPlayer().playerHit();
        ((EnvironmentalDamage) mapsArray[currentMapIndex].getPlayer().getCell().getItem()).playDamageSound();
        mapsArray[currentMapIndex].getPlayer().lowerHealth(mapsArray[currentMapIndex].getPlayer().getCell().getItem().getAttackPower());
        if (mapsArray[currentMapIndex].getPlayer().getHealth() < 1) {
            mapsArray[currentMapIndex].getPlayer().playDeathSound();
        }
    }

    public boolean isPlayerBeingAffectedByAnEnvironmentalDamageSource() {
        return mapsArray[currentMapIndex].getPlayer().getCell().getItem() instanceof EnvironmentalDamage && mapsArray[currentMapIndex].getPlayer().getCell().getItem().getAttackPower() > 0;
    }

    public static int getCurrentMapIndex() {
        return currentMapIndex;
    }

    public static int getCurrentAiIndex() {
        return currentAiIndex;
    }

    public static void setCurrentMapIndex(int currentMapIndex) {
        Main.currentMapIndex = currentMapIndex;
    }

    public static void setCurrentAiIndex(int currentAiIndex) {
        Main.currentAiIndex = currentAiIndex;
    }

    public static GameMap cheatingMapGetter() {
        return mapsArray[currentMapIndex];
    }

    public static AiMovement aiGetter() {
        return AiArray[currentAiIndex];
    }

   /* private boolean isPlayerSufferingEnvironmentalDamage() {
        if (map.getPlayer().getCell().getTileName().equals("trapActive")) {
            return true;
        } else {
            return false;
        }
    }*/

    /*private void environmentalDamageTick() {
        if (map.getPlayer().getCell().getTileName().equals("trapActive")) {
            map.getPlayer().setHealth(map.getPlayer().getHealth() - 2);
        }
    }*/

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    /* NEW STUFF!!!!!!!!!!!!!
    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }NEW STUFF!!!!!!!!!!!!!*/

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
                out.writeObject(mapsArray);
                out.close();
                fileOut.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    public void importMap() {
        System.out.println("import game");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Saved game file (*.sre)", "*.sre"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String fileName = file.getAbsolutePath();
            System.out.println(fileName);
            try {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                mapsArray = (GameMap[]) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("Load class not found");
                c.printStackTrace();
            }
        }

        // fix actors,items
        for (GameMap map : mapsArray) {
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
        for (GameMap map : mapsArray) {
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    map.getCell(x, y).setMap(map);
                }
            }
        }
        // fix AI
        for (int id = 0; id < mapsArray.length; id++) {
            AiArray[id] = new AiMovement(mapsArray[id]);
        }
        refresh();
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

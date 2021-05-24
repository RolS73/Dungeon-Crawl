package dungeoncrawl;

import dungeoncrawl.dao.GameDatabaseManager;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.actors.items.enviromentalHazards.EnvironmentalDamage;
import dungeoncrawl.logic.actors.items.enviromentalHazards.ProjectileCycle;
import dungeoncrawl.logic.actors.items.enviromentalHazards.TrapCycle;
import dungeoncrawl.logic.actors.items.interactablilty.*;
import dungeoncrawl.logic.actors.items.looting.Item;
import dungeoncrawl.logic.actors.items.looting.loottable.EveryItem;
import dungeoncrawl.logic.actors.items.looting.PickupableItem;
import dungeoncrawl.logic.*;
import dungeoncrawl.logic.maps.Maps;
import dungeoncrawl.screens.game.ui.UserInterface;
import dungeoncrawl.screens.gameover.GameOver;
import dungeoncrawl.screens.startmenu.Menu;
import dungeoncrawl.serializer.Serializer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Main extends Application {

    boolean isDeveloperStartingGearEnabled;

    @Getter private static Maps maps = new Maps();
    @Getter private static final InventoryManager INVENTORY_MANAGER = new InventoryManager();

    private final Menu menu = new Menu();
    private final GameOver gameOver = new GameOver();

    @Getter private static final UserInterface UI = new UserInterface();

    Canvas canvas = new Canvas(
            15 * Tiles.TILE_WIDTH,
            15 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    private Stage stage;

    private final List<String> wallCheat = Arrays.asList("Laci", "Ricsi", "Roland", "Szabolcs", "George");

    GameDatabaseManager dbManager; //Sprint 2-ből

    Serializer serializer = new Serializer(stage);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        setupDbManager(); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< THIS IS NEW!
        
        /*if (maps.getCurrentMapIndex() == 0) {
            SetInteractableItems.setStuff(0); //Map1 interactables
        }*/

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        borderPane.setCenter(canvas);
        borderPane.setRight(UI.getUi());

        primaryStage.setScene(menu.getMenuScreen());

        menu.getImportButton().setOnAction(i -> {
            maps = serializer.importMap();
            primaryStage.setScene(scene);
            refresh();
        });

        menu.getPlayButton().setOnAction(play -> {
            primaryStage.setScene(scene);
            UI.getName().setText(menu.getPlayerName().getText());
            getCurrentMap().getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
            if (wallCheat.contains(UI.getName().getText())) {
                getCurrentMap().getPlayer().setWallCheatOn(true);
            }
        });

        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                getCurrentMap().getPlayer().setTileName("playerU");
                getCurrentMap().getPlayer().move(0, -1);
                getCurrentMap().getPlayer().updateActorOrientation();
                maps.getCurrentAi().monsterMover();
                getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case DOWN:
            case S:
                if (!keyEvent.isControlDown()) {
                    getCurrentMap().getPlayer().setTileName("playerD");
                    getCurrentMap().getPlayer().move(0, 1);
                    getCurrentMap().getPlayer().updateActorOrientation();
                    maps.getCurrentAi().monsterMover();
                    getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                    getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                    getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                    if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                        playerSuffersEnvironmentalDamage();
                    }
                    if (getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                        ((StepOnActivatable) getCurrentMap().getPlayer().getCell().getItem()).activate();
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
                getCurrentMap().getPlayer().setTileName("playerL");
                getCurrentMap().getPlayer().move(-1, 0);
                getCurrentMap().getPlayer().updateActorOrientation();
                maps.getCurrentAi().monsterMover();
                getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case RIGHT:
            case D:
                getCurrentMap().getPlayer().setTileName("playerR");
                getCurrentMap().getPlayer().move(1, 0);
                getCurrentMap().getPlayer().updateActorOrientation();
                maps.getCurrentAi().monsterMover();
                getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case SPACE:
                maps.getCurrentAi().monsterMover();
                getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                refresh();
                //System.out.println("Player X Coordinate: " + getCurrentMap().getPlayer().getX() + "\n" + "Player Y Coordinate: " + getCurrentMap().getPlayer().getY());
                break;
            case Q:
                try {
                    if (InventoryManager.inventory.containsKey(INVENTORY_MANAGER.getPotion()) &&
                            !(getCurrentMap().getPlayer().getHealth() == getCurrentMap().getPlayer().getMaxHealth())) {
                        getCurrentMap().getPlayer().setHealth(getCurrentMap().getPlayer().getHealth() +
                                (getCurrentMap().getPlayer().getMaxHealth() / 2));
                        if (getCurrentMap().getPlayer().getHealth() > getCurrentMap().getPlayer().getMaxHealth()) {
                            getCurrentMap().getPlayer().setHealth(getCurrentMap().getPlayer().getMaxHealth());
                        }
                        INVENTORY_MANAGER.removeItemFromInventory(INVENTORY_MANAGER.getPotion());
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("You have no potion!");
                }
                refresh();
                break;
            case NUMPAD0:
                serializer.exportMap();
                break;
            case NUMPAD1:
                maps = serializer.importMap();
                refresh();
                break;
            case F4:
                getCurrentMap().getPlayer().teleport(94, 20);
                refresh();
                break;
            case F9:
                getCurrentMap().getPlayer().teleport(62, 38);
                refresh();
                break;
            case E:
                getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (getCurrentMap().getPlayer().getCellInFrontOfActor().getItem() instanceof InteractiveObject) {
                    int interactablesArrayCurrentIndex = 0;
                    dungeoncrawl.logic.Cell currentlyFocusedCell = getCurrentMap().getPlayer().getCellInFrontOfActor();
                    while (getCurrentMap().getInteractablesArray().size() > interactablesArrayCurrentIndex) {
                        InteractiveObject currentlyProcessedInteractable = getCurrentMap().getInteractablesArray().get(interactablesArrayCurrentIndex);
                        if (currentlyProcessedInteractable.isThisObjectInteractive() &&
                                currentlyProcessedInteractable.isThisInteractiveObjectCurrentlyBeingFocusedOn(currentlyFocusedCell) &&
                                currentlyProcessedInteractable.isPlayerInteractingFromLegalDirection(getCurrentMap().getPlayer().getCell())) {
                            currentlyProcessedInteractable.interact();
                            if (currentlyProcessedInteractable instanceof Switch && ((Switch) currentlyProcessedInteractable).getGroupName() != null) {
                                getCurrentMap().getSwitchablesCollection()
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
                } else if (getCurrentMap().getPlayer().getCell().getItem() != null && getCurrentMap().getPlayer().getCell().getItem() instanceof PickupableItem) {
                    Item item = (Item) getCurrentMap().getPlayer().getCell().getItem();
                    INVENTORY_MANAGER.pickUpItem(item, getCurrentMap());

                }
                if (getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                /*else if (isThereAPickupableItemUnderThePlayer()) {
                    map.getPlayer().getCell().getItem().
                }*/
                refresh();
                break;
            case C:
                System.out.println("CellType: " + getCurrentMap().getPlayer().getCellInFrontOfActor().getCellType());
                if (getCurrentMap().getPlayer().getCellInFrontOfActor().getItem() instanceof Switch) {
                    System.out.println("InterfaceGroupName: " + ((Switch) getCurrentMap().getPlayer().getCellInFrontOfActor().getItem()).getGroupName());
                }
                if (getCurrentMap().getPlayer().getCellInFrontOfActor().getItem() instanceof TeleportOnCurrentMap) {
                    TeleportOnCurrentMap targetEntity = (TeleportOnCurrentMap) getCurrentMap().getPlayer().getCellInFrontOfActor().getItem();
                    System.out.println("Cell Pair Coordinates: " + ((TeleportOnCurrentMap) getCurrentMap().getPlayer().getCellInFrontOfActor().getItem()).getPairIdentifier());
                }
                break;
            case N:
                getCurrentMap().getPlayer().getCellInFrontOfActor().setItem(EveryItem.getInstance().getItemRareLoot().get(4));
                refresh();
                break;
            case F5:
                if (!isDeveloperStartingGearEnabled) {
                    getCurrentMap().getPlayer().raiseMaxHealth(17);
                    getCurrentMap().getPlayer().setHealth(getCurrentMap().getPlayer().getMaxHealth());
                    INVENTORY_MANAGER.pickUpItem(EveryItem.getInstance().getWeaponRareLoot().get(1), getCurrentMap());
                    INVENTORY_MANAGER.pickUpItem(EveryItem.getInstance().getItemRareLoot().get(3), getCurrentMap());
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
            case F6:
                if (!isDeveloperStartingGearEnabled) {
                    getCurrentMap().getPlayer().raiseMaxHealth(35);
                    getCurrentMap().getPlayer().setHealth(getCurrentMap().getPlayer().getMaxHealth());
                    INVENTORY_MANAGER.pickUpItem(EveryItem.getInstance().getItemLegendaryLoot().get(3), getCurrentMap());
                    INVENTORY_MANAGER.pickUpItem(EveryItem.getInstance().getWeaponLegendaryLoot().get(1), getCurrentMap());
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
//            case S: //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<NEW!
//                Player player = map.getPlayer();
//                dbManager.savePlayer(player);
//                break;

            case F12:
                getCurrentMap().getPlayer().setWallCheatOn(!getCurrentMap().getPlayer().isWallCheatOn());
                break;
            case F11:
                getCurrentMap().getPlayer().saveStats();
                maps.incrementCurrentMapIndex();
                MapLoader.loadMap(maps.getCurrentMapIndex());
                getCurrentMap().getPlayer().loadStats();
                getCurrentMap().getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
                refresh();
                break;
            case F10:
                getCurrentMap().getPlayer().saveStats();
                maps.decrementCurrentMapIndex();
                MapLoader.loadMap(maps.getCurrentMapIndex());
                getCurrentMap().getPlayer().loadStats();
                getCurrentMap().getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
                refresh();
                break;
            case F2:
                getCurrentMap().getMapStateSwitchers().stream().filter(x -> x instanceof TorchPuzzle).forEach(InteractiveObject::interact);
                refresh();
                break;
        }


        if (getCurrentMap().getPlayer().getHealth() <= 0) {
            stage.setScene(gameOver.getGameOverScene());
        }
    }

    private void refresh() {
        // context.setFill(Color.BLACK);
        // context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Tiles.drawParalaxx(context, getCurrentMap().getPlayer().getX(), getCurrentMap().getPlayer().getY());


        int dx = 7 - getCurrentMap().getPlayer().getX(); // 0;
        int dy = 7 - getCurrentMap().getPlayer().getY(); // 0;
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
        for (int x = 0; x < getCurrentMap().getWidth(); x++) {
            for (int y = 0; y < getCurrentMap().getHeight(); y++) {
                dungeoncrawl.logic.Cell cell = getCurrentMap().getCell(x, y);
                Tiles.drawTile(context, cell, x + dx, y + dy);

            }
        }
        for (int x = 0; x < getCurrentMap().getWidth(); x++) {
            for (int y = 0; y < getCurrentMap().getHeight(); y++) {
                Cell cell = getCurrentMap().getCell(x, y);
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


                if (!(getCurrentMap().getPlayer().getTileName().equals("playerArmored2")) && getCurrentMap().getPlayer().getArmor() > 6) {
                    getCurrentMap().getPlayer().setTileName("playerArmored1");
                }
                if (getCurrentMap().getPlayer().getArmor() >= 13) {
                    getCurrentMap().getPlayer().setTileName("playerArmored2");
                }


            }
        }
        // Tiles.drawTile(context, map.getPlayer().getCell().getActor(), map.getPlayer().getX() + dx, map.getPlayer().getY() + dy);
        UI.managePlayerStatistics();
    }

/*    private boolean isThereAnInteractiveObjectAroundThePlayer() {
        if (getCurrentMap().getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject ||
                getCurrentMap().getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject ||
                getCurrentMap().getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject ||
                getCurrentMap().getPlayer().getCell().getNeighbor(0, -1).getItem() instanceof InteractiveObject) {
            return true;
        } else {
            return false;
        }
    }

    private int[] getTheInteractiveEntityDirection() {
        if (getCurrentMap().getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{1, 0};
        } else if (getCurrentMap().getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{-1, 0};
        } else if (getCurrentMap().getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject) {
            return new int[]{0, 1};
        } else {
            return new int[]{0, -1};
        }
    }*/

    private void playerSuffersEnvironmentalDamage() {
        getCurrentMap().getPlayer().playerHit();
        ((EnvironmentalDamage) getCurrentMap().getPlayer().getCell().getItem()).playDamageSound();
        getCurrentMap().getPlayer().lowerHealth(getCurrentMap().getPlayer().getCell().getItem().getAttackPower());
        if (getCurrentMap().getPlayer().getHealth() < 1) {
            getCurrentMap().getPlayer().playDeathSound();
        }
    }

    public boolean isPlayerBeingAffectedByAnEnvironmentalDamageSource() {
        return getCurrentMap().getPlayer().getCell().getItem() instanceof EnvironmentalDamage && getCurrentMap().getPlayer().getCell().getItem().getAttackPower() > 0;
    }

    public static GameMap getCurrentMap() { //getMap?
        return maps.getCurrentMap();
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
}
package dungeoncrawl;

import dungeoncrawl.dao.GameDatabaseManager;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.actors.items.enviromentalHazards.EnvironmentalDamage;
import dungeoncrawl.logic.actors.items.enviromentalHazards.ProjectileCycle;
import dungeoncrawl.logic.actors.items.enviromentalHazards.TrapCycle;
import dungeoncrawl.logic.actors.items.interactablilty.*;
import dungeoncrawl.logic.actors.items.looting.Item;
import dungeoncrawl.logic.actors.items.looting.LootTable;
import dungeoncrawl.logic.actors.items.looting.PickupableItem;
import dungeoncrawl.logic.*;
import dungeoncrawl.maps.Maps;
import dungeoncrawl.screens.game.ui.UserInterface;
import dungeoncrawl.screens.gameover.GameOver;
import dungeoncrawl.screens.startmenu.Menu;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Main extends Application {

    boolean isDeveloperStartingGearEnabled;

    @Getter @Setter private static int currentMapIndex = 0;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        if (currentMapIndex == 0) {
            SetInteractableItems.setStuff(0); //Map1 interactables
        }

        BorderPane borderPane = new BorderPane();

        Scene scene = new Scene(borderPane);

        menu.getImportButton().setOnAction(i -> {
            importMap();
            primaryStage.setScene(scene);
        });

        setupDbManager(); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< THIS IS NEW!

        borderPane.setCenter(canvas);
        borderPane.setRight(UI.getUi());

        primaryStage.setScene(menu.getMenuScreen());

        menu.getPlayButton().setOnAction(play -> {
            primaryStage.setScene(scene);
            UI.getName().setText(menu.getPlayerName().getText());
            maps.getMapList().get(currentMapIndex).getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
            if (wallCheat.contains(UI.getName().getText())) {
                maps.getMapList().get(currentMapIndex).getPlayer().setWallCheatOn(true);
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
                maps.getMapList().get(currentMapIndex).getPlayer().setTileName("playerU");
                maps.getMapList().get(currentMapIndex).getPlayer().move(0, -1);
                maps.getMapList().get(currentMapIndex).getPlayer().updateActorOrientation();
                maps.getAiList().get(currentMapIndex).monsterMover();
                maps.getMapList().get(currentMapIndex).getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case DOWN:
            case S:
                if (!keyEvent.isControlDown()) {
                    maps.getMapList().get(currentMapIndex).getPlayer().setTileName("playerD");
                    maps.getMapList().get(currentMapIndex).getPlayer().move(0, 1);
                    maps.getMapList().get(currentMapIndex).getPlayer().updateActorOrientation();
                    maps.getAiList().get(currentMapIndex).monsterMover();
                    maps.getMapList().get(currentMapIndex).getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                    maps.getMapList().get(currentMapIndex).getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                    maps.getMapList().get(currentMapIndex).getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                    if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                        playerSuffersEnvironmentalDamage();
                    }
                    if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                        ((StepOnActivatable) maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem()).activate();
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
                maps.getMapList().get(currentMapIndex).getPlayer().setTileName("playerL");
                maps.getMapList().get(currentMapIndex).getPlayer().move(-1, 0);
                maps.getMapList().get(currentMapIndex).getPlayer().updateActorOrientation();
                maps.getAiList().get(currentMapIndex).monsterMover();
                maps.getMapList().get(currentMapIndex).getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case RIGHT:
            case D:
                maps.getMapList().get(currentMapIndex).getPlayer().setTileName("playerR");
                maps.getMapList().get(currentMapIndex).getPlayer().move(1, 0);
                maps.getMapList().get(currentMapIndex).getPlayer().updateActorOrientation();
                maps.getAiList().get(currentMapIndex).monsterMover();
                maps.getMapList().get(currentMapIndex).getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case SPACE:
                maps.getAiList().get(currentMapIndex).monsterMover();
                maps.getMapList().get(currentMapIndex).getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem()).activate();
                }
                refresh();
                //System.out.println("Player X Coordinate: " + maps.getMapList().get(currentMapIndex).getPlayer().getX() + "\n" + "Player Y Coordinate: " + maps.getMapList().get(currentMapIndex).getPlayer().getY());
                break;
            case Q:
                try {
                    if (InventoryManager.inventory.containsKey(INVENTORY_MANAGER.getPotion()) &&
                            !(maps.getMapList().get(currentMapIndex).getPlayer().getHealth() == maps.getMapList().get(currentMapIndex).getPlayer().getMaxHealth())) {
                        maps.getMapList().get(currentMapIndex).getPlayer().setHealth(maps.getMapList().get(currentMapIndex).getPlayer().getHealth() +
                                (maps.getMapList().get(currentMapIndex).getPlayer().getMaxHealth() / 2));
                        if (maps.getMapList().get(currentMapIndex).getPlayer().getHealth() > maps.getMapList().get(currentMapIndex).getPlayer().getMaxHealth()) {
                            maps.getMapList().get(currentMapIndex).getPlayer().setHealth(maps.getMapList().get(currentMapIndex).getPlayer().getMaxHealth());
                        }
                        INVENTORY_MANAGER.removeItemFromInventory(INVENTORY_MANAGER.getPotion());
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("You have no potion!");
                }
                refresh();
                break;
            case F1/*NUMPAD0*/:
                exportMap();
                break;
            case F3/*NUMPAD1*/:
                importMap();
                break;
            case F4:
                maps.getMapList().get(currentMapIndex).getPlayer().teleport(94, 20);
                refresh();
                break;
            case F9:
                maps.getMapList().get(currentMapIndex).getPlayer().teleport(62, 38);
                refresh();
                break;
            case E:
                maps.getMapList().get(currentMapIndex).getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getMapList().get(currentMapIndex).getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getMapList().get(currentMapIndex).getPlayer().getCellInFrontOfActor().getItem() instanceof InteractiveObject) {
                    int interactablesArrayCurrentIndex = 0;
                    dungeoncrawl.logic.Cell currentlyFocusedCell = maps.getMapList().get(currentMapIndex).getPlayer().getCellInFrontOfActor();
                    while (maps.getMapList().get(currentMapIndex).getInteractablesArray().size() > interactablesArrayCurrentIndex) {
                        InteractiveObject currentlyProcessedInteractable = maps.getMapList().get(currentMapIndex).getInteractablesArray().get(interactablesArrayCurrentIndex);
                        if (currentlyProcessedInteractable.isThisObjectInteractive() &&
                                currentlyProcessedInteractable.isThisInteractiveObjectCurrentlyBeingFocusedOn(currentlyFocusedCell) &&
                                currentlyProcessedInteractable.isPlayerInteractingFromLegalDirection(maps.getMapList().get(currentMapIndex).getPlayer().getCell())) {
                            currentlyProcessedInteractable.interact();
                            if (currentlyProcessedInteractable instanceof Switch && ((Switch) currentlyProcessedInteractable).getGroupName() != null) {
                                maps.getMapList().get(currentMapIndex).getSwitchablesCollection()
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
                } else if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() != null && maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() instanceof PickupableItem) {
                    Item item = (Item) maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem();
                    INVENTORY_MANAGER.pickUpItem(item, maps.getMapList().get(currentMapIndex));

                }
                if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem()).activate();
                }
                /*else if (isThereAPickupableItemUnderThePlayer()) {
                    map.getPlayer().getCell().getItem().
                }*/
                refresh();
                break;
            case C:
                System.out.println(maps.getMapList().get(currentMapIndex).getPlayer().getCellInFrontOfActor().getCellType());
                if (maps.getMapList().get(currentMapIndex).getPlayer().getCellInFrontOfActor().getItem() instanceof Switch) {
                    System.out.println(((Switch) maps.getMapList().get(currentMapIndex).getPlayer().getCellInFrontOfActor().getItem()).getGroupName());
                }
                break;
            case N:
                maps.getMapList().get(currentMapIndex).getPlayer().getCellInFrontOfActor().setItem(new LootTable().getItemRareLoot().get(4));/*getMonsterCommonLoot().get(0));*/
                refresh();
                break;
            case F5:
                if (!isDeveloperStartingGearEnabled) {
                    maps.getMapList().get(currentMapIndex).getPlayer().raiseMaxHealth(17);
                    maps.getMapList().get(currentMapIndex).getPlayer().setHealth(maps.getMapList().get(currentMapIndex).getPlayer().getMaxHealth());
                    INVENTORY_MANAGER.pickUpItem(new LootTable().getWeaponRareLoot().get(1), maps.getMapList().get(currentMapIndex));
                    INVENTORY_MANAGER.pickUpItem(new LootTable().getItemRareLoot().get(3), maps.getMapList().get(currentMapIndex));
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
            case F6:
                if (!isDeveloperStartingGearEnabled) {
                    maps.getMapList().get(currentMapIndex).getPlayer().raiseMaxHealth(35);
                    maps.getMapList().get(currentMapIndex).getPlayer().setHealth(maps.getMapList().get(currentMapIndex).getPlayer().getMaxHealth());
                    INVENTORY_MANAGER.pickUpItem(new LootTable().getItemLegendaryLoot().get(3), maps.getMapList().get(currentMapIndex));
                    INVENTORY_MANAGER.pickUpItem(new LootTable().getWeaponLegendaryLoot().get(1), maps.getMapList().get(currentMapIndex));
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
//            case S: //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<NEW!
//                Player player = map.getPlayer();
//                dbManager.savePlayer(player);
//                break;

            case F12:
                maps.getMapList().get(currentMapIndex).getPlayer().setWallCheatOn(!maps.getMapList().get(currentMapIndex).getPlayer().isWallCheatOn());
                break;
            case F11:
                if (currentMapIndex == 2) {
                    break;
                } else {
                    maps.getMapList().get(currentMapIndex).getPlayer().saveStats();
                    currentMapIndex++;
                    MapLoader.loadMap(currentMapIndex);
                    maps.getMapList().get(currentMapIndex).getPlayer().loadStats();
                    maps.getMapList().get(currentMapIndex).getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
                    refresh();
                    break;
                }
            case F10:
                if (currentMapIndex == 0) {
                    break;
                } else {
                    maps.getMapList().get(currentMapIndex).getPlayer().saveStats();
                    currentMapIndex--;
                    MapLoader.loadMap(currentMapIndex);
                    maps.getMapList().get(currentMapIndex).getPlayer().loadStats();
                    maps.getMapList().get(currentMapIndex).getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
                    refresh();
                    break;
                }
            case F2:
                maps.getMapList().get(currentMapIndex).getMapStateSwitchers().stream().filter(x -> x instanceof TorchPuzzle).forEach(InteractiveObject::interact);
                refresh();
                break;
        }


        if (maps.getMapList().get(currentMapIndex).getPlayer().getHealth() <= 0) {
//            Sounds.playSound("Hdead");

            stage.setScene(gameOver.getGameOverScene());
        }
        /*if (map.getBoss1() == null) {
            Sounds.playSound("Odead");
            gameOver.setVictory();
            stage.setScene(gameOver.getGameOverScene());
        }*/
    }

    private void refresh() {
        // context.setFill(Color.BLACK);
        // context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Tiles.drawParalaxx(context, maps.getMapList().get(currentMapIndex).getPlayer().getX(), maps.getMapList().get(currentMapIndex).getPlayer().getY());


        int dx = 7 - maps.getMapList().get(currentMapIndex).getPlayer().getX(); // 0;
        int dy = 7 - maps.getMapList().get(currentMapIndex).getPlayer().getY(); // 0;
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
        for (int x = 0; x < maps.getMapList().get(currentMapIndex).getWidth(); x++) {
            for (int y = 0; y < maps.getMapList().get(currentMapIndex).getHeight(); y++) {
                dungeoncrawl.logic.Cell cell = maps.getMapList().get(currentMapIndex).getCell(x, y);
                Tiles.drawTile(context, cell, x + dx, y + dy);

            }
        }
        for (int x = 0; x < maps.getMapList().get(currentMapIndex).getWidth(); x++) {
            for (int y = 0; y < maps.getMapList().get(currentMapIndex).getHeight(); y++) {
                Cell cell = maps.getMapList().get(currentMapIndex).getCell(x, y);
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


                if (!(maps.getMapList().get(currentMapIndex).getPlayer().getTileName().equals("playerArmored2")) && maps.getMapList().get(currentMapIndex).getPlayer().getArmor() > 6) {
                    maps.getMapList().get(currentMapIndex).getPlayer().setTileName("playerArmored1");
                }
                if (maps.getMapList().get(currentMapIndex).getPlayer().getArmor() >= 13) {
                    maps.getMapList().get(currentMapIndex).getPlayer().setTileName("playerArmored2");
                }


            }
        }
        // Tiles.drawTile(context, map.getPlayer().getCell().getActor(), map.getPlayer().getX() + dx, map.getPlayer().getY() + dy);
        UI.managePlayerStatistics();
    }

/*    private boolean isThereAnInteractiveObjectAroundThePlayer() {
        if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject ||
                maps.getMapList().get(currentMapIndex).getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject ||
                maps.getMapList().get(currentMapIndex).getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject ||
                maps.getMapList().get(currentMapIndex).getPlayer().getCell().getNeighbor(0, -1).getItem() instanceof InteractiveObject) {
            return true;
        } else {
            return false;
        }
    }

    private int[] getTheInteractiveEntityDirection() {
        if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{1, 0};
        } else if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{-1, 0};
        } else if (maps.getMapList().get(currentMapIndex).getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject) {
            return new int[]{0, 1};
        } else {
            return new int[]{0, -1};
        }
    }*/

    private void playerSuffersEnvironmentalDamage() {
//        Player.playHurtSound();
        maps.getMapList().get(currentMapIndex).getPlayer().playerHit();
        ((EnvironmentalDamage) maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem()).playDamageSound();
        maps.getMapList().get(currentMapIndex).getPlayer().lowerHealth(maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem().getAttackPower());
        if (maps.getMapList().get(currentMapIndex).getPlayer().getHealth() < 1) {
            maps.getMapList().get(currentMapIndex).getPlayer().playDeathSound();
        }
    }

    public boolean isPlayerBeingAffectedByAnEnvironmentalDamageSource() {
        return maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem() instanceof EnvironmentalDamage && maps.getMapList().get(currentMapIndex).getPlayer().getCell().getItem().getAttackPower() > 0;
    }

    public static GameMap cheatingMapGetter() { //getMap?
        return maps.getMapList().get(currentMapIndex);
    }

    public static AiMovement aiGetter() {
        return maps.getAiList().get(currentMapIndex);
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
                out.writeObject(maps.getMapList());
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
                maps = new Maps((List<GameMap>) in.readObject());
                in.close();
                fileIn.close();
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
        // fix AI
        /* This is done in Maps' constructor
        }*/
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

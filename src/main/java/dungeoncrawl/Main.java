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

import java.io.*;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        
        setupDbManager(); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< THIS IS NEW!
        
        if (maps.getCurrentMapIndex() == 0) {
            SetInteractableItems.setStuff(0); //Map1 interactables
        }

        BorderPane borderPane = new BorderPane();

        Scene scene = new Scene(borderPane);

        menu.getImportButton().setOnAction(i -> {
            importMap();
            primaryStage.setScene(scene);
        });

        

        borderPane.setCenter(canvas);
        borderPane.setRight(UI.getUi());

        primaryStage.setScene(menu.getMenuScreen());

        menu.getPlayButton().setOnAction(play -> {
            primaryStage.setScene(scene);
            UI.getName().setText(menu.getPlayerName().getText());
            maps.getCurrentMap().getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
            if (wallCheat.contains(UI.getName().getText())) {
                maps.getCurrentMap().getPlayer().setWallCheatOn(true);
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
                maps.getCurrentMap().getPlayer().setTileName("playerU");
                maps.getCurrentMap().getPlayer().move(0, -1);
                maps.getCurrentMap().getPlayer().updateActorOrientation();
                maps.getCurrentAi().monsterMover();
                maps.getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case DOWN:
            case S:
                if (!keyEvent.isControlDown()) {
                    maps.getCurrentMap().getPlayer().setTileName("playerD");
                    maps.getCurrentMap().getPlayer().move(0, 1);
                    maps.getCurrentMap().getPlayer().updateActorOrientation();
                    maps.getCurrentAi().monsterMover();
                    maps.getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                    maps.getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                    maps.getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                    if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                        playerSuffersEnvironmentalDamage();
                    }
                    if (maps.getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                        ((StepOnActivatable) maps.getCurrentMap().getPlayer().getCell().getItem()).activate();
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
                maps.getCurrentMap().getPlayer().setTileName("playerL");
                maps.getCurrentMap().getPlayer().move(-1, 0);
                maps.getCurrentMap().getPlayer().updateActorOrientation();
                maps.getCurrentAi().monsterMover();
                maps.getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case RIGHT:
            case D:
                maps.getCurrentMap().getPlayer().setTileName("playerR");
                maps.getCurrentMap().getPlayer().move(1, 0);
                maps.getCurrentMap().getPlayer().updateActorOrientation();
                maps.getCurrentAi().monsterMover();
                maps.getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case SPACE:
                maps.getCurrentAi().monsterMover();
                maps.getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                refresh();
                //System.out.println("Player X Coordinate: " + maps.getCurrentMap().getPlayer().getX() + "\n" + "Player Y Coordinate: " + maps.getCurrentMap().getPlayer().getY());
                break;
            case Q:
                try {
                    if (InventoryManager.inventory.containsKey(INVENTORY_MANAGER.getPotion()) &&
                            !(maps.getCurrentMap().getPlayer().getHealth() == maps.getCurrentMap().getPlayer().getMaxHealth())) {
                        maps.getCurrentMap().getPlayer().setHealth(maps.getCurrentMap().getPlayer().getHealth() +
                                (maps.getCurrentMap().getPlayer().getMaxHealth() / 2));
                        if (maps.getCurrentMap().getPlayer().getHealth() > maps.getCurrentMap().getPlayer().getMaxHealth()) {
                            maps.getCurrentMap().getPlayer().setHealth(maps.getCurrentMap().getPlayer().getMaxHealth());
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
                maps.getCurrentMap().getPlayer().teleport(94, 20);
                refresh();
                break;
            case F9:
                maps.getCurrentMap().getPlayer().teleport(62, 38);
                refresh();
                break;
            case E:
                maps.getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                maps.getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                maps.getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (maps.getCurrentMap().getPlayer().getCellInFrontOfActor().getItem() instanceof InteractiveObject) {
                    int interactablesArrayCurrentIndex = 0;
                    dungeoncrawl.logic.Cell currentlyFocusedCell = maps.getCurrentMap().getPlayer().getCellInFrontOfActor();
                    while (maps.getCurrentMap().getInteractablesArray().size() > interactablesArrayCurrentIndex) {
                        InteractiveObject currentlyProcessedInteractable = maps.getCurrentMap().getInteractablesArray().get(interactablesArrayCurrentIndex);
                        if (currentlyProcessedInteractable.isThisObjectInteractive() &&
                                currentlyProcessedInteractable.isThisInteractiveObjectCurrentlyBeingFocusedOn(currentlyFocusedCell) &&
                                currentlyProcessedInteractable.isPlayerInteractingFromLegalDirection(maps.getCurrentMap().getPlayer().getCell())) {
                            currentlyProcessedInteractable.interact();
                            if (currentlyProcessedInteractable instanceof Switch && ((Switch) currentlyProcessedInteractable).getGroupName() != null) {
                                maps.getCurrentMap().getSwitchablesCollection()
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
                } else if (maps.getCurrentMap().getPlayer().getCell().getItem() != null && maps.getCurrentMap().getPlayer().getCell().getItem() instanceof PickupableItem) {
                    Item item = (Item) maps.getCurrentMap().getPlayer().getCell().getItem();
                    INVENTORY_MANAGER.pickUpItem(item, maps.getCurrentMap());

                }
                if (maps.getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) maps.getCurrentMap().getPlayer().getCell().getItem()).activate();
                }
                /*else if (isThereAPickupableItemUnderThePlayer()) {
                    map.getPlayer().getCell().getItem().
                }*/
                refresh();
                break;
            case C:
                System.out.println(maps.getCurrentMap().getPlayer().getCellInFrontOfActor().getCellType());
                if (maps.getCurrentMap().getPlayer().getCellInFrontOfActor().getItem() instanceof Switch) {
                    System.out.println(((Switch) maps.getCurrentMap().getPlayer().getCellInFrontOfActor().getItem()).getGroupName());
                }
                break;
            case N:
                maps.getCurrentMap().getPlayer().getCellInFrontOfActor().setItem(new LootTable().getItemRareLoot().get(4));/*getMonsterCommonLoot().get(0));*/
                refresh();
                break;
            case F5:
                if (!isDeveloperStartingGearEnabled) {
                    maps.getCurrentMap().getPlayer().raiseMaxHealth(17);
                    maps.getCurrentMap().getPlayer().setHealth(maps.getCurrentMap().getPlayer().getMaxHealth());
                    INVENTORY_MANAGER.pickUpItem(new LootTable().getWeaponRareLoot().get(1), maps.getCurrentMap());
                    INVENTORY_MANAGER.pickUpItem(new LootTable().getItemRareLoot().get(3), maps.getCurrentMap());
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
            case F6:
                if (!isDeveloperStartingGearEnabled) {
                    maps.getCurrentMap().getPlayer().raiseMaxHealth(35);
                    maps.getCurrentMap().getPlayer().setHealth(maps.getCurrentMap().getPlayer().getMaxHealth());
                    INVENTORY_MANAGER.pickUpItem(new LootTable().getItemLegendaryLoot().get(3), maps.getCurrentMap());
                    INVENTORY_MANAGER.pickUpItem(new LootTable().getWeaponLegendaryLoot().get(1), maps.getCurrentMap());
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
//            case S: //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<NEW!
//                Player player = map.getPlayer();
//                dbManager.savePlayer(player);
//                break;

            case F12:
                maps.getCurrentMap().getPlayer().setWallCheatOn(!maps.getCurrentMap().getPlayer().isWallCheatOn());
                break;
            case F11:
                maps.getCurrentMap().getPlayer().saveStats();
                maps.incrementCurrentMapIndex();
                MapLoader.loadMap(maps.getCurrentMapIndex());
                maps.getCurrentMap().getPlayer().loadStats();
                maps.getCurrentMap().getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
                refresh();
                break;
            case F10:
                maps.getCurrentMap().getPlayer().saveStats();
                maps.decrementCurrentMapIndex();
                MapLoader.loadMap(maps.getCurrentMapIndex());
                maps.getCurrentMap().getPlayer().loadStats();
                maps.getCurrentMap().getPlayer().setNameGivenByPlayer(menu.getPlayerName().getText());
                refresh();
                break;
            case F2:
                maps.getCurrentMap().getMapStateSwitchers().stream().filter(x -> x instanceof TorchPuzzle).forEach(InteractiveObject::interact);
                refresh();
                break;
        }


        if (maps.getCurrentMap().getPlayer().getHealth() <= 0) {
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

        Tiles.drawParalaxx(context, maps.getCurrentMap().getPlayer().getX(), maps.getCurrentMap().getPlayer().getY());


        int dx = 7 - maps.getCurrentMap().getPlayer().getX(); // 0;
        int dy = 7 - maps.getCurrentMap().getPlayer().getY(); // 0;
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
        for (int x = 0; x < maps.getCurrentMap().getWidth(); x++) {
            for (int y = 0; y < maps.getCurrentMap().getHeight(); y++) {
                dungeoncrawl.logic.Cell cell = maps.getCurrentMap().getCell(x, y);
                Tiles.drawTile(context, cell, x + dx, y + dy);

            }
        }
        for (int x = 0; x < maps.getCurrentMap().getWidth(); x++) {
            for (int y = 0; y < maps.getCurrentMap().getHeight(); y++) {
                Cell cell = maps.getCurrentMap().getCell(x, y);
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


                if (!(maps.getCurrentMap().getPlayer().getTileName().equals("playerArmored2")) && maps.getCurrentMap().getPlayer().getArmor() > 6) {
                    maps.getCurrentMap().getPlayer().setTileName("playerArmored1");
                }
                if (maps.getCurrentMap().getPlayer().getArmor() >= 13) {
                    maps.getCurrentMap().getPlayer().setTileName("playerArmored2");
                }


            }
        }
        // Tiles.drawTile(context, map.getPlayer().getCell().getActor(), map.getPlayer().getX() + dx, map.getPlayer().getY() + dy);
        UI.managePlayerStatistics();
    }

/*    private boolean isThereAnInteractiveObjectAroundThePlayer() {
        if (maps.getCurrentMap().getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject ||
                maps.getCurrentMap().getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject ||
                maps.getCurrentMap().getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject ||
                maps.getCurrentMap().getPlayer().getCell().getNeighbor(0, -1).getItem() instanceof InteractiveObject) {
            return true;
        } else {
            return false;
        }
    }

    private int[] getTheInteractiveEntityDirection() {
        if (maps.getCurrentMap().getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{1, 0};
        } else if (maps.getCurrentMap().getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject) {
            return new int[]{-1, 0};
        } else if (maps.getCurrentMap().getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject) {
            return new int[]{0, 1};
        } else {
            return new int[]{0, -1};
        }
    }*/

    private void playerSuffersEnvironmentalDamage() {
//        Player.playHurtSound();
        maps.getCurrentMap().getPlayer().playerHit();
        ((EnvironmentalDamage) maps.getCurrentMap().getPlayer().getCell().getItem()).playDamageSound();
        maps.getCurrentMap().getPlayer().lowerHealth(maps.getCurrentMap().getPlayer().getCell().getItem().getAttackPower());
        if (maps.getCurrentMap().getPlayer().getHealth() < 1) {
            maps.getCurrentMap().getPlayer().playDeathSound();
        }
    }

    public boolean isPlayerBeingAffectedByAnEnvironmentalDamageSource() {
        return maps.getCurrentMap().getPlayer().getCell().getItem() instanceof EnvironmentalDamage && maps.getCurrentMap().getPlayer().getCell().getItem().getAttackPower() > 0;
    }

    public static GameMap getCurrentMap() { //getMap?
        return maps.getCurrentMap();
    }

    public static AiMovement aiGetter() {
        return maps.getCurrentAi();
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

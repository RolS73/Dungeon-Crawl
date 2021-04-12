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
import dungeoncrawl.screens.saving.LoadDialog;
import dungeoncrawl.screens.saving.SaveDialog;
import dungeoncrawl.screens.saving.SavingInterface;
import dungeoncrawl.screens.startmenu.Menu;
import dungeoncrawl.serializer.Serializer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Main extends Application {

    boolean isDeveloperStartingGearEnabled;

    @Getter private static Maps maps = new Maps();
    @Getter private static final InventoryManager INVENTORY_MANAGER = new InventoryManager();
    @Getter private static final UserInterface UI = new UserInterface();

    private final Menu menu = new Menu();
    private final GameOver gameOver = new GameOver();

    Canvas canvas = new Canvas(
            15 * Tiles.TILE_WIDTH,
            15 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    private Stage stage;

    private final List<String> wallCheat = Arrays.asList("Laci", "Ricsi", "Roland", "Szabolcs", "George");

    private final SavingInterface saving = new SaveDialog();
    private final SavingInterface loading = new LoadDialog();

    GameDatabaseManager dbManager; //Sprint 2-ből

    Serializer serializer = new Serializer(stage);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        saving.setPrimary(primaryStage);
        loading.setPrimary(primaryStage);

        setupDbManager(); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< THIS IS NEW!
        
        if (maps.getCurrentMapIndex() == 0) {
            SetInteractableItems.setStuff(0); //Map1 interactables
        }

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
                movePlayer(Direction.UP);
                break;
            case DOWN:
            case S:
                if (!keyEvent.isControlDown()) {
                    movePlayer(Direction.DOWN);
                } else {
                    saving.openDialog();
                }
                break;
            case L:
                if (keyEvent.isControlDown()) {
                    loading.openDialog();
                }
                break;
            case LEFT:
            case A:
                movePlayer(Direction.LEFT);
                break;
            case RIGHT:
            case D:
                movePlayer(Direction.RIGHT);
                break;
            case SPACE:
                operateOtherMapObjects();
                refresh();
                //System.out.println("Player X Coordinate: " + getCurrentMap().getPlayer().getX() + "\n" + "Player Y Coordinate: " + getCurrentMap().getPlayer().getY());
                break;
            case Q:
                drinkPotion();
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
                operateTrapsAndItems();
                if (getCurrentMap().getPlayer().getCellInFrontOfActor().getItem() instanceof InteractiveObject) {
                    operateObjectFrontOfActor();
                } else if (getCurrentMap().getPlayer().getCell().getItem() != null && getCurrentMap().getPlayer().getCell().getItem() instanceof PickupableItem) {
                    pickUpItem();
                }
                refresh();
                break;
            case C:
                System.out.println(getCurrentMap().getPlayer().getCellInFrontOfActor().getCellType());
                if (getCurrentMap().getPlayer().getCellInFrontOfActor().getItem() instanceof Switch) {
                    System.out.println(((Switch) getCurrentMap().getPlayer().getCellInFrontOfActor().getItem()).getGroupName());
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

        endGameIfPlayerDies();
    }

    private void endGameIfPlayerDies() {
        if (getCurrentMap().getPlayer().getHealth() <= 0) {
            stage.setScene(gameOver.getGameOverScene());
        }
    }

    private void pickUpItem() {
        Item item = (Item) getCurrentMap().getPlayer().getCell().getItem();
        INVENTORY_MANAGER.pickUpItem(item, getCurrentMap());
    }

    private void operateObjectFrontOfActor() {
        Cell currentlyFocusedCell = getCurrentMap().getPlayer().getCellInFrontOfActor();
        for (int i = 0; i < getCurrentMap().getInteractablesArray().size(); i++) {
            InteractiveObject currentlyProcessedInteractable = getCurrentMap().getInteractablesArray().get(i);
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
                }
                if (currentlyProcessedInteractable.isMoveOnPossibleAfterInteraction() && !(currentlyProcessedInteractable instanceof Switch)) {
                    currentlyFocusedCell.setCellType(CellType.FLOOR);
                }
                break;
            }
        }
    }

    private void drinkPotion() {
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
    }

    private void movePlayer(Direction direction) {
        move(direction);
        executeOnPlayerMovement();
    }

    private void executeOnPlayerMovement() {
        getCurrentMap().getPlayer().updateActorOrientation();
        operateOtherMapObjects();
        refresh();
    }

    private void operateOtherMapObjects() {
        maps.getCurrentAi().monsterMover();
        operateTrapsAndItems();
    }

    private void operateTrapsAndItems() {
        getCurrentMap().getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
        getCurrentMap().getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
        getCurrentMap().getProjectilesCollection().removeIf(ProjectileCycle::isHit);
        if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
            playerSuffersEnvironmentalDamage();
        }
        if (getCurrentMap().getPlayer().getCell().getItem() instanceof StepOnActivatable) {
            ((StepOnActivatable) getCurrentMap().getPlayer().getCell().getItem()).activate();
        }
    }

    private void move(Direction direction) {
        switch (direction) {
            case UP:
                getCurrentMap().getPlayer().setTileName("playerU");
                getCurrentMap().getPlayer().move(0, -1);
                break;
            case DOWN:
                getCurrentMap().getPlayer().setTileName("playerD");
                getCurrentMap().getPlayer().move(0, 1);
                break;
            case LEFT:
                getCurrentMap().getPlayer().setTileName("playerL");
                getCurrentMap().getPlayer().move(-1, 0);
                break;
            case RIGHT:
                getCurrentMap().getPlayer().setTileName("playerR");
                getCurrentMap().getPlayer().move(1, 0);
                break;
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
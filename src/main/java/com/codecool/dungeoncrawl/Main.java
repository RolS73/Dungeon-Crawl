package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.items.Weapon;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.EnvironmentalDamage;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.ProjectileCycle;
import com.codecool.dungeoncrawl.logic.actors.items.enviromentalHazards.TrapCycle;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.InteractiveObject;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.OpenedDoor;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.StepOnActivatable;
import com.codecool.dungeoncrawl.logic.actors.items.interactablilty.Switch;
import com.codecool.dungeoncrawl.logic.actors.items.looting.Item;
import com.codecool.dungeoncrawl.logic.actors.items.looting.LootTable;
import com.codecool.dungeoncrawl.logic.actors.items.looting.PickupableItem;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {


//    public static ObservableList<Item> inventory = FXCollections.observableArrayList();
    boolean isDeveloperStartingGearEnabled;


    static GameMap map = MapLoader.loadMap();
    AiMovement AI = new AiMovement(map);
    Canvas canvas = new Canvas(
            15 * Tiles.TILE_WIDTH,
            15 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackPwLabel = new Label();
    Label armorLabel = new Label();
    Menu menu = new Menu();
    private Label name = new Label("");
    Button pickUpButton = new Button("Pick up!");
    GameOver gameOver = new GameOver();
    private Stage stage;
    private final List<String> wallCheat = Arrays.asList("Laci", "Ricsi", "Roland", "Szabolcs", "George");
    InventoryManager inventoryManager = new InventoryManager();
    GameDatabaseManager dbManager; //Sprint 2-ből

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        GridPane ui = new GridPane();
        ui.setStyle("-fx-background-color: black;");

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(name, 0, 0);

        HBox lifeStatus = new HBox();
        lifeStatus.setSpacing(5);
        lifeStatus.getChildren().addAll(new Label("Health:"), healthLabel, new Label("Armor:"), armorLabel);

        HBox attackPwStatus = new HBox();
        attackPwStatus.getChildren().addAll( new Label("Attackpw: "), attackPwLabel);

        ui.setHgap(10);
        ui.setVgap(10);
        ui.setPadding(new Insets(10, 10, 10, 10));
        ui.getStylesheets().add("Main.css");

        ui.add(lifeStatus, 0, 1);
        ui.add(attackPwStatus, 0, 2);


        Label instructions = new Label();
        instructions.setText("Move with arrow keys or WASD.\nInteract: E key.\nPick up items with E key.");
        ui.add(instructions, 0, 5);

        TableView<Item> inventoryTable = new TableView<>(InventoryManager.inventory);
        TableColumn<Item, String> itemNames = new TableColumn<>("Inventory");

        itemNames.setCellValueFactory(items -> new ReadOnlyStringWrapper(items.getValue().getName()));
        inventoryTable.getColumns().add(itemNames);
        inventoryTable.setMaxWidth(130);
        inventoryTable.setMaxHeight(150);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ui.add(inventoryTable, 0, 4);
        inventoryTable.setFocusTraversable(false);
        inventoryTable.setPlaceholder(new Label("Inventory is empty!"));

        SetInteractableItems.setStuff(map); //Map1 interactables

        pickUpButton.setDisable(true);
        pickUpButton.setOnAction(pickUp -> {
            Item item = (Item) map.getPlayer().getCell().getItem();
            inventoryManager.pickUpItem(item, map);
            refresh();
            pickUpButton.setDisable(true);
        });
        HBox lootButtons = new HBox();
        lootButtons.setSpacing(10);
        pickUpButton.setFocusTraversable(false);
        pickUpButton.setPrefWidth(130);

        lootButtons.getChildren().addAll(pickUpButton);
        ui.add(lootButtons, 0, 3);

        setupDbManager(); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< THIS IS NEW!


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        primaryStage.setScene(menu.getMenuScreen());

        Scene scene = new Scene(borderPane);

        menu.getPlayButton().setOnAction(play -> {
            primaryStage.setScene(scene);
            name.setText(menu.getPlayerName().getText());
            if (wallCheat.contains(name.getText())) {
                map.getPlayer().setWallCheatOn(true);
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
                map.getPlayer().setTileName("playerU");
                map.getPlayer().move(0, -1);
                AI.monsterMover();
                map.getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                map.getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                map.getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (map.getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) map.getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case DOWN:
            case S:
                map.getPlayer().setTileName("playerD");
                map.getPlayer().move(0, 1);
                AI.monsterMover();
                map.getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                map.getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                map.getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (map.getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) map.getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case LEFT:
            case A:
                map.getPlayer().setTileName("playerL");
                map.getPlayer().move(-1, 0);
                AI.monsterMover();
                map.getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                map.getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                map.getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (map.getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) map.getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case RIGHT:
            case D:
                map.getPlayer().setTileName("playerR");
                map.getPlayer().move(1, 0);
                AI.monsterMover();
                map.getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                map.getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                map.getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (map.getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) map.getPlayer().getCell().getItem()).activate();
                }
                refresh();
                break;
            case SPACE:
                AI.monsterMover();
                map.getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                map.getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                map.getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (map.getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) map.getPlayer().getCell().getItem()).activate();
                }
                refresh();
                //System.out.println("Player X Coordinate: " + map.getPlayer().getX() + "\n" + "Player Y Coordinate: " + map.getPlayer().getY());
                break;
            case F4:
                map.getPlayer().teleport(94,20);
                refresh();
                break;
            case F9:
                map.getPlayer().teleport(62,38);
                refresh();
                break;
            case E:
                map.getEndlessCycleTraps().forEach(TrapCycle::trapCycle);
                map.getProjectilesCollection().forEach(ProjectileCycle::projectileCycle);
                map.getProjectilesCollection().removeIf(ProjectileCycle::isHit);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (map.getPlayer().getCell().getItem() != null && map.getPlayer().getCell().getItem() instanceof PickupableItem) {
                    Item item = (Item) map.getPlayer().getCell().getItem();
                    inventoryManager.pickUpItem(item, map);

                } else if (isThereAnInteractiveObjectAroundThePlayer()) {
                    int[] interactableDirection = getTheInteractiveEntityDirection();
                    int interactablesArrayCurrentIndex = 0;
                    Cell currentlyFocusedCell = map.getPlayer().getCell().getNeighbor(interactableDirection[0], interactableDirection[1]);
                    while (map.getInteractablesArray().size() > interactablesArrayCurrentIndex) {
                        InteractiveObject currentlyProcessedInteractable = map.getInteractablesArray().get(interactablesArrayCurrentIndex);
                        //System.out.println(currentlyProcessedInteractable);
                        if (currentlyProcessedInteractable.isThisObjectInteractive() &&
                                currentlyProcessedInteractable.isThisInteractiveObjectCurrentlyBeingFocusedOn(currentlyFocusedCell) &&
                                currentlyProcessedInteractable.isPlayerInteractingFromLegalDirection(map.getPlayer().getCell())) {
                            currentlyProcessedInteractable.interact();
                            if (currentlyProcessedInteractable instanceof Switch && ((Switch) currentlyProcessedInteractable).getGroupName() != null) {
                                map.getSwitchablesCollection()
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
                }
                if (map.getPlayer().getCell().getItem() instanceof StepOnActivatable) {
                    ((StepOnActivatable) map.getPlayer().getCell().getItem()).activate();
                }
                /*else if (isThereAPickupableItemUnderThePlayer()) {
                    map.getPlayer().getCell().getItem().
                }*/
                refresh();
                break;
            case F5:
                if (!isDeveloperStartingGearEnabled) {
                    map.getPlayer().raiseMaxHealth(17);
                    map.getPlayer().setHealth(map.getPlayer().getMaxHealth());
                    Item rareWeapon = new LootTable().getWeaponRareLoot().get(1);
                    Item rareArmor = new LootTable().getItemRareLoot().get(3);
                    inventoryManager.pickUpItem(rareWeapon, map);
                    inventoryManager.pickUpItem(rareArmor, map);
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
            case F6:
                if (!isDeveloperStartingGearEnabled) {
                    map.getPlayer().raiseMaxHealth(35);
                    map.getPlayer().setHealth(map.getPlayer().getMaxHealth());
                    Item LegendaryWeapon = new LootTable().getWeaponLegendaryLoot().get(1);
                    Item LegendaryArmor = new LootTable().getItemLegendaryLoot().get(3);
                    inventoryManager.pickUpItem(LegendaryArmor, map);
                    inventoryManager.pickUpItem(LegendaryWeapon, map);
                    isDeveloperStartingGearEnabled = true;
                    refresh();
                    break;
                }
//            case S: //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<NEW!
//                Player player = map.getPlayer();
//                dbManager.savePlayer(player);
//                break;

            case F12:
                if (!map.getPlayer().isWallCheatOn()) {
                    map.getPlayer().setWallCheatOn(true);
                } else {
                    map.getPlayer().setWallCheatOn(false);
                }
                break;
        }
        if (map.getPlayer().getHealth() <= 0) {
//            Sounds.playSound("Hdead");

            stage.setScene(gameOver.getGameOverScene());
        }
        if (map.getPlayer().getCell().getItem() instanceof OpenedDoor || map.getPlayer().getCell().getItem() instanceof Switch
                || map.getPlayer().getCell().getItem() instanceof InteractiveObject || map.getPlayer().getCell().getItem() instanceof EnvironmentalDamage) {
            pickUpButton.setDisable(true);
        } else {
            pickUpButton.setDisable(map.getPlayer().getCell().getItem() == null);
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

        Tiles.drawParalaxx(context, map.getPlayer().getX(), map.getPlayer().getY());


        int dx = 7-map.getPlayer().getX(); // 0;
        int dy = 7-map.getPlayer().getY(); // 0;
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
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                Tiles.drawTile(context, cell, x + dx, y + dy);

            }
        }
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
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


                if (!(map.getPlayer().getTileName().equals("playerArmored2")) && map.getPlayer().getArmor() > 6) {
                    map.getPlayer().setTileName("playerArmored1");
                }
                if (map.getPlayer().getArmor() >= 13) {
                    map.getPlayer().setTileName("playerArmored2");
                }


            }
        }
        // Tiles.drawTile(context, map.getPlayer().getCell().getActor(), map.getPlayer().getX() + dx, map.getPlayer().getY() + dy);




        if (InventoryManager.inventory.stream().anyMatch(item -> item instanceof Weapon)) {
            attackPwLabel.setText(map.getPlayer().getStrength() + "+" + inventoryManager.getCurrentWeapon().getAttackpowerIncrease());
        } else {
            attackPwLabel.setText(String.valueOf(map.getPlayer().getStrength()));
        }
        healthLabel.setText("" + map.getPlayer().getHealth() + "/" + map.getPlayer().getMaxHealth());
        armorLabel.setText("" + map.getPlayer().getArmor());
    }

    private boolean isThereAnInteractiveObjectAroundThePlayer() {
        if (map.getPlayer().getCell().getNeighbor(1, 0).getItem() instanceof InteractiveObject ||
                map.getPlayer().getCell().getNeighbor(-1, 0).getItem() instanceof InteractiveObject ||
                map.getPlayer().getCell().getNeighbor(0, 1).getItem() instanceof InteractiveObject ||
                map.getPlayer().getCell().getNeighbor(0, -1).getItem() instanceof InteractiveObject) {
            return true;
        } else {
            return false;
        }
    }

    private int[] getTheInteractiveEntityDirection() {
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

    /*private boolean isThereAPickupableItemUnderThePlayer() {
        return map.getPlayer().getCell().getItem() instanceof PickupableItem;
    }*/

    private void playerSuffersEnvironmentalDamage() {
//        Player.playHurtSound();
        map.getPlayer().playerHit();
        ((EnvironmentalDamage) map.getPlayer().getCell().getItem()).playDamageSound();
        map.getPlayer().lowerHealth(map.getPlayer().getCell().getItem().getAttackPower());
        if (map.getPlayer().getHealth() < 1) {
            map.getPlayer().playDeathSound();
        }
    }

    public boolean isPlayerBeingAffectedByAnEnvironmentalDamageSource() {
        return map.getPlayer().getCell().getItem() instanceof EnvironmentalDamage && map.getPlayer().getCell().getItem().getAttackPower() > 0;
    }

    public static GameMap cheatingMapGetter(){
        return map;
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

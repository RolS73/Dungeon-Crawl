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

import java.util.NoSuchElementException;

public class Main extends Application {


    public static ObservableList<Item> inventory = FXCollections.observableArrayList();


    GameMap map = MapLoader.loadMap();
    AiMovement AI = new AiMovement(map);
    Canvas canvas = new Canvas(
            19 * Tiles.TILE_WIDTH,
            19 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackPwLabel = new Label();
    Label armorLabel = new Label();

    Button pickUpButton = new Button("Pick up!");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        HBox lifeStatus = new HBox();
        lifeStatus.setSpacing(5);
        lifeStatus.getChildren().addAll(new Label("Health:"), healthLabel, new Label("Armor:"), armorLabel);

        HBox attackPwStatus = new HBox();
        attackPwStatus.getChildren().addAll( new Label("Attackpw: "), attackPwLabel);

        ui.setHgap(10);
        ui.setVgap(10);
        ui.setPadding(new Insets(10, 10, 10, 10));
        ui.add(lifeStatus, 0, 0);
        ui.add(attackPwStatus, 0, 1);


        Label instructions = new Label();
        instructions.setText("Move with arrow keys or WASD.\nInteract: E key.\nPick up items with E key.");
        ui.add(instructions, 0, 4);

        TableView<Item> inventoryTable = new TableView<>(inventory);
        TableColumn<Item, String> itemnames = new TableColumn<>("Inventory");

        itemnames.setCellValueFactory(items -> new ReadOnlyStringWrapper(items.getValue().getName()));
        inventoryTable.getColumns().add(itemnames);
        inventoryTable.setMaxWidth(130);
        inventoryTable.setMaxHeight(150);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ui.add(inventoryTable, 0, 3);
        inventoryTable.setFocusTraversable(false);
        inventoryTable.setPlaceholder(new Label("Inventory is empty!"));

        map.getPlacedItemsCollection().get(0).getCell().setItem(new LootTable("Item","Rare").overwriteLoot(2));
        map.getPlacedItemsCollection().get(1).getCell().setItem((new LootTable("Item","Rare").overwriteLoot(3)));

        map.getDoorsSealedFromOtherSideArray().get(0).setOpenableFromWhatDirection("Up");
        map.getDoorsSealedFromOtherSideArray().get(1).setOpenableFromWhatDirection("Left");
        map.getDoorsSealedFromOtherSideArray().get(2).setOpenableFromWhatDirection("Right");

        map.getChestsCollection().get(0).setAnotherTilename("chest1");
        map.getChestsCollection().get(1).setAnotherTilename("chest2");
        map.getChestsCollection().get(2).setAnotherTilename("chest2");

        map.getLeverSwitchCollection().get(0).setGroupName("GateGroup1");
        map.getGateOpenableByASwitchCollection().get(0).setGroupName("GateGroup1");
        map.getGateOpenableByASwitchCollection().get(1).setGroupName("GateGroup1");
        map.getGateOpenableByASwitchCollection().get(2).setGroupName("GateGroup1");
        map.getGateOpenableByASwitchCollection().get(3).setGroupName("GateGroup1");

        map.getSuspiciousWallsCollection().get(0).setGroupName("SuspiciousWallGroup1");  // Under the first puzzle torch
        map.getHiddenPassagesCollection().get(0).setGroupName("SuspiciousWallGroup1");
        map.getHiddenItemsCollection().get(0).setGroupName("SuspiciousWallGroup1");

        map.getSuspiciousWallsCollection().get(1).setGroupName("SuspiciousWallGroup2");  // Under the first SusWall
        map.getHiddenItemsCollection().get(1).setGroupName("SuspiciousWallGroup2");
        map.getSuspiciousWallsCollection().get(1).setTileName("empty");

        map.getSuspiciousWallsCollection().get(4).setGroupName("SuspiciousWallGroup3");  // Main Hall
        map.getHiddenPassagesCollection().get(2).setGroupName("SuspiciousWallGroup3");
        map.getHiddenItemsCollection().get(3).setGroupName("SuspiciousWallGroup3");

        map.getSuspiciousWallsCollection().get(3).setGroupName("SuspiciousWallGroup4"); // Under spawn room
        map.getHiddenPassagesCollection().get(1).setGroupName("SuspiciousWallGroup4");
        map.getHiddenItemsCollection().get(2).setGroupName("SuspiciousWallGroup4");
        map.getHiddenEnemySpawnersCollection().get(0).setGroupName("SuspiciousWallGroup4");

        map.getSuspiciousWallsCollection().get(5).setGroupName("SuspiciousWallGroup5");  // Hidden passage between gates
        map.getSuspiciousWallsCollection().get(6).setGroupName("SuspiciousWallGroup5");
        for (int i = 3; i < 14; i++) {
            map.getHiddenPassagesCollection().get(i).setGroupName("SuspiciousWallGroup5");
        }

        map.getSuspiciousWallsCollection().get(7).setGroupName("SuspiciousWallGroup6");  // Left optional room
        map.getHiddenItemsCollection().get(4).setGroupName("SuspiciousWallGroup6");

        map.getSuspiciousWallsCollection().get(9).setGroupName("SuspiciousWallGroup7");  // Right optional room
        map.getHiddenItemsCollection().get(5).setGroupName("SuspiciousWallGroup7");

        pickUpButton.setDisable(true);
        pickUpButton.setOnAction(pickUp -> {
            Item item = (Item) map.getPlayer().getCell().getItem();
            pickUpItem(item);
            refresh();
            pickUpButton.setDisable(true);
        });
        HBox lootButtons = new HBox();
        lootButtons.setSpacing(10);
        pickUpButton.setFocusTraversable(false);
        pickUpButton.setPrefWidth(130);

        lootButtons.getChildren().addAll(pickUpButton);
        ui.add(lootButtons, 0, 2);

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

    private void pickUpItem(Item item) {
        if (item instanceof Weapon) {
            if (inventory.stream().anyMatch(i -> i instanceof Weapon)) {
                compareWithCurrentWeapon(item);
            } else {
                equipWeapon(item);
            }
        } else if (item instanceof Life) {
            //map.getPlayer().raiseMaxHealth(5);
            if (!(map.getPlayer().getHealth() == map.getPlayer().getMaxHealth())) {
                map.getPlayer().restoreHealth(map.getPlayer().getCell().getItem().getHealth());
                map.getPlayer().getCell().setItem(null);
            }
        } else if (item instanceof Key) {
            inventory.add(item);
            map.getPlayer().getCell().setItem(null);
        } else if (item instanceof LifeUpgrade) {
            map.getPlayer().raiseMaxHealth(map.getPlayer().getCell().getItem().getHealth());
            if (map.getPlayer().getMaxHealth() == map.getPlayer().getHealth()) {
                map.getPlayer().restoreHealth(map.getPlayer().getCell().getItem().getHealth());
            }
            map.getPlayer().getCell().setItem(null);
        } else if (item instanceof ArmorUpgrade) {
            if (inventory.stream().anyMatch(i -> i instanceof ArmorUpgrade)) {
                compareWithCurrentArmor(item);
            } else {
                equipArmor(item);
            }
            //System.out.println(map.getPlayer().getArmor());
        }
    }

    private void compareWithCurrentArmor(Item item) {
        ArmorUpgrade currentArmor = getCurrentArmor();
        if (currentArmor.getHealth() < ((ArmorUpgrade) item).getHealth()) {
            unequipArmor(currentArmor);
            equipArmor(item);
        }
    }

    private void unequipArmor(ArmorUpgrade currentArmor) {
        inventory.remove(currentArmor);
        map.getPlayer().setArmor(0);
    }

    private void equipArmor(Item item) {
        inventory.add(item);
        map.getPlayer().setArmor(map.getPlayer().getCell().getItem().getHealth());
        map.getPlayer().getCell().setItem(null);
    }

    private ArmorUpgrade getCurrentArmor() {
        return (ArmorUpgrade) inventory.stream().filter(a -> a instanceof ArmorUpgrade)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No Armor found"));
    }

    private void compareWithCurrentWeapon(Item item) {
        Weapon currentWeapon = getCurrentWeapon();
        if (currentWeapon.getAttackpowerIncrease() < ((Weapon) item).getAttackpowerIncrease()) {
            unequipCurrentWeapon(currentWeapon);
            equipWeapon(item);
        }
    }

    private Weapon getCurrentWeapon() {
        return (Weapon) inventory.stream().filter(weapon -> weapon instanceof Weapon)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No Weapon found"));
    }

    private void unequipCurrentWeapon(Weapon currentWeapon) {
        inventory.remove(currentWeapon);
        map.getPlayer().setAttackPower(map.getPlayer().getAttackPower() - currentWeapon.getAttackpowerIncrease());
    }

    private void equipWeapon(Item item) {
        map.getPlayer().raiseAttackPower(((Weapon) item).getAttackpowerIncrease());
        inventory.add(item);
        map.getPlayer().getCell().setItem(null);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                map.getPlayer().move(0, -1);
                AI.monsterMover();
                map.getTrapsCollection().forEach(TrapPlain::activate);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                refresh();
                break;
            case DOWN:
            case S:
                map.getPlayer().move(0, 1);
                AI.monsterMover();
                map.getTrapsCollection().forEach(TrapPlain::activate);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                refresh();
                break;
            case LEFT:
            case A:
                map.getPlayer().move(-1, 0);
                AI.monsterMover();
                map.getTrapsCollection().forEach(TrapPlain::activate);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                refresh();
                break;
            case RIGHT:
            case D:
                map.getPlayer().move(1, 0);
                AI.monsterMover();
                map.getTrapsCollection().forEach(TrapPlain::activate);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                refresh();
                break;
            case SPACE:
                AI.monsterMover();
                map.getTrapsCollection().forEach(TrapPlain::activate);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                refresh();
                break;
            case E:
                map.getTrapsCollection().forEach(TrapPlain::activate);
                if (isPlayerBeingAffectedByAnEnvironmentalDamageSource()) {
                    playerSuffersEnvironmentalDamage();
                }
                if (map.getPlayer().getCell().getItem() != null && map.getPlayer().getCell().getItem() instanceof PickupableItem) {
                    Item item = (Item) map.getPlayer().getCell().getItem();
                    pickUpItem(item);

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
                /*else if (isThereAPickupableItemUnderThePlayer()) {
                    map.getPlayer().getCell().getItem().
                }*/
                refresh();
                break;
        }
        if (map.getPlayer().getCell().getItem() instanceof OpenedDoor || map.getPlayer().getCell().getItem() instanceof Switch
                || map.getPlayer().getCell().getItem() instanceof InteractiveObject || map.getPlayer().getCell().getItem() instanceof EnvironmentalDamage) {
            pickUpButton.setDisable(true);
        } else {
            if (map.getPlayer().getCell().getItem() != null) {
                pickUpButton.setDisable(false);
            } else {
                pickUpButton.setDisable(true);
            }
        }
    }

    private boolean isItemInInventory(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int dx = Math.min(0, 11 - map.getPlayer().getX());
        int dy = Math.min(0, 11 - map.getPlayer().getY());
        dx = Math.max(19 - map.getWidth(), dx);
        dy = Math.max(19 - map.getHeight(), dy);

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                Tiles.drawTile(context, cell, x + dx, y + dy);
                if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x + dx, y + dy);
                }
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x + dx, y + dy);
                }
                if (!(map.getPlayer().getTileName().equals("playerArmored2")) && map.getPlayer().getArmor() > 6) {
                    map.getPlayer().setTileName("playerArmored1");
                }
                if (map.getPlayer().getArmor() >= 13) {
                    map.getPlayer().setTileName("playerArmored2");
                }
            }
        }
        attackPwLabel.setText("" + map.getPlayer().getAttackPower());
        healthLabel.setText("" + map.getPlayer().getHealth());
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
        map.getPlayer().lowerHealth(map.getPlayer().getCell().getItem().getAttackPower());
    }

    public boolean isPlayerBeingAffectedByAnEnvironmentalDamageSource() {
        return map.getPlayer().getCell().getItem() instanceof EnvironmentalDamage && map.getPlayer().getCell().getItem().getAttackPower() > 0;
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

}

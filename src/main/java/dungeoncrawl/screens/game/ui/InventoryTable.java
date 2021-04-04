package dungeoncrawl.screens.game.ui;

import dungeoncrawl.logic.InventoryManager;
import dungeoncrawl.logic.actors.items.looting.Item;
import javafx.beans.binding.Bindings;
import javafx.collections.MapChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;

public class InventoryTable {

    @Getter
    TableView<Item> inventoryTable = new TableView<>(InventoryManager.keys);

    private TableColumn<Item, String> inventoryLabel = new TableColumn<>("Inventory");
    private TableColumn<Item, String> itemNames = new TableColumn<>("Items");
    private TableColumn<Item, Integer> itemAmount = new TableColumn<>("Amt");

    {
        InventoryManager.inventory.addListener((
                MapChangeListener.Change<? extends Item, ? extends Integer> change) -> {
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                if (removed) {
                    InventoryManager.keys.remove(change.getKey());
                } else {
                    InventoryManager.keys.add(change.getKey());
                }
            }
        });

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
        inventoryTable.setFocusTraversable(false);
        inventoryTable.setPlaceholder(new Label("Inventory is empty!"));
    }

}

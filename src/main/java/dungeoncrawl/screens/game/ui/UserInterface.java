package dungeoncrawl.screens.game.ui;

import dungeoncrawl.Main;
import dungeoncrawl.logic.CombatEvent;
import dungeoncrawl.logic.InventoryManager;
import dungeoncrawl.logic.actors.items.Weapon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class UserInterface {

    private VBox ui = new VBox();
    private HBox lifeStatus = new HBox();
    private HBox attackPwStatus = new HBox();
    private HBox financialStatus = new HBox();
    private Label name = new Label("");
    private Label healthLabel = new Label();
    private Label attackPwLabel = new Label();
    private Label armorLabel = new Label();
    private Label moneyLabel = new Label("0");
    private InventoryTable inventoryTable = new InventoryTable();
    private ObservableList<CombatEvent> combatEvents = FXCollections.observableArrayList();
    private TextArea combatLog = new TextArea();
    private Label combatLogLabel = new Label("Combat Log");

    {
        ui.setStyle("-fx-background-color: black;");
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setSpacing(10);
        ui.setPadding(new Insets(10, 10, 10, 10));
        ui.getStylesheets().add("Main.css");

        lifeStatus.setSpacing(5);
        lifeStatus.getChildren().addAll(new Label("Health:"), healthLabel, new Label("Armor:"), armorLabel);

        attackPwStatus.getChildren().addAll(new Label("Attackpw: "), attackPwLabel);

        financialStatus.getChildren().addAll(new Label("Coins: "), moneyLabel);

        Region r = new Region();

        combatLog.setFocusTraversable(false);

        ui.getChildren().addAll(name, lifeStatus, attackPwStatus, financialStatus, inventoryTable.getInventoryTable(),
                r, combatLogLabel, combatLog);
    }

    public void managePlayerStatistics() {
        manageAttackPw();
        healthLabel.setText("" + Main.getMaps().getMapList().get(Main.getCurrentMapIndex()).getPlayer().getHealth() + "/" +
                Main.getMaps().getMapList().get(Main.getCurrentMapIndex()).getPlayer().getMaxHealth());
        armorLabel.setText("" + Main.getMaps().getMapList().get(Main.getCurrentMapIndex()).getPlayer().getArmor());
        moneyLabel.setText("" + Main.getMaps().getMapList().get(Main.getCurrentMapIndex()).getPlayer().getMoneyAmount());
        manageCombatLog();
    }

    private void manageAttackPw() {
        if (InventoryManager.inventory.keySet().stream().anyMatch(item -> item instanceof Weapon)) {
            attackPwLabel.setText((Main.getMaps().getMapList().get(Main.getCurrentMapIndex()).getPlayer().getAttackPower()
                    - Main.getINVENTORY_MANAGER().getCurrentWeapon().getAttackpowerIncrease()) + "+"
                    + Main.getINVENTORY_MANAGER().getCurrentWeapon().getAttackpowerIncrease());
        } else {
            attackPwLabel.setText(String.valueOf(Main.getMaps().getMapList().get(Main.getCurrentMapIndex()).getPlayer().getAttackPower()));
        }
    }

    private void manageCombatLog() {
        for (CombatEvent combatEvent : combatEvents) {
            combatLog.setText(combatLog.getText() + combatEvent.getLog().toString());
            combatLog.positionCaret(combatLog.getText().length());
        }
        combatEvents.clear();
    }

}

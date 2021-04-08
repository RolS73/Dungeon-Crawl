package dungeoncrawl.screens.saving;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class SavingInterface {

    @Getter private final BorderPane root = new BorderPane();
    private final Stage dialog = new Stage();
    @Setter private Stage primary;
    @Setter private String dialogTitle = "";
    @Getter private final Button okButton = new Button("okBtnLabel");
    @Getter private final HBox buttons = new HBox();

    public SavingInterface() {
    }

    {
        dialog.setTitle(dialogTitle);
        dialog.setAlwaysOnTop(true);
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.UNDECORATED);

        TableView<List<String>> table = new TableView<>();
        TableColumn<List<String>, String> col = new TableColumn<>("Saved games");
        col.setMinWidth(320);
        table.getColumns().add(col);
        table.setPlaceholder(new Label("No saved game"));
        root.setCenter(table);


        okButton.setMinWidth(160);
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction((e) -> {
            primary.setOpacity(1);
            dialog.close();
        });
        cancelButton.setMinWidth(160);
        buttons.getChildren().addAll(okButton, cancelButton);

        Scene dialogScene = new Scene(root, 320, 640);
        dialog.setScene(dialogScene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primary);

    }

    public void openDialog() {
        primary.setOpacity(0.5);
        dialog.show();
    }
}

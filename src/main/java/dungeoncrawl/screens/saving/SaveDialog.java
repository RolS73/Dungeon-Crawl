package dungeoncrawl.screens.saving;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SaveDialog extends SavingInterface {

    public SaveDialog() {
        super();
        setDialogTitle("Save game");
        getOkButton().setText("Save");
    }

    {
        VBox inputField = new VBox();
        TextField saveInput = new TextField();
        inputField.getChildren().addAll(saveInput, getButtons());
        getRoot().setBottom(inputField);
    }
}

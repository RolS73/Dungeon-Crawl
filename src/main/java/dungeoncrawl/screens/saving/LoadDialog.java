package dungeoncrawl.screens.saving;

public class LoadDialog extends SavingInterface {

    public LoadDialog() {
        super();
        setDialogTitle("Load game");
        getOkButton().setText("Load");
    }

    {
        getRoot().setBottom(getButtons());
    }
}

package com.codecool.dungeoncrawl;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Menu {

    private final BorderPane menuLayout = new BorderPane();
    private final Scene menuScreen = new Scene(menuLayout);

    public Scene getMenuScreen() {
        return menuScreen;
    }

    {
        menuLayout.setTop(new Label("PLACEHOLDER TITLE"));
    }

}

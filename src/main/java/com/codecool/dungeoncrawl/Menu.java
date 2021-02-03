package com.codecool.dungeoncrawl;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu {

    private final BorderPane menuLayout = new BorderPane();
    private final Scene menuScreen = new Scene(menuLayout, 500, 500);

    public Scene getMenuScreen() {
        return menuScreen;
    }

    {

        Label title = new Label("PLACEHOLDER TITLE");
        HBox titledPane = new HBox();
        titledPane.setAlignment(Pos.CENTER);
        titledPane.getChildren().add(title);

        menuLayout.setTop(titledPane);

        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        Button playButton = new Button("Play!");
        playButton.setOnAction(play -> Main.stage.setScene(Main.gameScene));
        menu.getChildren().add(playButton);
        menuLayout.setCenter(menu);
    }

}

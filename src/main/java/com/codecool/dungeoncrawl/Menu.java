package com.codecool.dungeoncrawl;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu {

    private final BorderPane menuLayout = new BorderPane();
    private final Scene menuScreen = new Scene(menuLayout, 300, 300);

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

        Label askForName = new Label("Please enter your name!");

        TextField playerName = new TextField();
        playerName.setMaxWidth(100);

        Button playButton = new Button("Play!");

        playButton.setDisable(true);

        playButton.setOnAction(play -> Main.stage.setScene(Main.gameScene));
        playerName.textProperty().addListener((observable, oldvalue, newvalue) -> {
            if (newvalue.equals("")) {
                playButton.setDisable(true);
            } else {
                playButton.setDisable(false);
            }
        });

        menu.getChildren().addAll(askForName, playerName, playButton);
        menuLayout.setCenter(menu);
    }

}

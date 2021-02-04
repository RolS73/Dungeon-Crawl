package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Menu {

    private final BorderPane menuLayout = new BorderPane();
    private final Scene menuScreen = new Scene(menuLayout, 490, 300);

    public Scene getMenuScreen() {
        return menuScreen;
    }

    {
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Image gameOver = new Image("/tunnel.jpg");
        menuLayout.setBackground(new Background(new BackgroundImage(gameOver,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        menuLayout.setStyle("-fx-border-color : black; -fx-border-width : 0 5 ");

        Label title = new Label("Dungeon Crawl");
        HBox titledPane = new HBox();
        titledPane.setAlignment(Pos.CENTER);
        titledPane.getChildren().add(title);
        menuLayout.setTop(titledPane);
        menuLayout.setPadding(new Insets(10, 10, 10, 10));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 20pt");

        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);

        Label askForName = new Label("Please enter your name!");
        askForName.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF");

        TextField playerName = new TextField();
        playerName.setMaxWidth(100);

        Button playButton = new Button("Play!");
        playButton.setStyle("-fx-font-weight: bold; -fx-text-fill: #000000");

        playButton.setDisable(true);

        playerName.textProperty().addListener((observable, oldValue, newValue) -> {
            playButton.setDisable(newValue.isEmpty());
        });

        playButton.setOnAction(play -> {
            Main.stage.setScene(Main.gameScene);
            Main.name.setText(playerName.getText());


//            System.out.println(Player.getPlayerName());
        });

        menu.getChildren().addAll(askForName, playerName, playButton);
        menuLayout.setCenter(menu);
    }



}

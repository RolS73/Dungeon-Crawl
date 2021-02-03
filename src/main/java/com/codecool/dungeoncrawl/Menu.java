package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Menu {

    private final BorderPane menuLayout = new BorderPane();
    private final Scene menuScreen = new Scene(menuLayout, 300, 300);

    public Scene getMenuScreen() {
        return menuScreen;
    }

    {
//        BackgroundImage myBI= new BackgroundImage(new Image("my url",32,32,false,true),
//                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                BackgroundSize.DEFAULT);
////then you set to your node
//        myContainer.setBackground(new Background(myBI));
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

        playerName.textProperty().addListener((observable, oldvalue, newvalue) -> {
            if (newvalue.equals("")) {
                playButton.setDisable(true);
            } else {
                playButton.setDisable(false);
            }
        });

        playButton.setOnAction(play -> {
            Main.stage.setScene(Main.gameScene);
            Player.setPlayerName(playerName.getText());
            System.out.println(Player.getPlayerName());
        });

        menu.getChildren().addAll(askForName, playerName, playButton);
        menuLayout.setCenter(menu);
    }

}

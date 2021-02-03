package com.codecool.dungeoncrawl;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class GameOver {

    private final BorderPane borderPane = new BorderPane();
    private final Scene gameOverScene = new Scene(borderPane);

    {
        javafx.scene.control.Label title = new Label("Game Over");
        HBox titledPane = new HBox();
        titledPane.setAlignment(Pos.CENTER);
        titledPane.getChildren().add(title);
        borderPane.setTop(titledPane);

        VBox vBox = new VBox();

    }

    public Scene getGameOverScene() {
        return gameOverScene;
    }
}

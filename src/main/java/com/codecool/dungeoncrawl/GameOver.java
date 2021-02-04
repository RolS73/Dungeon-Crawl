package com.codecool.dungeoncrawl;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;


public class GameOver {

    private final BorderPane borderPane = new BorderPane();
    private final Scene gameOverScene = new Scene(borderPane, 100, 100);

    {
        javafx.scene.control.Label title = new Label("Game Over");
        HBox titledPane = new HBox();
        titledPane.setAlignment(Pos.CENTER);
        titledPane.getChildren().add(title);
        borderPane.setTop(titledPane);

        VBox vBox = new VBox();

        Label question = new Label("Try Again?");

        Button tryAgain = new Button("Yes!");
        Button dontTryAgain = new Button("No!!");

        tryAgain.setOnAction(play -> Main.stage.setScene(Main.getMenu().getMenuScreen()));;
        dontTryAgain.setOnAction(quit -> System.exit(0));

        HBox answers = new HBox();
        answers.getChildren().addAll(tryAgain, dontTryAgain);

        vBox.getChildren().addAll(question, answers);

        borderPane.setCenter(vBox);
    }

    public Scene getGameOverScene() {
        return gameOverScene;
    }
}

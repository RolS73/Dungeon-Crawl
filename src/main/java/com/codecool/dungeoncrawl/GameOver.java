package com.codecool.dungeoncrawl;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.Button;



public class GameOver {

    private final BorderPane borderPane = new BorderPane();
    private final Scene gameOverScene = new Scene(borderPane, 280, 210);

    {
//        javafx.scene.control.Label title = new Label("Game Over");
        HBox titledPane = new HBox();
        titledPane.setAlignment(Pos.CENTER);
//        titledPane.getChildren().add(title);
        borderPane.setTop(titledPane);

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Image gameOver = new Image("/gameOver.png");
        borderPane.setBackground(new Background(new BackgroundImage(gameOver,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);

        Label question = new Label("YOU DIED!");

//        Button tryAgain = new Button("Yes!");
//        Button dontTryAgain = new Button("No!!");
//
////        tryAgain.setOnAction(play -> Main.stage.setScene(Main.getMenu().getMenuScreen()));;;
//        dontTryAgain.setOnAction(quit -> System.exit(0));
//
//        HBox answers = new HBox();
//        answers.getChildren().addAll(dontTryAgain);

        vBox.getChildren().addAll(question);

        borderPane.setCenter(vBox);
    }

    public Scene getGameOverScene() {
        return gameOverScene;
    }
}

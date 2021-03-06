package dungeoncrawl.screens.gameover;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import lombok.Getter;

public class GameOver {

    private final BorderPane borderPane = new BorderPane();
    @Getter private final Scene gameOverScene = new Scene(borderPane, 280, 210);
    private final BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

    {
//        javafx.scene.control.Label title = new Label("Game Over");
//        HBox titledPane = new HBox();
//        titledPane.setAlignment(Pos.CENTER);
////        titledPane.getChildren().add(title);
//        borderPane.setTop(titledPane);

        Image gameOver = new Image("/gameOver.png");
        borderPane.setBackground(new Background(new BackgroundImage(gameOver,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        borderPane.setStyle("-fx-border-color : black; -fx-border-width : 0 5 ");

//        VBox vBox = new VBox();
//
//        vBox.setAlignment(Pos.CENTER);
//
//        Label question = new Label("YOU DIED!");
//
////        Button tryAgain = new Button("Yes!");
////        Button dontTryAgain = new Button("No!!");
////
//////        tryAgain.setOnAction(play -> Main.stage.setScene(Main.getMenu().getMenuScreen()));;;
////        dontTryAgain.setOnAction(quit -> System.exit(0));
////
////        HBox answers = new HBox();
////        answers.getChildren().addAll(dontTryAgain);
//
//        vBox.getChildren().addAll(question);
//
//        borderPane.setCenter(vBox);
    }

    public void setVictory() {
        Image victory = new Image("/Victory.png");
        borderPane.setBackground(new Background(new BackgroundImage(victory,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }
}

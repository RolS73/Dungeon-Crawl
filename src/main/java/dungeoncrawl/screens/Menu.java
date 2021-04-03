package dungeoncrawl.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class Menu {

    private final BorderPane menuLayout = new BorderPane();
    private final Scene menuScreen = new Scene(menuLayout, 490, 300);
    private final Button playButton = new Button("Play!");
    private  final TextField playerName = new TextField();
    private final Button importButton = new Button("Import game");

    public Scene getMenuScreen() {
        return menuScreen;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public TextField getPlayerName() {
        return playerName;
    }

    public Button getImportButton() {
        return importButton;
    }

    {
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Image gameOver = new Image("/tunnel.jpg");
        menuLayout.setBackground(new Background(new BackgroundImage(gameOver,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        menuLayout.getStylesheets().add("menu.css");
//        menuLayout.getStylesheets().add(getClass().getResource("/fontstyle.css").toExternalForm());

//        Font font = Font.loadFont(getClass().getResource("/blackjack.otf").toExternalForm(), 10);
//        Main.installFont("/blackjack.otf");
        Label title = new Label("Dungeon Crawl");
        HBox titledPane = new HBox();
        titledPane.setAlignment(Pos.CENTER);
        titledPane.getChildren().add(title);
        menuLayout.setTop(titledPane);
        menuLayout.setPadding(new Insets(10, 10, 20, 10));
//        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 30pt");
//        title.setFont(font);
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 30pt; -fx-font-family: 'Brush Script MT', Brush Script Std, cursive");
//        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 20pt; -fx-font-family: 'BlackJack'");
//        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 20pt; -fx-font-family: 'BlackJack'");

        VBox menu = new VBox();
        menu.setAlignment(Pos.BOTTOM_CENTER);
        menu.setSpacing(10);

        Label askForName = new Label("Please enter your name!");

        playerName.setMaxWidth(100);

        playButton.setDisable(true);

        playerName.textProperty().addListener((observable, oldValue, newValue) -> {
            playButton.setDisable(newValue.isEmpty());
        });

        menu.getChildren().addAll(askForName, playerName, playButton, importButton);
        menuLayout.setCenter(menu);
    }


}

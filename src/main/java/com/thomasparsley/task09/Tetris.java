package com.thomasparsley.task09;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Tetris extends Application {

    private static final String APP_TITLE = "Tetris remake";
    private static final int SEPARATOR_WIDTH = 2;
    private static final int WIDTH = GameLogic.WIDTH + SEPARATOR_WIDTH + 150;
    private static final int HEIGHT = GameLogic.HEIGHT;

    private static Pane root = new Pane();
    private static Scene scene = new Scene(root, WIDTH, HEIGHT);
    private static GameLogic gameLogic = new GameLogic();

    private static Line lineSeparator = new Line(GameLogic.WIDTH, 0, GameLogic.WIDTH, HEIGHT);
    private static Text scoreText = new Text(GameLogic.WIDTH + (SEPARATOR_WIDTH * 5), HEIGHT / 10, "Score: " + gameLogic.getScore());

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        lineSeparator.setStrokeWidth(SEPARATOR_WIDTH);

        root.getChildren().addAll(gameLogic, lineSeparator, scoreText);
        root.setMinWidth(WIDTH);
        root.setMinHeight(HEIGHT);

        onCloseExit(stage);

        scene.setOnKeyPressed(gameLogic.getKeyHandler());

        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    private void onCloseExit(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void updateUI() {
        var score = gameLogic.getScore();
        scoreText.setText("Score: " + score);
    }

}
package com.example.tictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    //create a new instance of the info center
    private InfoCenter infoCenter;
    TileBoard tileBoard;


    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
        intiLayout(root);
        stage.setScene(scene);
        stage.show();
    }

    private void intiLayout(BorderPane root) {
        intiInfoCenter(root);
        initTileBoard(root);
    }

    private void initTileBoard(BorderPane root) {
        tileBoard = new TileBoard(infoCenter);
        root.getChildren().add(tileBoard.getPane());

    }

    private void intiInfoCenter(BorderPane root) {
        infoCenter = new InfoCenter();
        root.getChildren().add(infoCenter.getPane());
        infoCenter.setStartGameButtonOnAction(startNewGame());
    }

    private EventHandler<ActionEvent> startNewGame() {
        return new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent event) {
               infoCenter.hideStartButton();
               infoCenter.setMessage("Player X's turn");
               infoCenter.getMessage().setStyle("-fx-text-fill: black");
               tileBoard.resetGame();
           }
        };
    }

    public static void main(String[] args) {
        launch();
    }
}
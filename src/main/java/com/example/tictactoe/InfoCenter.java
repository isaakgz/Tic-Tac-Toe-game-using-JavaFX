package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class InfoCenter {
    private StackPane pane;
    private Label message;
    private Button startGameButton;


    public InfoCenter() {
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        pane.setTranslateX((double) UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((double) UIConstants.INFO_CENTER_HEIGHT / 2);

        message = new Label("Welcome to Tic Tac Toe");
        message.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        message.setFont(Font.font(24));
        message.setAlignment(Pos.CENTER);
        message.setTranslateY(-20);

        startGameButton = new Button("Start new game");
        startGameButton.setMinSize(135,30);
        startGameButton.setTranslateY(20);


        pane.getChildren().addAll(message, startGameButton);
    }

    public StackPane getPane() {
        return pane;
    }

    public Label getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void showStartButton() {
        startGameButton.setVisible(true);
    }

    public void hideStartButton() {
        startGameButton.setVisible(false);
    }

    /*
        * Set the action for the start game button when it is clicked on by the user
        * @param onAction the action to be performed when the start game button is clicked on
        * EvenHandler is an interface that is used to handle events in JavaFX
        * ActionEvent is a class that is used to handle actions in JavaFX
        *
     */
    public void setStartGameButtonOnAction(EventHandler<ActionEvent> onAction) {
        startGameButton.setOnAction(onAction);
    }
}

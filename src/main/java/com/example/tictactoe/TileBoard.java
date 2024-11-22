package com.example.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class TileBoard {

    private StackPane pane;
    private InfoCenter infoCenter;
    private Tile[][] tiles = new Tile[3][3];
    private String currentPlayer = "X";
    private boolean gameOver = false;
    private Line winnigLine;

    public TileBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TITLE_BOARD_Height);
        pane.setTranslateX((double) UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((double) (UIConstants.TITLE_BOARD_Height / 2) + UIConstants.INFO_CENTER_HEIGHT);

        addAllTiles();
        winnigLine = new Line();
        winnigLine.setStroke(Color.GREEN);
        pane.getChildren().add(winnigLine);
    }

    private void addAllTiles() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Tile tile = new Tile();
                tile.getPane().setTranslateX((col * 100) - 100);
                tile.getPane().setTranslateY((row * 100) - 100);
                pane.getChildren().add(tile.getPane());
                tiles[row][col] = tile;
            }

        }
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        infoCenter.setMessage("Player " + currentPlayer + "'s turn");
    }

    public String getPlayerTurn() {
        return String.valueOf(currentPlayer);
    }

    public StackPane getPane() {
        return pane;
    }

    public void resetGame() {
        gameOver = false;
        currentPlayer = "X";
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                tiles[row][col].setLabel("");
            }
        }
        winnigLine.setVisible(false);
    }

    public class Tile {

        private StackPane pane;
        Label label;


        public Tile() {
            pane = new StackPane();
            pane.setMinSize(100, 100);

            Rectangle border = new Rectangle(100, 100, Color.TRANSPARENT);
            border.setStroke(Color.BLACK);
            border.setStrokeWidth(2);
            pane.getChildren().add(border);

            label = new Label();
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(24));
            label.setTextFill(Color.BLACK);
            pane.getChildren().add(label);

            pane.setOnMouseClicked(e -> {
                if (label.getText().isEmpty() && !gameOver) {
                    label.setText(currentPlayer);
                    checkForWinner();
                    if (!gameOver) {
                        switchPlayer();
                    }
                }
            });
        }

        public void checkForWinner() {
            checkRowsForWinner();
            checkColsForWinner();
            checkDiagonalsForWinner();
            checkStalemate();
        }

        private void checkStalemate() {
            if (!gameOver) {
                boolean stalemate = true;
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        if (tiles[row][col].getLabel().isEmpty()) {
                            stalemate = false;
                            break;
                        }
                    }
                }

                if (stalemate) {
                    gameOver = true;
                    infoCenter.setMessage("Stalemate!");
                    infoCenter.getMessage().setStyle("-fx-text-fill: red");
                    infoCenter.showStartButton();
                    winnigLine.setVisible(false);
                }
            }
        }

        private void checkDiagonalsForWinner() {
            if (!gameOver) {
                if (tiles[0][0].getLabel().equals(currentPlayer) && tiles[1][1].getLabel().equals(currentPlayer) && tiles[2][2].getLabel().equals(currentPlayer)) {
                    endGame(
                            new WinningTiles(tiles[0][0], tiles[1][1], tiles[2][2])
                    );
                    return;
                }

                if (tiles[0][2].getLabel().equals(currentPlayer) && tiles[1][1].getLabel().equals(currentPlayer) && tiles[2][0].getLabel().equals(currentPlayer)) {
                    endGame(
                            new WinningTiles(tiles[0][2], tiles[1][1], tiles[2][0])
                    );
                }
            }


        }

        private void checkColsForWinner() {

            if (!gameOver) {
                for (int col = 0; col < 3; col++) {
                    if (tiles[0][col].getLabel().equals(currentPlayer) && tiles[1][col].getLabel().equals(currentPlayer) && tiles[2][col].getLabel().equals(currentPlayer)) {
                        endGame(
                                new WinningTiles(tiles[0][col], tiles[1][col], tiles[2][col])
                        );
                        return;
                    }
                }
            }


        }

        private void checkRowsForWinner() {
            if (!gameOver) {
                for (int row = 0; row < 3; row++) {
                    if (tiles[row][0].getLabel().equals(currentPlayer) && tiles[row][1].getLabel().equals(currentPlayer) && tiles[row][2].getLabel().equals(currentPlayer)) {
                        endGame(
                                new WinningTiles(tiles[row][0], tiles[row][1], tiles[row][2])
                        );
                        return;
                    }
                }
            }


        }

        public void endGame(WinningTiles winningTiles) {
            gameOver = true;
            drawWinningLine(winningTiles);
            infoCenter.setMessage("Player " + currentPlayer + " wins!");
            infoCenter.getMessage().setStyle("-fx-text-fill: green");
            infoCenter.showStartButton();
        }

        private void drawWinningLine(WinningTiles winningTiles) {
            winnigLine.setStartX(winningTiles.startTile.getPane().getTranslateX() + 50);
            winnigLine.setStartY(winningTiles.startTile.getPane().getTranslateY() + 50);
            winnigLine.setEndX(winningTiles.endTile.getPane().getTranslateX() + 50);
            winnigLine.setEndY(winningTiles.endTile.getPane().getTranslateY() + 50);
            winnigLine.setVisible(true);
        }

        private class WinningTiles {
            Tile startTile;
            Tile middleTile;
            Tile endTile;

            public WinningTiles(Tile startTile, Tile middleTile, Tile endTile) {
                this.startTile = startTile;
                this.middleTile = middleTile;
                this.endTile = endTile;
            }
        }

        public StackPane getPane() {
            return pane;
        }

        public String getLabel() {
            return label.getText();
        }

        public void setLabel(String text) {
            label.setText(text);
        }
    }


}

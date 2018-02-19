package com.example.jacco.tictactoe;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Jacco on 13-2-2018.
 */

public class Game implements Serializable {

    // variables
    final private int BOARD_SIZE = 3;
    private Tile[][] board;
    private boolean playerOneTurn;
    private int movesPlayed;
    final private int maxMoves = 9;
    private boolean gameOver;

    public Game() {
        // construct board
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i=0; i<BOARD_SIZE; i++)
            for (int j=0; j<BOARD_SIZE; j++)
                board[i][j] = Tile.BLANK;

        //set startvalues
        playerOneTurn = true;
        movesPlayed = 0;
        gameOver = false;
    }

    public Tile draw(int row, int column) {
        // find out what to draw and end turn
        if (board[row][column] == Tile.BLANK && !gameOver) {
            if (playerOneTurn) {
                board[row][column] = Tile.CROSS;
                playerOneTurn = false;
                movesPlayed++;
                return Tile.CROSS;
            } else {
                board[row][column] = Tile.CIRCLE;
                playerOneTurn = true;
                movesPlayed++;
                return Tile.CIRCLE;
            }
        } else {
            return Tile.INVALID;
        }
    }

    public GameState process() {
        // check all possibilities for winning and return the winner
        for (int i=0; i<BOARD_SIZE; i++) {
            // check all rows
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == Tile.CROSS) {
                    gameOver = true;
                    return GameState.PLAYER_ONE;
                } else if (board[i][0] == Tile.CIRCLE) {
                    gameOver = true;
                    return GameState.PLAYER_TWO;
                }
            }
            // check all columns
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == Tile.CROSS) {
                    gameOver = true;
                    return GameState.PLAYER_ONE;
                } else if (board[0][i] == Tile.CIRCLE) {
                    gameOver = true;
                    return GameState.PLAYER_TWO;
                }
            }
        }
        // check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == Tile.CROSS) {
                gameOver = true;
                return GameState.PLAYER_ONE;
            } else if (board[0][0] == Tile.CIRCLE) {
                gameOver = true;
                return GameState.PLAYER_TWO;
            }
        }
        if (board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
            if (board[2][0] == Tile.CROSS) {
                gameOver = true;
                return GameState.PLAYER_ONE;
            } else if (board[2][0] == Tile.CIRCLE) {
                gameOver = true;
                return GameState.PLAYER_TWO;
            }
        }
        // if board is full, but no one won, it's a draw
        if (movesPlayed == maxMoves) {
            return GameState.DRAW;
        }
        return GameState.IN_PROGRESS;
    }

    public Tile test(int row, int column) {
        // check if a tile is blank
        if (board[row][column] == Tile.BLANK || movesPlayed == maxMoves) {
            return Tile.BLANK;
        }
        return Tile.INVALID;
    }

    public int prevent() {
        // look if there's a possibility to win
        for (int i=0; i<BOARD_SIZE; i++) {
            if (board[i][0] == board[i][1] && board[i][2] == Tile.BLANK) {
                return i*3+2;
            } else if (board[i][0] == board[i][2] && board[i][1] == Tile.BLANK) {
                return i*3+1;
            } else if (board[i][1] == board[i][2] && board[i][0] == Tile.BLANK) {
                return i*3;
            } else if (board[0][i] == board[1][i] && board[2][i] == Tile.BLANK) {
                return i+6;
            } else if (board[0][i] == board[2][i] && board[1][i] == Tile.BLANK) {
                return i+3;
            } else if (board[1][i] == board[2][i] && board[0][i] == Tile.BLANK) {
                return i;
            }
        }
        if (board[0][0] == board[1][1] && board[2][2] == Tile.BLANK) {
            return 8;
        } else if (board[0][0] == board[2][2] && board[1][1] == Tile.BLANK) {
            return 4;
        } else if (board[1][1] == board[2][2] && board[0][0] == Tile.BLANK) {
            return 0;
        } else if (board[0][2] == board[1][1] && board[2][0] == Tile.BLANK) {
            return 6;
        } else if (board[0][2] == board[2][0] && board[1][1] == Tile.BLANK) {
            return 4;
        } else if (board[1][1] == board[2][0] && board[0][2] == Tile.BLANK) {
            return 2;
        }
        return 9;
    }

    public Tile drawTile(int row, int column) {
        if (board[row][column] == Tile.CROSS) {
            return Tile.CROSS;
        } else if (board[row][column] == Tile.CIRCLE) {
            return Tile.CIRCLE;
        }
        return Tile.BLANK;
    }
}

package com.example.jacco.tictactoe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // variables
    Game game;
    int row;
    int column;
    String[] names = {"t1","t2","t3","t4","t5","t6","t7","t8","t9"};
    int[] buttons = {R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6, R.id.t7,
                     R.id.t8, R.id.t9};
    int[][] coordinates = {{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}};
    boolean AIOn;
    int randTile;
    private int[] emptySet = {};
    public int[] betterPlay;

    // saving state buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            game =(Game) savedInstanceState.getSerializable("Game");

            for (int i=0; i<names.length; i++){
                // kan niet meer winnen na draaaien en X begint altijd na draaien
                Tile tile = game.drawTile(i/3, i%3);

                Button button = findViewById(buttons[i]);
                if (tile == Tile.CROSS) {
                    button.setText("X");
                } else if (tile == Tile.CIRCLE) {
                    button.setText("O");
                }
            }
        } else {
            game = new Game();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

         outState.putSerializable("Game",game);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Game game =(Game) savedInstanceState.getSerializable("Game");

        for (int i=0; i<names.length; i++){
            // kan niet meer winnen na draaaien en X begint altijd na draaien
            Tile tile = game.drawTile(i/3, i%3);

            Button button = findViewById(buttons[i]);
            if (tile == Tile.CROSS) {
                button.setText("X");
            } else if (tile == Tile.CIRCLE) {
                button.setText("O");
            }
        }
    }

    public void tileClicked(View view) {
        // find clicked button
        int id = view.getId();

        int length = buttons.length;
        for (int i=0; i<length; i++) {
            if (id == buttons[i]) {
                row = coordinates[i][0];
                column = coordinates[i][1];
            }
        }

        // find out what to draw
        Tile tile = game.draw(row, column);

        // print on board
        Button button = (Button) view;
        switch(tile) {
            case CROSS:
                button.setText("X");
                break;
            case CIRCLE:
                button.setText("O");
                break;
            case INVALID:
                // popup message from:
                // https://developer.android.com/guide/topics/ui/notifiers/toasts.html#Positioning
                Context context = getApplicationContext();
                CharSequence text = "Invalid move!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;

            // check if AI has to play
//            if (AIOn && game.process() != GameState.PLAYER_ONE) {
//                // check if there's a good play
//
//                if (game.prevent()<9) {
//                    randTile = game.prevent();
//                } else {
//                    // find a random blank tile and find out what to draw
//                    Tile tileTest;
//                    do {
//                        Random rand = new Random();
//                        randTile = rand.nextInt(9);
//                        tileTest = game.test(coordinates[randTile][0], coordinates[randTile][1]);
//                    }
//                    while (tileTest == Tile.INVALID || row == coordinates[randTile][0] || column == coordinates[randTile][1]);
//                }
//
//                game.draw(coordinates[randTile][0],coordinates[randTile][1]);
//
//                // print on board
//
//                Button button2 = findViewById(buttons[randTile]);
//                button2.setText("O");
//            }
        }

        // find out who won and print message
        if (game.process() == GameState.DRAW) {
            Context context = getApplicationContext();
            CharSequence text = "It's a draw! Press new game";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else if (game.process() == GameState.PLAYER_ONE) {
            Context context = getApplicationContext();
            CharSequence text = "Player one wins! Press new game";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else if (game.process() == GameState.PLAYER_TWO) {
            Context context = getApplicationContext();
            CharSequence text = "Player two wins! Press new game";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void resetClicked(View view) {
        // clear all buttons and start new game
        for (int i : buttons) {
            Button button = findViewById(i);
            button.setText("");
        }
        game = new Game();
    }

    public void switchCheck(View view) {
        Switch aSwitch = findViewById(R.id.switch1);
        if (aSwitch.isChecked()) {
            AIOn = true;

            // clear all buttons and start new game
            for (int i : buttons) {
                Button button = findViewById(i);
                button.setText("");
            }
            game = new Game();
        } else {
            AIOn = false;
        }
    }
}

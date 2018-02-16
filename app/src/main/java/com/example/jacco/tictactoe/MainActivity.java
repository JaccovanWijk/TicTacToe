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

public class MainActivity extends AppCompatActivity {

    // variables
    Game game;
    int row;
    int column;
    String[] names = {"t1","t2","t3","t4","t5","t6","t7","t8","t9"};
    int[] buttons = {R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6, R.id.t7,
                     R.id.t8, R.id.t9};
    int[][] coordinates = {{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}};
    boolean gameOver;
    boolean AIOn;
    int randTile;

    // saving state buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        gameOver = false;

        if (savedInstanceState != null) {
            for (int i = 0; i < buttons.length; i++) {
                Button button = findViewById(buttons[i]);
                String state = button.getText().toString();
                if (button != null) {
                    button.setText(savedInstanceState.getString(state));
                }
            }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; i < buttons.length; i++) {
            Button button = findViewById(buttons[i]);
            outState.putString(names[i], button.getText().toString());
        }
    }

    public void tileClicked(View view) {
        // find clicked button
        if (gameOver == false) {
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
                    CharSequence text = "Already selected!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
            }

            // check if AI has to play
            if (AIOn && game.process() != GameState.PLAYER_ONE) {
                Tile tileTest;
                // find a blank tile and find out what to draw
                do {
                    Random rand = new Random();
                    randTile = rand.nextInt(9);
                    tileTest = game.test(coordinates[randTile][0],coordinates[randTile][1]);
                } while (tileTest == Tile.INVALID || row == coordinates[randTile][0] || column == coordinates[randTile][1]);
                game.draw(coordinates[randTile][0],coordinates[randTile][1]);

                // print on board
                Button button2 = findViewById(buttons[randTile]);
                button2.setText("O");
            }

            // check if game is over
            if (game.process() != GameState.IN_PROGRESS) {
                gameOver = true;
            }
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
        gameOver = false;
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
            gameOver = false;
        } else {
            AIOn = false;
        }
    }
}

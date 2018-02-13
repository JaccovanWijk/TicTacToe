package com.example.jacco.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Game game;
    int row;
    int column;
    String[] buttons = {"1","2","3","4","5","6","7","8","9"};
    int[][] coordinates = {{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
    }

    public void tileClicked(View view) {

        int id = view.getId();
        Button button = (Button) view;
        String name = button.getText().toString();

        int length = buttons.length;
        for (int i=0; i<length; i++) {
            if (name.equals(buttons[i])) {
                row = coordinates[i][0];
                column = coordinates[i][1];
            }
        }

        Tile tile = game.draw(row, column);

        switch(tile) {
            case CROSS:
                button.setText("X");
                break;
            case CIRCLE:
                button.setText("O");
                break;
            case INVALID:

                break;
        }

    }

    public void resetClicked(View view) {
        game = new Game();
    }
}

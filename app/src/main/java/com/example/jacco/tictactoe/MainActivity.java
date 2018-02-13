package com.example.jacco.tictactoe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Game game;
    int row;
    int column;
    int[] buttons = {R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6, R.id.t7,
                     R.id.t8, R.id.t9};
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

        int length = buttons.length;
        for (int i=0; i<length; i++) {
            if (id == buttons[i]) {
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
                // https://developer.android.com/guide/topics/ui/notifiers/toasts.html#Positioning
                Context context = getApplicationContext();
                CharSequence text = "Already selected!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
        }
    }

    public void resetClicked(View view) {
        for (int i : buttons) {
            Button button = findViewById(i);
            button.setText("");
            }

        game = new Game();
    }
}

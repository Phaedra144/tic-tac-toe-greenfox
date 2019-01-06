package com.szilvi.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    // 0 = unicorn, 1 = greenfox

    int activePlayer = 0;
    boolean gameIsActive = true;

    // 2 means unplayed

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    final int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    TextView winnerMessage;
    LinearLayout layout;
    TextView playersTurn;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        playersTurn = findViewById(R.id.playerTurn);

        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.unicorn);
                playersTurn.setText("It's fox's turn!");
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.greenfox);
                playersTurn.setText("It's unicorn's turn!");
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            checkWinnerAndSendMsg();
        }
    }

    private void checkWinnerAndSendMsg() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {

                // Someone has won!
                gameIsActive = false;
                String winner = "Green Fox";

                if (gameState[winningPosition[0]] == 0) {
                    winner = "Unicorn";
                }

                winnerMessage.setText(winner + " has won!");
                layout.setVisibility(View.VISIBLE);
                playersTurn.setVisibility(View.GONE);
                break;
            } else {
                boolean gameIsOver = true;
                for (int counterState : gameState) {
                    if (counterState == 2) gameIsOver = false;
                }

                if (gameIsOver) {
                    winnerMessage.setText("It's a draw");
                    layout.setVisibility(View.VISIBLE);
                    playersTurn.setVisibility(View.GONE);
                }
            }
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        winnerMessage = findViewById(R.id.winnerMessage);
        layout = findViewById(R.id.playAgainLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

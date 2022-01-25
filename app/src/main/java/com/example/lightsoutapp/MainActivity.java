package com.example.lightsoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lightsoutapp.Model.LightsOutGame;

public class MainActivity extends AppCompatActivity {

    private LightsOutGame mGame;
    private GridLayout mLightGrid;
    private int mLightOnColor;
    private int mLightOffColor;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLightGrid = findViewById(R.id.light_grid);

        mLightOnColor = ContextCompat.getColor(this, R.color.teal_200);
        mLightOffColor = ContextCompat.getColor(this, R.color.black);

        mGame = new LightsOutGame();
        startGame();
    }

    private void startGame() {
        mGame.newGame();
        setButtonColors();
    }

    public void onLightButtonClick(View view) {

        // Find the button's row and col
        int buttonIndex = mLightGrid.indexOfChild(view);
        int row = buttonIndex / LightsOutGame.GRID_SIZE;
        int col = buttonIndex % LightsOutGame.GRID_SIZE;

        //---------------------------------------

        if(row == 0 && col ==0){
            count += 1;
            if(count % 5 == 0){
                mGame.cheat();
                mGame.isGameOver();
            }
        }


        mGame.selectLight(row, col);
        setButtonColors();

        // Congratulate the user if the game is over
        if (mGame.isGameOver()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonColors() {

        // Set all buttons' background color
        for (int row = 0; row < LightsOutGame.GRID_SIZE; row++) {
            for (int col = 0; col < LightsOutGame.GRID_SIZE; col++) {

                // Find the button in the grid layout at this row and col
                int buttonIndex = row * LightsOutGame.GRID_SIZE + col;
                ImageButton gridButton = (ImageButton) mLightGrid.getChildAt(buttonIndex);

                if (mGame.isLightOn(row, col)) {
                    gridButton.setBackgroundColor(mLightOnColor);
                } else {
                    gridButton.setBackgroundColor(mLightOffColor);
                }
            }
        }
    }

    public void onNewGameClick(View view) {
        startGame();
    }
}


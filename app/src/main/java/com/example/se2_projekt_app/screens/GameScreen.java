package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.game.GameBoard;
import com.example.se2_projekt_app.game.GameBoardManager;
import com.example.se2_projekt_app.networking.json.ActionValues;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

public class GameScreen extends Activity implements ResponseReceiver {

    public static ResponseReceiver responseReceiver;
    private DrawerLayout drawerLayout;
    private Button toggleDrawerButton;
    private Button closeDrawerButton;
    private GameBoardManager gameBoardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);

        findViewById(R.id.debug_back).setOnClickListener(v -> finish());

        drawerLayout = findViewById(R.id.drawer_layout);
        toggleDrawerButton = findViewById(R.id.toggle_drawer_button);
        closeDrawerButton = findViewById(R.id.close_drawer_button);

        toggleDrawerButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        closeDrawerButton.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Translate the button with the drawer slide
                toggleDrawerButton.setTranslationX(slideOffset * drawerView.getWidth());
                toggleDrawerButton.setVisibility(slideOffset == 0 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                toggleDrawerButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                toggleDrawerButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Not used
            }
        });
    }

    @Override
    public void receiveResponse(JSONObject response) throws JSONException {
        String action = response.getString("action");
        String message = response.getString("message");
        String username = response.getString("username");

        switch (action) {
            case "updateGameBoardSimple":
                gameBoardManager.simpleUpdateGameBoard(message, username);
                break;

            case "updateGameBoardFull":
                gameBoardManager.fullUpdateGameBoard(message, username);
                break;

            default:
                break;
        }
    }
}

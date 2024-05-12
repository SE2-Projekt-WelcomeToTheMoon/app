package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game.GameBoardManager;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.views.GameBoardView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class GameScreen extends Activity {

    public static ResponseReceiver responseReceiver;

    private Button toggleDrawerButton;
    private ProgressBar progressBar;
    private TextView view;
    private GameBoardManager gameBoardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DrawerLayout drawerLayout;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        GameBoardView gameBoardView = findViewById(R.id.gameBoardView);
        gameBoardManager = new GameBoardManager(gameBoardView);

        String localUser = getIntent().getStringExtra("username");
        gameBoardManager.initGameBoard(new User(localUser));
        gameBoardManager.setLocalUsername(localUser);

        gameBoardManager.showGameBoard(gameBoardManager.getLocalUsername());

        findViewById(R.id.debug_back).setOnClickListener(v -> finish());
        findViewById(R.id.player1_button).setOnClickListener(v -> gameBoardManager.showGameBoard("Player1"));
        findViewById(R.id.player2_button).setOnClickListener(v -> gameBoardManager.showGameBoard("Player2"));
        findViewById(R.id.player3_button).setOnClickListener(v -> gameBoardManager.showGameBoard("Player3"));
        findViewById(R.id.player4_button).setOnClickListener(v -> gameBoardManager.showGameBoard("Player4"));

        findViewById(R.id.game_screen_accept_turn_button).setOnClickListener(v -> gameBoardManager.acceptTurn());

        // insert draw on touch values
        findViewById(R.id.game_screen_random_field_button).setOnClickListener(v -> gameBoardView.setFieldValue(FieldValue.getRandomFieldValue()));

        findViewById(R.id.game_screen_server_response_button).setOnClickListener(v -> mockServer());

        drawerLayout = findViewById(R.id.drawer_layout);
        toggleDrawerButton = findViewById(R.id.toggle_drawer_button);
        Button closeDrawerButton = findViewById(R.id.close_drawer_button);
//        progressBar = findViewById(R.id.progressbar);
        //view = findViewById(R.id.rocket_count);
//        view.setText("22"); // Testing purposes
//        progressBar.setMax(57);
//        progressBar.setProgress(15);
//        final int[] progress = {0};

//                progress[0] += user.getRockets;
        new Thread(() -> {
//                        progressBar.setProgress(progress[0], true);
//                        view.setText(String.valueOf(Username.user.getRockets()));
        });

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

        responseReceiver = response -> {
            if (response.getBoolean("success")) {
                String action = response.getString("action");
                String username = response.getString("username");
                String message = response.getString("message");
                switch (action) {
                    case "initUsers":
                        runOnUiThread(() -> {
                            Log.i("GameScreen", "Game started, got UserList: " + message);
                            List<String> usernames = deserializeUserList(message);
                            if (usernames != null) {
                                for (String user : usernames) {
                                    gameBoardManager.initGameBoard(new User(user));
                                }
                            }

                        });
                        break;
                    case "updateGameBoard":
                        runOnUiThread(() -> gameBoardManager.updateUser(username, message));
                        break;
                    default:
                        Log.w("GameScreen", "Server response has invalid or no sender. Response not routed.");
                }
            }
        };
    }

    public List<String> deserializeUserList(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(message, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            Log.e("GameScreen", "Error deserializing user list", e);
            return null;
        }
    }

    public void mockServer() {
        String player = "Player1";
        gameBoardManager.initGameBoard(new User(player));
        String response = "{\"floor\":0, \"chamber\":0, \"field\":0, \"fieldValue\":\"FIVE\"}";
        gameBoardManager.updateUser(player, response);
        gameBoardManager.initGameBoard(new User(player));
        response = "{\"floor\":8, \"chamber\":2, \"field\":1, \"fieldValue\":\"TEN\"}";
        gameBoardManager.updateUser(player, response);
    }
}

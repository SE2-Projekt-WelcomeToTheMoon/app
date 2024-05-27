package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game.GameBoardManager;
import com.example.se2_projekt_app.game.CardController;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.views.GameBoardView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen extends Activity {
    public static ResponseReceiver responseReceiver;
    private Button toggleDrawerButton;

//    private ProgressBar progressBar;
    private TextView view;
    private GameBoardManager gameBoardManager;
    private HashMap<String, String> playerMap;
    private CardController cardcontroller;
    private static final String TAG = "GameScreen";
    private static final String TAG_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DrawerLayout drawerLayout;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        GameBoardView gameBoardView = findViewById(R.id.gameBoardView);
        gameBoardManager = new GameBoardManager(gameBoardView);


        // get local user
        String localUser = getIntent().getStringExtra(TAG_USERNAME);
        Log.d(TAG, "Local user is " + localUser);
        gameBoardManager.setLocalUsername(localUser);

        playerMap = new HashMap<>();
        ArrayList<String> users = getIntent().getStringArrayListExtra("users");
        initUsers(users);

        gameBoardManager.showGameBoard(gameBoardManager.getLocalUsername());

        findViewById(R.id.debug_back).setOnClickListener(v -> finish());
        findViewById(R.id.player1_button).setOnClickListener(v -> gameBoardManager.showGameBoard(localUser));
        findViewById(R.id.player2_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player2"));
        });
        findViewById(R.id.player3_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player3"));
        });
        findViewById(R.id.player4_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player4"));
        });

        findViewById(R.id.game_screen_accept_turn_button).setOnClickListener(v -> gameBoardManager.acceptTurn());

        // insert draw on touch values
        findViewById(R.id.game_screen_random_field_button).setOnClickListener(v -> gameBoardView.setFieldValue(FieldValue.getRandomFieldValue()));

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
                String username = response.getString(TAG_USERNAME);
                String message = response.getString("message");
                switch (action) {
                    case "updateUser":
                        Log.d(TAG, "Received updateUser message {}" + message);
                        runOnUiThread(() -> gameBoardManager.updateUser(username, message));
                        break;
                    case "makeMove":
                        //placeholder
                        break;
                    default:
                        Log.w(TAG, "Server response has invalid or no sender. Response not routed.");
                }
            }
        };
    }

    void initUsers(ArrayList<String> users) {
        String localUser = getIntent().getStringExtra(TAG_USERNAME);
        if (users != null && !users.isEmpty()) {
            playerMap = new HashMap<>();
            playerMap.put("Player1", localUser);
            Log.i(TAG, "Player 1 is " + localUser);
            int count = 2;
            for (String user : users) {
                gameBoardManager.initGameBoard(new User(user));
                if (!user.equals(localUser)) {
                    Log.i(TAG, "Player " + count + " is " + user);
                    playerMap.put("Player" + count++, user);
                }
            }
        }
    }
}

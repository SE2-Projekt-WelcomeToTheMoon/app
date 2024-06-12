package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.enums.GameState;
import com.example.se2_projekt_app.game.CardCombination;
import com.example.se2_projekt_app.game.GameBoardManager;
import com.example.se2_projekt_app.game.CardController;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.views.CardDrawView;
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
    private static final String TAG = "GameScreen";
    private static final String TAG_USERNAME = "username";
    private GameState gameState = GameState.INITIAL;
    private String currentOwner = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DrawerLayout drawerLayout;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        Button btn_winner = findViewById(R.id.btn_winnerScreen);

        CardDrawView cardDrawView = findViewById(R.id.cardDrawView);
        GameBoardView gameBoardView = findViewById(R.id.gameBoardView);
        gameBoardManager = new GameBoardManager(gameBoardView, new CardController(cardDrawView, this));


        // get local user
        String localUser = getIntent().getStringExtra(TAG_USERNAME);
        Log.d(TAG, "Local user is " + localUser);
        gameBoardManager.setLocalUsername(localUser);

        playerMap = new HashMap<>();
        ArrayList<String> users = getIntent().getStringArrayListExtra("users");
        initUsers(users);

        gameBoardManager.showGameBoard(gameBoardManager.getLocalUsername());

        findViewById(R.id.debug_back).setOnClickListener(v -> finish());
        findViewById(R.id.player1_button).setOnClickListener(v -> {
            gameBoardManager.showGameBoard(localUser);
            view = findViewById(R.id.rocket_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(localUser))); // Testing purposes
            currentOwner = localUser;
        });
        findViewById(R.id.player2_button).setOnClickListener(v -> {

            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player2"));
            view = findViewById(R.id.rocket_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get("Player2")))); // Testing purposes
            currentOwner = playerMap.size() >= 2 ? playerMap.get("Player2") : "";
        });
        findViewById(R.id.player3_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player3"));
            view = findViewById(R.id.rocket_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get("Player3")))); // Testing purposes
            currentOwner = playerMap.size() >= 3 ? playerMap.get("Player3") : "";
        });
        findViewById(R.id.player4_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player4"));
            view = findViewById(R.id.rocket_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get("Player4")))); // Testing purposes
            currentOwner = playerMap.size() >= 4 ? playerMap.get("Player4") : "";
        });


        Button randomButton = findViewById(R.id.game_screen_random_field_button);
        randomButton.setText(gameState.toString());

        findViewById(R.id.game_screen_accept_turn_button).setOnClickListener(v -> gameBoardManager.acceptTurn());


        findViewById(R.id.game_screen_cheat_button).setOnClickListener(v -> {
            Log.e(TAG, String.valueOf(currentOwner));
            if (currentOwner.equals(localUser)) {
                Log.i(TAG, "Cheat");
                gameBoardManager.cheat();
            } else {
                if(!currentOwner.isEmpty()){
                    Log.i(TAG, "Detect cheat");
                    gameBoardManager.detectCheat(currentOwner);
                }
            }
        });

        // insert draw on touch values
        findViewById(R.id.game_screen_random_field_button).setOnClickListener(v -> gameBoardView.setCurrentSelection(new CardCombination(FieldCategory.ENERGY,FieldCategory.PLANNING,FieldValue.getRandomFieldValue())));

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
        btn_winner.setOnClickListener(v -> {
            Intent intent = new Intent(GameScreen.this, WinnerScreen.class);
            startActivity(intent);
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
                String message = response.optString("message", "");
                switch (action) {
                    case "makeMove":
                        Log.d(TAG, "Received makeMove message {}" + message);
                        runOnUiThread(() -> gameBoardManager.updateUser(username, message));
                        break;
                    case "playerHasCheated":
                        Log.d(TAG, "Player {} has cheated" + message);
                        runOnUiThread(() -> {
                            gameBoardManager.updateCheatedUser(username, message);
                            view = findViewById(R.id.rocket_count);
                            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(username)));
                        });
                        break;
                    case "playerDetectedCheatCorrect":
                        Log.d(TAG, "Player {} has cheated" + message);
                        runOnUiThread(() -> {
                            gameBoardManager.updateCorrectCheatDetection(username, message, true);

                            view = findViewById(R.id.rocket_count);
                            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(username)));
                        });
                        break;
                    case "playerDetectedCheatWrong":
                        Log.d(TAG, "Player {} has cheated" + message);
                        runOnUiThread(() -> {
                            gameBoardManager.updateCorrectCheatDetection(username, message, false);

                            view = findViewById(R.id.rocket_count);
                            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(username)));
                        });
                        break;
                    case "endGame":
                        Intent intent = new Intent(GameScreen.this, WinnerScreen.class);
                        startActivity(intent);
                        break;
                    case "updateCurrentCards":
                    case "nextCardDraw":
                        Log.d(TAG, "Updating to show next card drawn with message {}" + message);
                        gameBoardManager.extractCardsFromServerString(message);
                        gameBoardManager.displayCurrentCombination();
                        break;
                    case "notifyGameState":
                        Log.d(TAG, "Received notifyGameState message {}" + message);
                        gameState = GameState.valueOf(message);
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
                        break;
                    case "invalidCombination":
                    case "invalidMove":
                    case "alreadyMoved":
                        Log.d(TAG, "Received " + action);
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), action, Toast.LENGTH_SHORT).show());
                        break;
                    default:
                        Log.w(TAG, "Server response has invalid or no sender. Response not routed.");
                }
            }
        };
        gameBoardManager.updateCurrentCardDraw();
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

    public void setSelectedCard(CardCombination combination) {
        gameBoardManager.setSelectedCard(combination);
    }
}

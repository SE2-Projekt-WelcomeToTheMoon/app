package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game.CardCombination;
import com.example.se2_projekt_app.game.GameBoardManager;
import com.example.se2_projekt_app.game.CardController;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.views.CardDrawView;
import com.example.se2_projekt_app.views.GameBoardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameScreen extends Activity {
    public static ResponseReceiver responseReceiver;
    private Button toggleDrawerButton;

    //    private ProgressBar progressBar;
    private TextView view;
    private TextView txtview_syserror;
    private GameBoardManager gameBoardManager;
    private HashMap<String, String> playerMap;
    private static final String TAG = "GameScreen";
    private static final String TAG_USERNAME = "username";
    private String currentOwner = "";
    private DrawerLayout drawerLayout;

    private ImageView mission_card_a;

    private ImageView mission_card_b;

    private ImageView mission_card_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DrawerLayout drawerLayout;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        //Button btn_winner = findViewById(R.id.btn_winnerScreen);


        CardDrawView cardDrawView = findViewById(R.id.cardDrawView);
        GameBoardView gameBoardView = findViewById(R.id.gameBoardView);
        gameBoardManager = new GameBoardManager(gameBoardView,new CardController(cardDrawView,this));

        /*runOnUiThread(() -> {
            txtview_syserror = findViewById(R.id.error_count);
            txtview_syserror.setText(String.valueOf(4));
        });*/

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
            txtview_syserror = findViewById(R.id.error_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(localUser))); // Testing purposes
            currentOwner = localUser;
        });
        findViewById(R.id.player2_button).setOnClickListener(v -> {

            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player2"));
            view = findViewById(R.id.rocket_count);
            txtview_syserror = findViewById(R.id.error_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get("Player2")))); // Testing purposes
            currentOwner = playerMap.size() >= 2 ? playerMap.get("Player2") : "";
        });
        findViewById(R.id.player3_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player3"));
            view = findViewById(R.id.rocket_count);
            txtview_syserror = findViewById(R.id.error_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get("Player3")))); // Testing purposes
            currentOwner = playerMap.size() >= 3 ? playerMap.get("Player3") : "";
        });
        findViewById(R.id.player4_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get("Player4"));
            view = findViewById(R.id.rocket_count);
            txtview_syserror = findViewById(R.id.error_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get("Player4")))); // Testing purposes
            currentOwner = playerMap.size() >= 4 ? playerMap.get("Player4") : "";
        });

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

       /* btn_winner.setOnClickListener(v -> {
            //Intent intent = new Intent(GameScreen.this, WinnerScreen.class);
            //startActivity(intent);
            SendMessageService.sendMessage(JSONService.generateJSONObject("systemError", localUser, true, "", ""));
        });*/

        setupDrawer();

        responseReceiver = response -> {
            if (response.getBoolean("success")) {
                String action = response.getString("action");
                String username = response.getString(TAG_USERNAME);
                String message = response.getString("message");
                boolean success = Boolean.parseBoolean(response.getString("success"));

                switch (action) {
                    case "updateUser":
                        Log.d(TAG, "Received updateUser message {}" + message);
                        runOnUiThread(() -> gameBoardManager.updateUser(username, message));
                        break;
                    case "makeMove":
                        //placeholder
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
                    case "nextCardDraw":
                        Log.d(TAG, "Updating to show next card drawn with message {}"+message);
                        gameBoardManager.extractCardsFromServerString(message);
                        gameBoardManager.displayCurrentCombination();
                        break;
                    case "systemError":
                        Log.i(TAG, "GameScreen case SystemError errichet!: " + response);
                        int errorCount = response.getInt("points");
                        runOnUiThread(() -> {
                            gameBoardManager.updateSysErrorUser(username, errorCount);
                            txtview_syserror = findViewById(R.id.error_count);
                            int sysError = gameBoardManager.getSysErrorOfPlayer(username);
                            txtview_syserror.setText(String.valueOf(sysError));
                            Log.i(TAG, "GameScreen case SystemError errichet!: " + username + " " + sysError);
                        });
                        break;
                    case "initialMissionCards":
                        try {
                            JSONArray missionCardsArray = response.getJSONArray("missionCards");
                            runOnUiThread(() -> setupInitialMissionCards(missionCardsArray));
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing initial mission cards: " + e.getMessage());
                        }
                        break;
                    case "missionFlipped":
                        try {
                            String missionDescription = response.getString("missionDescription");
                            runOnUiThread(() -> updateMissionCardImage(missionDescription, true));
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing mission flipped message: " + e.getMessage());
                        }
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
    public void setSelectedCard(CardCombination combination){
        gameBoardManager.setSelectedCard(combination);
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        Button toggleDrawerButton = findViewById(R.id.toggle_drawer_button);
        Button closeDrawerButton = findViewById(R.id.close_drawer_button);

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

    public void setupInitialMissionCards(JSONArray missionCardsArray) {
        for (int i = 0; i < missionCardsArray.length(); i++) {
            try {
                JSONObject cardJson = missionCardsArray.getJSONObject(i);
                String missionDescription = cardJson.getString("missionDescription");
                boolean isFlipped = cardJson.getBoolean("flipped");
                updateMissionCardImage(missionDescription, isFlipped);
            } catch (JSONException e) {
                Log.e(TAG, "Error setting up initial mission cards: " + e.getMessage());
            }
        }
    }

    public void updateMissionCardImage(String missionDescription, boolean isFlipped) {
        Log.d(TAG, "Updating mission card image: " + missionDescription + ", flipped: " + isFlipped);

        int resourceId = 0;
        switch (missionDescription.toLowerCase()) {
            case "a1":
                resourceId = isFlipped ? R.drawable.a1_back : R.drawable.a1;
                break;
            case "a2":
                resourceId = isFlipped ? R.drawable.a2_back : R.drawable.a2;
                break;
            case "b1":
                resourceId = isFlipped ? R.drawable.b1_back : R.drawable.b1;
                break;
            case "b2":
                resourceId = isFlipped ? R.drawable.b2_back : R.drawable.b2;
                break;
            case "c1":
                resourceId = isFlipped ? R.drawable.c1_back : R.drawable.c1;
                break;
            case "c2":
                resourceId = isFlipped ? R.drawable.c2_back : R.drawable.c2;
                break;
            default:
                Log.e(TAG, "Invalid mission description: " + missionDescription);
                return;
        }

        char missionType = missionDescription.toLowerCase().charAt(0); // a, b, or c
        int imageViewId = 0;
        switch (missionType) {
            case 'a':
                imageViewId = R.id.mission_card_a;
                break;
            case 'b':
                imageViewId = R.id.mission_card_b;
                break;
            case 'c':
                imageViewId = R.id.mission_card_c;
                break;
            default:
                Log.e(TAG, "Invalid mission type: " + missionType);
                return;
        }

        ImageView imageView = findViewById(imageViewId);
        if (imageView != null && resourceId != 0) {
            imageView.setImageResource(resourceId);
        } else {
            Log.e(TAG, "Failed to update mission card image. ImageView or Resource not found.");
        }
    }
}

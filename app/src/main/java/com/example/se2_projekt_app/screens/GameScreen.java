package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.enums.GameState;
import com.example.se2_projekt_app.enums.MissionType;
import com.example.se2_projekt_app.game.CardCombination;
import com.example.se2_projekt_app.game.GameBoardManager;
import com.example.se2_projekt_app.game.CardController;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.views.CardDrawView;
import com.example.se2_projekt_app.views.GameBoardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class GameScreen extends Activity {
    @Getter
    @Setter
    private static ResponseReceiver responseReceiver;
    private Button toggleDrawerButton;
    private TextView view;
    private TextView txtViewSysError;
    private GameBoardManager gameBoardManager;
    private HashMap<String, String> playerMap;
    private static final String TAG = "GameScreen";
    private static final String TAG_USERNAME = "username";
    private GameState gameState = GameState.INITIAL;
    private String currentOwner = "";
    private DrawerLayout drawerLayout;
    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";
    private static final String PLAYER_3 = "Player3";
    private static final String PLAYER_4 = "Player4";
    private static final String PLAYER_HAS_CHEATED = "Player {} has cheated";
    private static final String INVALID_MISSION_TYPE = "Invalid mission type: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

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
            txtViewSysError = findViewById(R.id.error_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(localUser))); // Testing purposes
            currentOwner = localUser;
        });
        findViewById(R.id.player2_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get(PLAYER_2));
            view = findViewById(R.id.rocket_count);
            txtViewSysError = findViewById(R.id.error_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get(PLAYER_2)))); // Testing purposes
            currentOwner = playerMap.size() >= 2 ? playerMap.get(PLAYER_2) : "";
        });
        findViewById(R.id.player3_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get(PLAYER_3));
            view = findViewById(R.id.rocket_count);
            txtViewSysError = findViewById(R.id.error_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get(PLAYER_3)))); // Testing purposes
            currentOwner = playerMap.size() >= 3 ? playerMap.get(PLAYER_3) : "";
        });
        findViewById(R.id.player4_button).setOnClickListener(v -> {
            assert playerMap != null;
            gameBoardManager.showGameBoard(playerMap.get(PLAYER_4));
            view = findViewById(R.id.rocket_count);
            txtViewSysError = findViewById(R.id.error_count);
            view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(playerMap.get(PLAYER_4)))); // Testing purposes
            currentOwner = playerMap.size() >= 4 ? playerMap.get(PLAYER_4) : "";
        });


        Button randomButton = findViewById(R.id.game_screen_random_field_button);
        randomButton.setText(gameState.toString());

        findViewById(R.id.game_screen_accept_turn_button).setOnClickListener(v -> gameBoardManager.acceptTurn());

        setupCheatButton(localUser);

        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(this::updateCards, 2000);

        // insert draw on touch values
        findViewById(R.id.game_screen_random_field_button).setOnClickListener(v -> gameBoardView.setCurrentSelection(new CardCombination(FieldCategory.ENERGY, FieldCategory.PLANNING, FieldValue.getRandomFieldValue())));

        drawerLayout = findViewById(R.id.drawer_layout);
        toggleDrawerButton = findViewById(R.id.toggle_drawer_button);
        Button closeDrawerButton = findViewById(R.id.close_drawer_button);

        toggleDrawerButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        setupDrawer();
    }

    private void setupCheatButton(String localUser) {
        findViewById(R.id.game_screen_cheat_button).setOnClickListener(v -> {
            Log.e(TAG, String.valueOf(currentOwner));
            if (currentOwner.equals(localUser)) {
                Log.i(TAG, "Cheat");
                gameBoardManager.cheat();
            } else {
                if (!currentOwner.isEmpty()) {
                    Log.i(TAG, "Detect cheat");
                    gameBoardManager.detectCheat(currentOwner);
                }
            }
        });
    }

    private void handleResponse(JSONObject response) throws JSONException {
        if (response.getBoolean("success")) {
            String action = response.getString("action");
            String username = response.getString(TAG_USERNAME);
            String message = response.optString("message", "");
            switch (action) {
                case "addRocket":
                    Log.d(TAG, "Received addRocket message {}" + message);
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Added " + Integer.parseInt(message) + " Rockets", Toast.LENGTH_SHORT).show());
                    gameBoardManager.addRocketUser(username, Integer.parseInt(message));
                    break;
                case "makeMove":
                    Log.d(TAG, "Received makeMove message {}" + message);
                    runOnUiThread(() -> gameBoardManager.updateUser(username, message));
                    break;
                case "playerHasCheated":
                    Log.d(TAG, PLAYER_HAS_CHEATED + message);
                    runOnUiThread(() -> {
                        gameBoardManager.updateCheatedUser(username, message);
                        view = findViewById(R.id.rocket_count);
                        view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(username)));
                    });
                    break;
                case "playerDetectedCheatCorrect":
                    Log.d(TAG, PLAYER_HAS_CHEATED + message);
                    runOnUiThread(() -> {
                        gameBoardManager.updateCorrectCheatDetection(username, message, true);
                        view = findViewById(R.id.rocket_count);
                        view.setText(String.valueOf(gameBoardManager.getRocketsOfPlayer(username)));
                    });
                    break;
                case "playerDetectedCheatWrong":
                    Log.d(TAG, PLAYER_HAS_CHEATED + message);
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
                    updateChamberOutline();
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
                case "systemError":
                    Log.i(TAG, "GameScreen case SystemError errichet!: " + response);
                    int errorCount = response.getInt("points");
                    runOnUiThread(() -> {
                        gameBoardManager.updateSysErrorUser(username, errorCount);
                        txtViewSysError = findViewById(R.id.error_count);
                        int sysError = gameBoardManager.getSysErrorOfPlayer(username);
                        txtViewSysError.setText(String.valueOf(sysError));
                        Log.i(TAG, "GameScreen case SystemError errichet!: " + username + " " + sysError);
                    });
                    break;
                case "initialMissionCards":
                    try {
                        JSONArray missionCardsArray = response.getJSONArray("missionCards");
                        Log.d(TAG, "Mission Cards received: " + missionCardsArray.toString());
                        runOnUiThread(() -> setupInitialMissionCards(missionCardsArray));
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing initial mission cards: " + e.getMessage());
                    }
                    break;
                case "missionFlipped":
                    try {
                        MissionType missionType = MissionType.valueOf(response.getString("missionType"));
                        boolean isFlipped = response.getBoolean("flipped");
                        runOnUiThread(() -> updateMissionCardImage(missionType, isFlipped));
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing mission flipped message: " + e.getMessage());
                    }
                    break;
                default:
                    Log.w(TAG, "Server response has invalid or no sender. Response not routed.");
            }
        }
    }

    void initUsers(ArrayList<String> users) {
        String localUser = getIntent().getStringExtra(TAG_USERNAME);
        if (users != null && !users.isEmpty()) {
            playerMap = new HashMap<>();
            playerMap.put(PLAYER_1, localUser);
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

    public void updateCards() {
        gameBoardManager.updateCurrentCardDraw();
    }

    public void updateChamberOutline() {
        gameBoardManager.updateChamberOutline();
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
                MissionType missionType = MissionType.valueOf(cardJson.getString("missionType").toUpperCase());
                boolean isFlipped = cardJson.getBoolean("flipped");
                updateMissionCardImage(missionType, isFlipped);
            } catch (JSONException | IllegalArgumentException e) {
                Log.e(TAG, "Error setting up initial mission cards: " + e.getMessage());
            }
        }
    }

    public void updateMissionCardImage(MissionType missionType, boolean isFlipped) {
        int resourceId;
        int imageViewId;

        switch (missionType) {
            case A1:
            case A2:
                resourceId = isFlipped ? getBackResourceId(missionType) : getFrontResourceId(missionType);
                imageViewId = R.id.mission_card_a;
                break;
            case B1:
            case B2:
                resourceId = isFlipped ? getBackResourceId(missionType) : getFrontResourceId(missionType);
                imageViewId = R.id.mission_card_b;
                break;
            case C1:
            case C2:
                resourceId = isFlipped ? getBackResourceId(missionType) : getFrontResourceId(missionType);
                imageViewId = R.id.mission_card_c;
                break;
            default:
                Log.e(TAG, INVALID_MISSION_TYPE + missionType);
                return;
        }

        updateImageView(imageViewId, resourceId);
    }

    private int getBackResourceId(MissionType missionType) {
        switch (missionType) {
            case A1: return R.drawable.a1_back;
            case A2: return R.drawable.a2_back;
            case B1: return R.drawable.b1_back;
            case B2: return R.drawable.b2_back;
            case C1: return R.drawable.c1_back;
            case C2: return R.drawable.c2_back;
            default: throw new IllegalArgumentException(INVALID_MISSION_TYPE + missionType);
        }
    }

    private int getFrontResourceId(MissionType missionType) {
        switch (missionType) {
            case A1: return R.drawable.a1;
            case A2: return R.drawable.a2;
            case B1: return R.drawable.b1;
            case B2: return R.drawable.b2;
            case C1: return R.drawable.c1;
            case C2: return R.drawable.c2;
            default: throw new IllegalArgumentException(INVALID_MISSION_TYPE + missionType);
        }
    }

    private void updateImageView(int imageViewId, int resourceId) {
        ImageView imageView = findViewById(imageViewId);
        if (imageView != null && resourceId != 0) {
            imageView.setImageResource(resourceId);
        } else {
            Log.e(TAG, "Failed to update mission card image. ImageView or Resource not found.");
        }
    }
}

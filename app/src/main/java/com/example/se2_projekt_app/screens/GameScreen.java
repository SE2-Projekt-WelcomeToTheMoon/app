package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

public class GameScreen extends Activity implements ResponseReceiver {

    public static ResponseReceiver responseReceiver;
    private DrawerLayout drawerLayout;
    private Button toggleDrawerButton;
    private Button closeDrawerButton;
    private ProgressBar progressBar;
    private TextView view;
    private GameBoardManager gameBoardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        GameBoardView gameBoardView = findViewById(R.id.gameBoardView);
        gameBoardManager = new GameBoardManager(gameBoardView);

        for (int i = 1; i < 5; i++) {
            gameBoardManager.initGameBoard(new User("Player" + i));
        }

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
        closeDrawerButton = findViewById(R.id.close_drawer_button);
//        progressBar = findViewById(R.id.progressbar);
        //view = findViewById(R.id.rocket_count);
//        view.setText("22"); // Testing purposes
//        progressBar.setMax(57);
//        progressBar.setProgress(15);
//        final int[] progress = {0};

//                progress[0] += user.getRockets;
        new Thread(new Runnable() {
            @Override
            public void run() {
//                        progressBar.setProgress(progress[0], true);
//                        view.setText(String.valueOf(Username.user.getRockets()));
            }
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
    }

    @Override
    public void receiveResponse(JSONObject response) throws JSONException {
        String username = response.getString("username");
        String action = response.getString("action");

        switch (action) {
            case "updateGameBoard":
                gameBoardManager.updateUser(response.getString("message"), username);
                break;
            case "newScore":
                // get value from message
                break;
            case "newDraw":
                // override current draw
                break;
            case "makeMove":
                // make move
                break;
            default:
                // error handling
        }
    }

    public void mockServer(){
        gameBoardManager.initGameBoard(new User("Player1"));
        String response = "{\"floor\":0, \"chamber\":0, \"field\":0, \"fieldValue\":\"FIVE\"}";
        gameBoardManager.updateUser("Player1", response);
        gameBoardManager.initGameBoard(new User("Player1"));
        response = "{\"floor\":8, \"chamber\":2, \"field\":1, \"fieldValue\":\"TEN\"}";
        gameBoardManager.updateUser("Player1", response);
    }
}

package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.WebSocketClient;
import com.example.se2_projekt_app.networking.json.JSONKeys;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.networking.services.SendMessageService;

import org.json.JSONObject;

public class MainMenu extends Activity{
    public static ResponseReceiver responseReceiver;
    private static final String TAG = "MainMenu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // simplified buttons and listeners
        findViewById(R.id.startSP).setOnClickListener(this::startSP);
        findViewById(R.id.startMP).setOnClickListener(this::startMP);
        findViewById(R.id.settings).setOnClickListener(this::openSettings);
        findViewById(R.id.highscore).setOnClickListener(this::openHighscore);
        findViewById(R.id.exit).setOnClickListener(v -> {
            // Exit the game
            // Closing connection to server
            WebSocketClient.setDisconPurpously(true);
            JSONObject sendMessage = JSONService.generateJSONObject(JSONKeys.DISCONNECT.getValue(),
                    Username.user.getUsername(),true, "", "");
            SendMessageService.sendMessage(sendMessage);

            responseReceiver = exitResponse -> {
                boolean success = exitResponse.getBoolean(JSONKeys.SUCCESS.getValue());
                if(success){
                    Username.webSocketClient.disconnectFromServer();
                    this.finishAffinity();
                    Log.i(TAG, "User disconnected.");
                }else{
                    Log.w(TAG, "Username couldn't disconnect.");
                }
            };
        });
        findViewById(R.id.debug).setOnClickListener(this::openDebug);
    }

    public void startSP(View view) {
        // Start the game in Singleplayer
        Intent intent = new Intent(this, Singleplayer.class);
        startActivity(intent);

    }

    public void startMP(View view) {
        Intent intent = new Intent(this, Multiplayer.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(intent);
    }

    public void openSettings(View view) {
        // Open the settings
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

    }

    public void openHighscore(View view) {
        Intent intent = new Intent(this, Highscore.class);
        startActivity(intent);
        // Open the highscore
    }

    public void openDebug(View view) {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}

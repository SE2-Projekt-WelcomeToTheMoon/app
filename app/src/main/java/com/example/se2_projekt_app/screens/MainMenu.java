package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.json.JSONKeys;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.networking.services.SendMessageService;

import org.json.JSONObject;

import lombok.SneakyThrows;

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
        findViewById(R.id.exit).setOnClickListener(this::exit);
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

    @SneakyThrows
    public void exit(View view) {
        // Exit the game
        // Closing connection to server
        JSONObject sendMessage = JSONService.generateJSONObject(JSONKeys.DISCONNECT.getValue(),
                Username.user.getUsername(),true, "", "");
        SendMessageService.sendMessage(sendMessage);

        responseReceiver = exitResponse -> {
            String username = exitResponse.getString(JSONKeys.USERNAME.getValue());
            boolean success = exitResponse.getBoolean(JSONKeys.SUCCESS.getValue());
            String message = exitResponse.getString(JSONKeys.MESSAGE.getValue());
            if(success){
                Username.webSocketClient.disconnectFromServer();
                this.finishAffinity();
                Log.i(TAG, "User disconnected.");
            }else{
//                runOnUiThread(() -> {
//                    outputText.setText(message);
//                    outputText.setBackgroundColor(0x8003DAC5);
//                });
                Log.w(TAG, "Username couldn't disconnect.");
            }
        };

    }

    public void openDebug(View view) {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}

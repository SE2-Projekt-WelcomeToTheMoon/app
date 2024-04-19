package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.ResponseHandler;
import com.example.se2_projekt_app.networking.WebSocketClient;
import com.example.se2_projekt_app.networking.services.JSON.ActionValues;
import com.example.se2_projekt_app.networking.services.JSON.GenerateJSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import lombok.SneakyThrows;

public class MainMenu extends Activity{

    public static WebSocketClient webSocket;
    private final Logger logger = LogManager.getLogger(MainMenu.class);
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


        // Establishing connection to server
        webSocket = new WebSocketClient();

        // Handle response from server
        ResponseHandler responseHandler = message -> {
            String action = message.getString("action");
            boolean success = message.getBoolean("success");
            if(action.equals("registerUser") && success){
                logger.info("Username set to: "+ message.getString("username"));
            }else{logger.warn("Username couldn't be set: " + message.getString("message"));}
        };
        webSocket.connectToServer(responseHandler);

        // Generating JSONObject to send message to server
        JSONObject msg = GenerateJSONObject.generateJSONObject(ActionValues.REGISTERUSER.getValue(),
                "Dummy", null,"", "");

        // Sending message to server to register dummy user
        webSocket.sendMessageToServer(msg);
    }

    public void startSP(View view) {
        // Start the game in Singleplayer
        Intent intent = new Intent(this, Singleplayer.class);
        startActivity(intent);

    }

    public void startMP(View view) {
        Intent intent = new Intent(this, Multiplayer.class);
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
        webSocket.disconnectFromServer();
        finish();
    }

    public void openDebug(View view) {
        Intent intent = new Intent(this, Debug.class);
        startActivity(intent);
    }
}

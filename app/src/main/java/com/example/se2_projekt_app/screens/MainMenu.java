package com.example.se2_projekt_app.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.ConnectionHandler;
import com.example.se2_projekt_app.networking.JSON.ActionValues;
import com.example.se2_projekt_app.networking.JSON.GenerateJSONObject;
import com.example.se2_projekt_app.networking.ServerResponseListener;
import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends Activity implements ServerResponseListener {

    public static ConnectionHandler connectionHandler = new ConnectionHandler();

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

        // Creating Listener for Activity
        connectionHandler.setServerResponseListener(this);

        // Establishing connection to server
        connectionHandler.connectToWebSocketServer();
        JSONObject msg;
        try {
            msg = GenerateJSONObject.generateJSONObject();
            msg.put("username", "Dummy");
            msg.put("action", ActionValues.REGISTERUSER.getValue());
            // Sending message to server to register dummy user
            connectionHandler.sendMessage(msg);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

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

    public void exit(View view) {
        // Exit the game
        finish();
    }

    public void openDebug(View view) {
        Intent intent = new Intent(this, Debug.class);
        startActivity(intent);
    }

    /**
     * Method to handle message received form server.
     * @param response Message from server to handle.
     */
    @Override
    public void onResponseReceived(JSONObject response) {
        try {
            if(response.getString("action").equals("registeredUser") && response.getBoolean("success")){
                System.out.println("Username set to: "+ response.getString("username"));
            }
            else{
                System.out.println("Username couldn't be set: " + response.getString("message"));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}

package com.example.se2_projekt_app.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.ConnectionHandler;
import com.example.se2_projekt_app.networking.JSON.ActionValues;
import com.example.se2_projekt_app.networking.JSON.GenerateJSONObject;
import com.example.se2_projekt_app.networking.WebSocketClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainMenu extends Activity{

    public static ConnectionHandler connectionHandler = new ConnectionHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button startSP = findViewById(R.id.startSP);
        Button startMP = findViewById(R.id.startMP);
        Button settings = findViewById(R.id.settings);
        Button highscore = findViewById(R.id.highscore);
        Button exit = findViewById(R.id.exit);

        connectionHandler.connectToWebSocketServer();
        JSONObject msg;
        try {
            msg = GenerateJSONObject.generateJSONObject();
            msg.put("Username", "Dummy");
            msg.put("Action", ActionValues.REGISTERUSER.getValue());
            connectionHandler.sendMessage(msg);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        startSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSP(v);
            }
        });
        startMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMP(v);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings(v);
            }
        });
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHighscore(v);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit(v);
            }
        });

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
}

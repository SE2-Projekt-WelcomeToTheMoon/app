package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.json.ActionValues;
import com.example.se2_projekt_app.networking.json.GenerateJSONObject;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

import org.json.JSONObject;

import java.util.ArrayList;

public class Multiplayer extends Activity {

    private UserListAdapter userListAdapter;

    //Object implements method to handle response received from server
    public static ResponseReceiver responseReceiver;

    //Tag needed for logger
    private static final String TAG = "Multiplayer";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        RecyclerView userListView = findViewById(R.id.lobbyUserList);
        Button startGameButton = findViewById(R.id.startGameButton);
        Button backButton = findViewById(R.id.backButton);

        userListAdapter = new UserListAdapter(new ArrayList<>());
        userListView.setAdapter(userListAdapter);
        userListView.setLayoutManager(new LinearLayoutManager(this));



        backButton.setOnClickListener(v -> finish());

        startGameButton.setOnClickListener(v -> {

            // Generating JSONObject to send message to server
            JSONObject msg = GenerateJSONObject.generateJSONObject(
                    ActionValues.JOINLOBBY.getValue(), "Dummy", null,"",
                    "");

            // Sending message to server to join a lobby as dummy user via websocket object
            // instantiated in MainMenu view
            MainMenu.webSocket.sendMessageToServer(msg);

            // Handling response from server
            responseReceiver = response -> {
                boolean success = response.getBoolean("success");
                if(success){
                    String newUsername = response.getString("username");
                    User newUser = new User(newUsername);
                    runOnUiThread(() -> {
                        userListAdapter.addUser(newUser);
                        userListAdapter.notifyDataSetChanged();
                    });
                    Log.i(TAG,"User " + newUsername + " added to lobby.");
                }else{
                    runOnUiThread(() -> Toast.makeText(Multiplayer.this,
                            "Failed to join lobby. Please try again.",
                            Toast.LENGTH_LONG).show());

                    Log.w(TAG,"Failed to join lobby. Please try again.");
                }
            };
        });
    }
}

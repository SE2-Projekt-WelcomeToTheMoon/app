package com.example.se2_projekt_app.screens;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;
import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.JSON.ActionValues;
import com.example.se2_projekt_app.networking.JSON.GenerateJSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Multiplayer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        RecyclerView userListView = findViewById(R.id.lobbyUserList);
        Button startGameButton = findViewById(R.id.startGameButton);
        Button backButton = findViewById(R.id.backButton);

        UserListAdapter userListAdapter = new UserListAdapter(new ArrayList<>());
        userListView.setAdapter(userListAdapter);
        userListView.setLayoutManager(new LinearLayoutManager(this));

        // Using lambda expressions for setting click listeners
        backButton.setOnClickListener(v -> finish());
        startGameButton.setOnClickListener(v -> {
            try {
                JSONObject msg = GenerateJSONObject.generateJSONObject();
                msg.put("username", "Dummy");
                msg.put("action", ActionValues.JOINLOBBY.getValue());
                MainMenu.connectionHandler.sendMessage(msg);

                JSONObject response = MainMenu.connectionHandler.getResponse();
                if(response.getString("action").equals("joinedLobby") && response.getBoolean("success")){
                    // Assuming response contains a "username" to add
                    String newUsername = response.getString("username"); // Adjust according to actual JSON structure
                    User newUser = new User(newUsername);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userListAdapter.addUser(newUser);
                        }
                    });
                }
                else {
                    // Displaying Toast message
                    Toast.makeText(Multiplayer.this, "Failed to join lobby. Please try again.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Multiplayer.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

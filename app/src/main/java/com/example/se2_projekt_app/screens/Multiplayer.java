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
import com.example.se2_projekt_app.networking.ServerResponseListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Multiplayer extends Activity implements ServerResponseListener {

    private UserListAdapter userListAdapter;

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

        // Creating Listener for Activity
        MainMenu.connectionHandler.setServerResponseListener(this);

        backButton.setOnClickListener(v -> finish());
        startGameButton.setOnClickListener(v -> {
            try {
                JSONObject msg = GenerateJSONObject.generateJSONObject();
                msg.put("username", "Dummy");
                msg.put("action", ActionValues.JOINLOBBY.getValue());
                // Sending message to server to join a lobby as dummy user
                MainMenu.connectionHandler.sendMessage(msg);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Multiplayer.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Method to handle message received from server.
     * @param response Message from server to handle.
     */
    @Override
    public void onResponseReceived(JSONObject response) {
        try {
            if (response.getString("action").equals("joinedLobby") && response.getBoolean("success")) {
                String newUsername = response.getString("username");
                User newUser = new User(newUsername);
                runOnUiThread(() -> {
                    userListAdapter.addUser(newUser);
                    userListAdapter.notifyDataSetChanged();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(Multiplayer.this, "Failed to join lobby. Please try again.", Toast.LENGTH_LONG).show());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(Multiplayer.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }
}
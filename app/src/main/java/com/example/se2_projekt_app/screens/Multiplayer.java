package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.ResponseHandler;
import com.example.se2_projekt_app.networking.services.JSON.ActionValues;
import com.example.se2_projekt_app.networking.services.JSON.GenerateJSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;

public class Multiplayer extends Activity {

    private UserListAdapter userListAdapter;
    private final Logger logger = LogManager.getLogger(Multiplayer.class);

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

            // Sending message to server to join a lobby as dummy user


            // Handling response from server
            ResponseHandler responseHandler = message -> {
                String action = message.getString("action");
                boolean success = message.getBoolean("success");

                if(action.equals("joinLobby") && success){
                    String newUsername = message.getString("username");
                    User newUser = new User(newUsername);
                    runOnUiThread(() -> {
                        userListAdapter.addUser(newUser);
                        userListAdapter.notifyDataSetChanged();
                    });
                    logger.info("User " + newUsername + " added to lobby.");
                }else{
                    runOnUiThread(() -> Toast.makeText(Multiplayer.this,
                            "Failed to join lobby. Please try again.",
                            Toast.LENGTH_LONG).show());

                    logger.warn("Failed to join lobby. Please try again.");
                }
            };
            MainMenu.webSocket.connectToServer(responseHandler);

            MainMenu.webSocket.sendMessageToServer(msg);
        });
    }
}

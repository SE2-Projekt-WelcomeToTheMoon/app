package com.example.se2_projekt_app.screens;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.WebSocketClient;
import java.util.ArrayList;

public class Multiplayer extends Activity {

    private RecyclerView userListView;
    private Button startGameButton;
    private UserListAdapter userListAdapter;
    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        userListView = findViewById(R.id.lobbyUserList);
        startGameButton = findViewById(R.id.startGameButton);

        userListAdapter = new UserListAdapter(new ArrayList<>());
        userListView.setAdapter(userListAdapter);
        userListView.setLayoutManager(new LinearLayoutManager(this));

        Button back = findViewById(R.id.multiplayer_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Assuming WebSocketClient has methods to handle lobby logic
        webSocketClient = new WebSocketClient();
        webSocketClient.connectToServer(message -> {
            // Update your user list based on messages received from the server
            // This assumes the server sends a list of users whenever a new user joins or leaves
            runOnUiThread(() -> {
                // Update userListAdapter with new user data
            });
        });

        startGameButton.setOnClickListener(v -> {
            // Send message to server to start game if conditions are met (e.g., minimum number of players)
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
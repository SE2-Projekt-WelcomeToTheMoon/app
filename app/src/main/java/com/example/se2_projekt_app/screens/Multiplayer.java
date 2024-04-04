package com.example.se2_projekt_app.screens;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import com.example.se2_projekt_app.R;
import java.util.ArrayList;

public class Multiplayer extends Activity {

    private RecyclerView userListView;
    private Button startGameButton;

    private Button backButton;
    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        userListView = findViewById(R.id.lobbyUserList);
        startGameButton = findViewById(R.id.startGameButton);
        backButton = findViewById(R.id.backButton);

        userListAdapter = new UserListAdapter(new ArrayList<>());
        userListView.setAdapter(userListAdapter);
        userListView.setLayoutManager(new LinearLayoutManager(this));

        // Using lambda expressions for setting click listeners
        backButton.setOnClickListener(v -> finish());
        startGameButton.setOnClickListener(v -> finish());
    }
}
package com.example.se2_projekt_app.screens;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.widget.Button;
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
                if(response.getString("action").equals("joinedLobby") && response.getString("success").equals("true")){
                    //TODO add user to UserListAdapter view
                }
                else {
                    //TODO implement error output to user screen
                    System.out.println("An error occurred when joining the lobby, please try again.");
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

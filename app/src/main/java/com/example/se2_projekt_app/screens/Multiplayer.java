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
                msg.put("Username", "Dummy");
                msg.put("Action", ActionValues.JOINLOBBY.getValue());
                MainMenu.connectionHandler.sendMessage(msg);

                JSONObject response = MainMenu.connectionHandler.getResponse();
//                if(response.getString("Action").equals("joinedLobby") && response.getString("Success").equals("true")){
//
//                }
//                else {
//
//                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

//            try {
//                joinLobby();
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
        });
    }

//    public void joinLobby() throws JSONException {
//        JSONObject msg = GenerateJSONObject.generateJSONObject();
//        msg.put("Username", "Dummy");
//        msg.put("Action", ActionValues.JOINLOBBY.getValue());
//        MainMenu.connectionHandler.sendMessage(msg);
//
//        JSONObject response = MainMenu.connectionHandler.getResponse();
//        if(response.getString("Action").equals("joinedLobby") && response.getString("Success").equals("true")){
//            this.userListAdapter
//        }
//        else {
//
//        }
//    }
}
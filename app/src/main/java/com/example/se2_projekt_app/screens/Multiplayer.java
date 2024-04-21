package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.lobby.LobbyManager;
import com.example.se2_projekt_app.networking.json.ActionValues;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import org.json.JSONObject;


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
        Button joinLobbyButton = findViewById(R.id.btn_joinLobby);
        Button leaveLobbyButton = findViewById(R.id.btn_leaveLobby);

        userListAdapter = new UserListAdapter(LobbyManager.getInstance().getUsers());
        userListView.setAdapter(userListAdapter);
        userListView.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener(v -> finish());

        startGameButton.setOnClickListener(v -> {

            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.JOINLOBBY.getValue(), "Dummy", null,"",
                    "");

            MainMenu.webSocket.sendMessageToServer(msg);

            responseReceiver = response -> {
                boolean success = response.getBoolean("success");
                if(success){
                    String newUsername = response.getString("username");
                    User newUser = new User(newUsername);
                    runOnUiThread(() -> {
                        userListAdapter.addUser(newUser);
                        userListAdapter.notifyDataSetChanged();
                    });
                    Log.i(TAG, "User " + newUsername + " added to lobby.");

                }
            };
        });
        joinLobbyButton.setOnClickListener(v -> {
            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.JOINLOBBY.getValue(), "Dummy", null,"",
                    "");
            MainMenu.webSocket.sendMessageToServer(msg);

            responseReceiver = response -> {
                boolean success = response.getBoolean("success");
                if(success){
                    String username = response.getString("username");
                    User user = new User(username);
                    runOnUiThread(() -> {
                        userListAdapter.addUser(user);
                        userListAdapter.notifyDataSetChanged();
                    });
                    Log.i(TAG, "User " + username + " added to lobby.");

                }
            };
        });


        leaveLobbyButton.setOnClickListener(v -> {
            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.LEAVELOBBY.getValue(), "Dummy", null,"",
                    "");
            MainMenu.webSocket.sendMessageToServer(msg);

            responseReceiver = response -> {
                boolean success = response.getBoolean("success");
                if(success){
                    String username = response.getString("username");
                    User user = new User(username);
                    runOnUiThread(() -> {
                        userListAdapter.removeUser(user);
                        userListAdapter.notifyDataSetChanged();
                    });
                    Log.i(TAG, "User " + username + " removed from lobby.");

                }
            };
        });

        startGameButton.setOnClickListener(v -> {
            finish();
        });

    }
}

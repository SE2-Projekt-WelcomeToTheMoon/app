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


public class    Multiplayer extends Activity {

    private UserListAdapter userListAdapter;

    //Object implements method to handle response received from server
    public static ResponseReceiver responseReceiver;
    public static ResponseReceiver startGameResponseReceiver;

    //Tag needed for logger
    private static final String TAG = "Multiplayer";
    private static final String SUCCESS = "success";



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

        joinLobbyButton.setOnClickListener(v -> {
            String username = Username.user.getUsername();
            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.JOINLOBBY.getValue(), username, null,"",
                    "");
            Username.webSocketClient.sendMessageToServer(msg);

            responseReceiver = response -> {
                boolean success = response.getBoolean(SUCCESS);
                if(success){
                    runOnUiThread(() -> {
                        userListAdapter.addUser(Username.user);
                        userListAdapter.notifyDataSetChanged();
                    });
                    Log.i(TAG, "User " + username + " added to lobby.");

                }
            };
        });


        leaveLobbyButton.setOnClickListener(v -> {
            String username = Username.user.getUsername();
            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.LEAVELOBBY.getValue(), username, null,"",
                    "");
            Username.webSocketClient.sendMessageToServer(msg);

            responseReceiver = response -> {
                boolean success = response.getBoolean(SUCCESS);
                if(success){
                    runOnUiThread(() -> {
                        userListAdapter.removeUser(Username.user);
                        userListAdapter.notifyDataSetChanged();
                    });
                    Log.i(TAG, "User " + username + " removed from lobby.");

                }
            };
        });

        Multiplayer.startGameResponseReceiver = response -> {
            boolean success = response.getBoolean(SUCCESS);
            if(success){
                runOnUiThread(() -> {
                    Log.i(TAG, "Switched to game view");
                    setContentView(R.layout.activity_multiplayer_game);
                });
            }
        };

        startGameButton.setOnClickListener(v -> {
            String username = Username.user.getUsername();
            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.STARTGAME.getValue(), username, null,"",
                    "");
            Username.webSocketClient.sendMessageToServer(msg);
            Multiplayer.responseReceiver = response -> {
                boolean success = response.getBoolean(SUCCESS);
                if(success){
                    Log.i(TAG, "Started game successfully");
                }
            };
        });

    }
}
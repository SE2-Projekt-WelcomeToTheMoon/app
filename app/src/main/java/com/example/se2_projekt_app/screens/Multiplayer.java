package com.example.se2_projekt_app.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.json.ActionValues;
import com.example.se2_projekt_app.networking.json.JSONKeys;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.networking.services.SendMessageService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Multiplayer extends Activity {

    private UserListAdapter userListAdapter = new UserListAdapter(new ArrayList<>());

    //Object implements method to handle response received from server
    public static ResponseReceiver responseReceiver;
    public static ResponseReceiver startGameResponseReceiver;

    //Tag needed for logger
    private static final String TAG = "Multiplayer";
    private static final String TAG_USERS = "users";
    private static final String SUCCESS = JSONKeys.SUCCESS.getValue();
    private static final String USERNAME = JSONKeys.USERNAME.getValue();
    private static final String ACTION = JSONKeys.ACTION.getValue();




    // fixme extract methods!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        RecyclerView userListView = findViewById(R.id.lobbyUserList);
        Button startGameButton = findViewById(R.id.startGameButton);
        Button backButton = findViewById(R.id.backButton);
        Button joinLobbyButton = findViewById(R.id.btn_joinLobby);
        Button leaveLobbyButton = findViewById(R.id.btn_leaveLobby);

        userListAdapter = new UserListAdapter(new ArrayList<>());
        userListView.setAdapter(userListAdapter);
        userListView.setLayoutManager(new LinearLayoutManager(this));

        updateLobby();

        backButton.setOnClickListener(v -> finish());

        joinLobbyButton.setOnClickListener(v -> {
            String username = Username.user.getUsername();
            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.JOINLOBBY.getValue(), username, null,"",
                    "");
            SendMessageService.sendMessage(msg);
        });


        leaveLobbyButton.setOnClickListener(v -> {
            String username = Username.user.getUsername();
            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.LEAVELOBBY.getValue(), username, null,"",
                    "");
            SendMessageService.sendMessage(msg);
        });

        Multiplayer.startGameResponseReceiver = response -> {
            boolean success = response.getBoolean(SUCCESS);
            if(success){
                runOnUiThread(() -> {
                    Log.i(TAG, "Switched to game view");
                    Intent intent = new Intent(this, GameScreen.class);
                    intent.putExtra(USERNAME, getIntent().getStringExtra(USERNAME));

                    ArrayList<String> users = userListAdapter.getUsernameList();
                    intent.putStringArrayListExtra(TAG_USERS, users);

                    startActivity(intent);
                });
            }
        };

        startGameButton.setOnClickListener(v -> {
            String username = Username.user.getUsername();
            JSONObject msg = JSONService.generateJSONObject(
                    ActionValues.STARTGAME.getValue(), username, null,"",
                    "");
            SendMessageService.sendMessage(msg);
            Multiplayer.responseReceiver = response -> {
                boolean success = response.getBoolean(SUCCESS);
                if(success){
                    Log.i(TAG, "Started game successfully");
                }
            };
        });

        responseReceiver = response -> {
            if (response.getBoolean(SUCCESS)) {
                switch (response.getString(ACTION)) {
                    case "joinLobby":
                        runOnUiThread(() -> {
                            try {
                                userListAdapter.addUser(new User(response.getString(USERNAME)));

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case "leaveLobby":
                        runOnUiThread(() -> {
                            try {
                                userListAdapter.removeUser(new User(response.getString(USERNAME)));

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    case "requestLobbyUser":
                        JSONArray users = response.getJSONArray(TAG_USERS);
                        List<User> newUserList = new ArrayList<>();
                        for (int i = 0; i < users.length(); i++) {
                            newUserList.add(new User(users.getString(i)));
                        }
                        runOnUiThread(() -> userListAdapter.setUsers(newUserList));
                        break;
                    default:
                        Log.w(TAG, "Server response has invalid or no sender. Response not routed.");
                }
            }

        };
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateLobby(){
        String username = Username.user.getUsername();
        JSONObject requestLobbyUsersMsg = JSONService.generateJSONObject("requestLobbyUser", username, true, "", "");
        SendMessageService.sendMessage(requestLobbyUsersMsg);

        responseReceiver = response -> {
            if (response.getBoolean(SUCCESS)) {
                JSONArray users = response.getJSONArray(TAG_USERS);
                List<User> newUserList = new ArrayList<>();

                for (int i = 0; i < users.length(); i++) {
                    newUserList.add(new User(users.getString(i)));
                }
                runOnUiThread(() -> {
                    userListAdapter.setUsers(newUserList);
                    userListAdapter.notifyDataSetChanged();
                });
            }
        };
    }
}


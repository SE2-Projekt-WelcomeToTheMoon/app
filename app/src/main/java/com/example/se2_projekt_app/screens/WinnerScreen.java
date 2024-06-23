package com.example.se2_projekt_app.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.json.JSONKeys;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.networking.services.SendMessageService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class WinnerScreen extends Activity {
    private static final String TAG_USERS = "users";
    private static final String SUCCESS = JSONKeys.SUCCESS.getValue();
    @Getter
    @Setter
    private static ResponseReceiver responseReceiver;
    List<User> newUserList = new ArrayList<>();
    private TextView txtViewFirstPlace;
    private TextView txtViewSecondPlace;
    private TextView txtViewThirdPlace;
    private TextView txtViewFourthPlace;
    private final Map<String, Integer> userMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_screen);

        Button btnHome = findViewById(R.id.btn_home);
        Button btnShare = findViewById(R.id.btn_share);
        txtViewFirstPlace = findViewById(R.id.textView_1stPlace);
        txtViewSecondPlace = findViewById(R.id.textView_2ndPlace);
        txtViewThirdPlace = findViewById(R.id.textView_3rdPlace);
        txtViewFourthPlace = findViewById(R.id.textView_4thPlace);

        getPlayerWithPoints();

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(WinnerScreen.this, MainMenu.class);
            startActivity(intent);
        });
        btnShare.setOnClickListener(v -> finish()); // ohne Funktion

        txtViewFirstPlace.setText("");
        txtViewSecondPlace.setText("");
        txtViewThirdPlace.setText("");
        txtViewFourthPlace.setText("");

    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    public void getPlayerWithPoints() {
        String username = Username.user.getUsername();
        JSONObject requestLobbyUsersMsg = new JSONService("winnerScreen", username, true, "", "").generateJSONObject();
        SendMessageService.sendMessage(requestLobbyUsersMsg);

        setResponseReceiver(response -> {
            if (response.getBoolean(SUCCESS)) {
                parseUsersAndPoints(response.getJSONArray(TAG_USERS));
                runOnUiThread(this::updateUI);
            }
        });
    }

    private void parseUsersAndPoints(JSONArray users) throws JSONException {
        userMap.clear();
        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            String playerName = userJson.getString("username");
            int points = userJson.getInt("points");
            userMap.put(playerName, points);
        }
        sortUsersByPoints(userMap);
    }
    private void sortUsersByPoints(Map<String, Integer> userMap) {
        List<Map.Entry<String, Integer>> userList = new ArrayList<>(userMap.entrySet());

        userList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        newUserList.clear();
        for (Map.Entry<String, Integer> entry : userList) {
            newUserList.add(new User(entry.getKey()));
        }
    }

    private void updateUI() {
        try {
            updateTextView(txtViewFirstPlace, 0, "NO WINNER");
            updateTextView(txtViewSecondPlace, 1, "");
            updateTextView(txtViewThirdPlace, 2, "");
            updateTextView(txtViewFourthPlace, 3, "");
        } catch (IndexOutOfBoundsException e) {
            txtViewFirstPlace.setText("");
            txtViewSecondPlace.setText("");
            txtViewThirdPlace.setText("");
            txtViewFourthPlace.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateTextView(TextView textView, int index, String defaultText) {
        if (newUserList.size() > index) {
            String username = newUserList.get(index).getUsername();
            int points = userMap.get(username);
            if (index == 0) {
                textView.setText(username + " - " + points + " Pkt.");
            } else if (index == 1 || index == 2 || index == 3) {
                textView.setText((index+1) + ". " + username + " - " + points + " Pkt.");
            } else {
                textView.setText(defaultText);
            }
        }
    }
}
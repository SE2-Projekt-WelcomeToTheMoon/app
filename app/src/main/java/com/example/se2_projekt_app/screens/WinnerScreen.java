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

public class WinnerScreen extends Activity {
    private static final String TAG_USERS = "users";
    private static final String SUCCESS = JSONKeys.SUCCESS.getValue();
    public static ResponseReceiver responseReceiver;
    List<User> newUserList = new ArrayList<>();
    private TextView txtview_firstPlace;
    private TextView txtview_secondPlace;
    private TextView txtview_thirdPlace;
    private TextView txtview_fourthPlace;
    private final Map<String, Integer> userMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_screen);

        Button btn_home = findViewById(R.id.btn_home);
        Button btn_share = findViewById(R.id.btn_share);
        txtview_firstPlace = findViewById(R.id.textView_1stPlace);
        txtview_secondPlace = findViewById(R.id.textView_2ndPlace);
        txtview_thirdPlace = findViewById(R.id.textView_3rdPlace);
        txtview_fourthPlace = findViewById(R.id.textView_4thPlace);

        getPlayerWithPoints();

        btn_home.setOnClickListener(v -> {
            Intent intent = new Intent(WinnerScreen.this, MainMenu.class);
            startActivity(intent);
        });
        btn_share.setOnClickListener(v -> finish()); // ohne Funktion

        txtview_firstPlace.setText("");
        txtview_secondPlace.setText("");
        txtview_thirdPlace.setText("");
        txtview_fourthPlace.setText("");

    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    public void getPlayerWithPoints() {
        String username = Username.user.getUsername();
        JSONObject requestLobbyUsersMsg = JSONService.generateJSONObject("winnerScreen", username, true, "", "");
        SendMessageService.sendMessage(requestLobbyUsersMsg);

        responseReceiver = response -> {
            if (response.getBoolean(SUCCESS)) {
                parseUsersAndPoints(response.getJSONArray(TAG_USERS));
                runOnUiThread(this::updateUI);
            }
        };
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
            updateTextView(txtview_firstPlace, 0, "NO WINNER");
            updateTextView(txtview_secondPlace, 1, "");
            updateTextView(txtview_thirdPlace, 2, "");
            updateTextView(txtview_fourthPlace, 3, "");
        } catch (IndexOutOfBoundsException e) {
            txtview_firstPlace.setText("");
            txtview_secondPlace.setText("");
            txtview_thirdPlace.setText("");
            txtview_fourthPlace.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateTextView(TextView textView, int index, String defaultText) {
        if (newUserList.size() > index) {
            String username = newUserList.get(index).getUsername();
            int points = userMap.get(username);
            if (index == 0) {
                textView.setText(username + " mit " + points + " Punkten");
            } else if (index == 1 || index == 2 || index == 3) {
                textView.setText((index+1) + ". " + username + " - " + points + " Pkt.");
            } else {
                textView.setText(defaultText);
            }
        }
    }
}
package com.example.se2_projekt_app.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import java.util.List;

public class WinnerScreen extends Activity {
    private static final String TAG_USERS = "users";
    private static final String SUCCESS = JSONKeys.SUCCESS.getValue();
    public static ResponseReceiver responseReceiver;
    List<User> newUserList = new ArrayList<>();
    private TextView txtview_firstPlace;
    private TextView txtview_secondPlace;
    private TextView txtview_thirdPlace;
    private TextView txtview_fourthPlace;


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

        getPlayer();

        btn_home.setOnClickListener(v -> finish());
        btn_share.setOnClickListener(v -> finish()); // ohne Funktion

        txtview_firstPlace.setText("");
        txtview_secondPlace.setText("");
        txtview_thirdPlace.setText("");
        txtview_fourthPlace.setText("");

    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    public void getPlayer() {
        String username = Username.user.getUsername();
        JSONObject requestLobbyUsersMsg = JSONService.generateJSONObject("requestUsersForWinningScreen", username, true, "", "");
        SendMessageService.sendMessage(requestLobbyUsersMsg);

        responseReceiver = response -> {
            if (response.getBoolean(SUCCESS)) {
                parseUsers(response.getJSONArray(TAG_USERS));
                runOnUiThread(this::updateUI);
            }
        };
    }

    private void parseUsers(JSONArray users) throws JSONException {
        newUserList.clear();
        for (int i = 0; i < users.length(); i++) {
            User playerName = new User(users.getString(i));
            newUserList.add(playerName);
        }
    }

    private void updateUI() {
        try {
            updateTextView(txtview_firstPlace, 0, "NO WINNER");
            updateTextView(txtview_secondPlace, 1, "");
            updateTextView(txtview_thirdPlace, 2, "");
            updateTextView(txtview_fourthPlace, 3, "");
        } catch (IndexOutOfBoundsException e) {
            clearTextViews();
        }
    }

    private void updateTextView(TextView textView, int index, String defaultText) {
        if (newUserList.size() > index) {
            textView.setText(newUserList.get(index).getUsername());
        } else {
            textView.setText(defaultText);
        }
    }

    private void clearTextViews() {
        txtview_firstPlace.setText("");
        txtview_secondPlace.setText("");
        txtview_thirdPlace.setText("");
        txtview_fourthPlace.setText("");
    }

    public void sortPlayersByPoints () {
        //TODO: Sort the player in the List based on their score
    }
}


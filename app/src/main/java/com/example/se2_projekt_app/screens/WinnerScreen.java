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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WinnerScreen extends Activity {


    public static ResponseReceiver responseReceiver;
    private TextView txtview_firstPlace;
    private TextView txtview_secondPlace;
    private TextView txtview_thirdPlace;
    private TextView txtview_fourthPlace;
    private Button btn_home;
    private Button btn_share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_screen);

        btn_home = findViewById(R.id.btn_home);
        btn_share = findViewById(R.id.btn_share);
        txtview_firstPlace = findViewById(R.id.textView_1stPlace);
        txtview_secondPlace = findViewById(R.id.textView_2ndPlace);
        txtview_thirdPlace = findViewById(R.id.textView_3rdPlace);
        txtview_fourthPlace = findViewById(R.id.textView_4thPlace);


        btn_home.setOnClickListener(v -> finish());
        btn_share.setOnClickListener(v -> finish()); // ohne Funktion

        txtview_firstPlace.setText("");
        txtview_secondPlace.setText("");
        txtview_thirdPlace.setText("");
        txtview_fourthPlace.setText("");

    }
}



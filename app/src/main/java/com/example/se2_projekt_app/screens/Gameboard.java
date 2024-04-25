package com.example.se2_projekt_app.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.game.CardController;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

public class Gameboard extends AppCompatActivity {
    CardController cardController=new CardController();

    public static ResponseReceiver responseReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.singleplayer);
        findViewById(R.id.singleplayer_back).setOnClickListener(v -> finish());

        // Handling response from server
        responseReceiver = response -> {
            String cardMessage = response.getString("message");
            cardController.extractCardsFromServerString(cardMessage);
        };
    }
}

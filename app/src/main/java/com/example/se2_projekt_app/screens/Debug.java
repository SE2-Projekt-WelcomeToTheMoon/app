package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.game.CardCombination;
import com.example.se2_projekt_app.game.CardController;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

public class Debug extends Activity {
    private CardController cardController;
    public static ResponseReceiver responseReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);
        this.cardController=new CardController();
        findViewById(R.id.debug_back).setOnClickListener(v -> finish());

        // Handling response from server
        responseReceiver = response -> {
            String cardMessage = response.getString("message");
            cardController.extractCardsFromServerString(cardMessage);
        };

    }
}

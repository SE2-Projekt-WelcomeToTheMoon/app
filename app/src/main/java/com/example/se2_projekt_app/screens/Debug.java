package com.example.se2_projekt_app.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        findViewById(R.id.buttonTest).setOnClickListener(this::updateCardsTest);

        // Handling response from server
        responseReceiver = response -> {
            //TODO Add functionality with actual images of cards
            String cardMessage = response.getString("message");
            cardController.extractCardsFromServerString(cardMessage);
            updateTextViews();
        };

    }

    private void updateCardsTest(View view) {
        String testDataString="0-ROBOTER-9-WASSER;1-PFLANZE-3-ENERGIE;2-RAUMANZUG-15-PLANNUNG";
        cardController.extractCardsFromServerString(testDataString);
        updateTextViews();

    }
    @SuppressLint("DefaultLocale")
    private void updateTextViews(){
        TextView cardOne = findViewById(R.id.cardOne);
        TextView cardTwo = findViewById(R.id.cardTwo);
        TextView cardThree = findViewById(R.id.cardThree);
        String inputString="Number %d, Symbol %s\nNextSymbol %s";
        CardCombination[] com=cardController.getCurrentCombination();
        cardOne.setText(String.format(inputString,com[0].getCurrentNumber().getValue(),com[0].getCurrentSymbol().toString(),com[0].getNextSymbol().toString()));
        cardTwo.setText(String.format(inputString,com[1].getCurrentNumber().getValue(),com[1].getCurrentSymbol().toString(),com[1].getNextSymbol().toString()));
        cardThree.setText(String.format(inputString,com[2].getCurrentNumber().getValue(),com[2].getCurrentSymbol().toString(),com[2].getNextSymbol().toString()));

    }
}

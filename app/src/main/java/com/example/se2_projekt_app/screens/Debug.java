package com.example.se2_projekt_app.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.game.CardCombination;
import com.example.se2_projekt_app.game.CardController;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

public class Debug extends Activity {
    private CardController cardController;
    public static ResponseReceiver responseReceiver;

    private DrawerLayout drawerLayout;
    private Button toggleDrawerButton;
    private Button closeDrawerButton;

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
      drawerLayout = findViewById(R.id.drawer_layout);
        toggleDrawerButton = findViewById(R.id.toggle_drawer_button);
        closeDrawerButton = findViewById(R.id.close_drawer_button);

        toggleDrawerButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        closeDrawerButton.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Translate the button with the drawer slide
                toggleDrawerButton.setTranslationX(slideOffset * drawerView.getWidth());
                toggleDrawerButton.setVisibility(slideOffset == 0 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                toggleDrawerButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                toggleDrawerButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Not used
            }
        });

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

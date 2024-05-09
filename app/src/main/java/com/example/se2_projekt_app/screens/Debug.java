package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.game.CardController;
import com.example.se2_projekt_app.game.CardDrawView;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

public class Debug extends Activity {
    private CardController cardController;
    public static ResponseReceiver responseReceiver;

    private DrawerLayout drawerLayout;
    private CardDrawView cardDrawView;
    private Button toggleDrawerButton;
    private Button closeDrawerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);
        this.cardController=new CardController();
        findViewById(R.id.debug_back).setOnClickListener(v -> finish());
        findViewById(R.id.buttonTest).setOnClickListener(this::updateCardsTest);
        this.cardDrawView=findViewById(R.id.cardDrawView);

        // Handling response from server
        responseReceiver = response -> {

            String cardMessage = response.getString("message");
            cardController.extractCardsFromServerString(cardMessage);
            cardDrawView.updateCanvas(cardController.getCurrentCombination());
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
        String testDataString="0-ROBOTER-9-WASSER;1-PLANNUNG-3-ENERGIE;2-RAUMANZUG-15-PLANNUNG";
        cardController.extractCardsFromServerString(testDataString);
        cardDrawView.updateCanvas(cardController.getCurrentCombination());
    }
  

}

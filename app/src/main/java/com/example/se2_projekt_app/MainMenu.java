package com.example.se2_projekt_app;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;

public class MainMenu extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button startSP = findViewById(R.id.startSP);
        Button startMP = findViewById(R.id.startMP);
        Button settings = findViewById(R.id.settings);
        Button highscore = findViewById(R.id.highscore);
        Button exit = findViewById(R.id.exit);


        startSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSP(v);
            }
        });
        startMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMP(v);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings(v);
            }
        });
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHighscore(v);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit(v);
            }
        });

    }

    public void startSP(View view) {
        // Start the game in Singleplayer
    }

    public void startMP(View view) {
        // Start the game in Multiplayer
    }

    public void openSettings(View view) {
        // Open the settings
    }

    public void openHighscore(View view) {
        // Open the highscore
    }

    public void exit(View view) {
        // Exit the game
        finish();
    }
}

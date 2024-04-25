package com.example.se2_projekt_app.screens;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;

import com.example.se2_projekt_app.R;

public class Highscore extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);

        findViewById(R.id.highscore_back).setOnClickListener(v -> finish());
    }
}

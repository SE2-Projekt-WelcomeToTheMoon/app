package com.example.se2_projekt_app.screens;

import android.os.Bundle;
import android.app.Activity;

import com.example.se2_projekt_app.R;

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        findViewById(R.id.settings_back).setOnClickListener(v -> finish());
    }
}

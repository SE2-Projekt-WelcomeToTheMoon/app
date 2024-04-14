package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.se2_projekt_app.R;

public class Debug extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);

        Button back = findViewById(R.id.debug_back);

        back.setOnClickListener(v -> finish());
    }
}

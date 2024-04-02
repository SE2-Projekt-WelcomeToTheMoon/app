package com.example.se2_projekt_app.screens;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;

import com.example.se2_projekt_app.R;

public class Highscore extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);

        Button back = findViewById(R.id.highscore_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

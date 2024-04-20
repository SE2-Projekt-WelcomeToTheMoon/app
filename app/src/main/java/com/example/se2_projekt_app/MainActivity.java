package com.example.se2_projekt_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se2_projekt_app.screens.MainMenu;

public class MainActivity extends Activity {

    TextView textViewServerResponse;

    // currently network stuff is ignored to forward this all to main menu
    // this is done because of the merge conflicts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonConnect = findViewById(R.id.buttonConnect);

        Button buttonSendMsg = findViewById(R.id.buttonSendMsg);

        textViewServerResponse = findViewById(R.id.textViewResponse);

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);

        //stop this activity, cause it just starts the MainMenu
        finish();
    }

}
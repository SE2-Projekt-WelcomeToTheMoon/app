package com.example.se2_projekt_app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

// still let this be the "main one" to maybe start more than just the MainMenu.
// maybe a server connection?
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);

        //stop this activity, cause it just starts the MainMenu
        finish();
    }
}
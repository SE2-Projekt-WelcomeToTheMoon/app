package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

public class Debug extends Activity {

    private DrawerLayout drawerLayout;
    private Button toggleDrawerButton;
    private Button closeDrawerButton;
    private ProgressBar progressBar;
    private TextView view;
    public static ResponseReceiver responseReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);

        findViewById(R.id.debug_back).setOnClickListener(v -> finish());

        drawerLayout = findViewById(R.id.drawer_layout);
        toggleDrawerButton = findViewById(R.id.toggle_drawer_button);
        closeDrawerButton = findViewById(R.id.close_drawer_button);
//        progressBar = findViewById(R.id.progressbar);
        view = findViewById(R.id.rocket_count);
        view.setText("22"); // Testing purposes
//        progressBar.setMax(57);
//        progressBar.setProgress(15);
//        final int[] progress = {0};

//                progress[0] += user.getRockets;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        progressBar.setProgress(progress[0], true);
//                        view.setText(String.valueOf(Username.user.getRockets()));
                    }
                });

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
}

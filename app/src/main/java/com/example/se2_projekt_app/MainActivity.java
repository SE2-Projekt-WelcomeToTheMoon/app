package com.example.se2_projekt_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.app.Activity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.se2_projekt_app.networking.WebSocketClient;
import com.example.se2_projekt_app.screens.MainMenu;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    TextView textViewServerResponse;

    WebSocketClient networkHandler;

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
        //buttonConnect.setOnClickListener(v -> connectToWebSocketServer());

        Button buttonSendMsg = findViewById(R.id.buttonSendMsg);
        //buttonSendMsg.setOnClickListener(v -> sendMessage());

        textViewServerResponse = findViewById(R.id.textViewResponse);

        networkHandler = new WebSocketClient();


        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);

        //stop this activity, cause it just starts the MainMenu
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkHandler != null) {
            networkHandler.closeConnection(); // Ensure WebSocket is closed
        }
    }
}
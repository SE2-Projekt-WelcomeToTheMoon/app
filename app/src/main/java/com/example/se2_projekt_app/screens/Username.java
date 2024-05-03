package com.example.se2_projekt_app.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.networking.WebSocketClient;
import com.example.se2_projekt_app.networking.json.ActionValues;
import com.example.se2_projekt_app.networking.json.JSONKeys;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

import org.json.JSONObject;

public class Username extends Activity {

    //Object to establish connection to server
    public static WebSocketClient webSocketClient;

    //Object implements method to handle response received from server
    public static ResponseReceiver responseReceiver;

    //Create user object
    public static User user;

    //Tag needed for logger
    private static final String TAG = "Username";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_username);

        Button setUsernameButton = findViewById(R.id.setUsername);
        TextView inputText = findViewById(R.id.inputUsername);
        TextView outputText = findViewById(R.id.outputText);

        // Establishing connection to server via new thread
        webSocketClient = new WebSocketClient();
        Thread thread = new Thread(webSocketClient);
        thread.start();


        setUsernameButton.setOnClickListener(v -> {

            // Handle response from server
            responseReceiver = response -> {
                String username = response.getString(JSONKeys.USERNAME.getValue());
                boolean success = response.getBoolean(JSONKeys.SUCCESS.getValue());
                String message = response.getString(JSONKeys.MESSAGE.getValue());
                if(success){
                    user = new User(username);
                    Log.i(TAG, "Username set to: "+ username);

                    Intent intent = new Intent(this, MainMenu.class);
                    startActivity(intent);
                }else{
                    runOnUiThread(() -> {
                        outputText.setText(message);
                        outputText.setBackgroundColor(0x8003DAC5);
                            });
                    Log.w(TAG, "Username couldn't be set.");
                }
            };

            // Generating JSONObject to send message to server
            JSONObject msg = JSONService.generateJSONObject(ActionValues.REGISTERUSER.getValue(),
                    inputText.getText().toString(), null,"", "");

            // Sending message to server to register user
            webSocketClient.sendMessageToServer(msg);
        });
    }
}

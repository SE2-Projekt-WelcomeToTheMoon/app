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
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.PostOffice;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;

import org.json.JSONObject;

public class Username extends Activity {

    //Object to establish connection to server
    public static WebSocketClient webSocket;

    //ResponseHandler passed when connection to server is being established
    public PostOffice responseHandler = new PostOffice();

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

        // Establishing connection to server
        webSocket = new WebSocketClient();
        webSocket.connectToServer(responseHandler);


        setUsernameButton.setOnClickListener(v -> {

            // Handle response from server
            responseReceiver = response -> {
                boolean success = response.getBoolean("success");
                if(success){
                    user = new User(response.getString("username"));
                    Log.i(TAG, "Username set to: "+ response.getString("username"));

                    Intent intent = new Intent(this, MainMenu.class);
                    startActivity(intent);
                }else{
                    inputText.setText("Username could not be set, please try again or another one.");
                    Log.w(TAG, "Username couldn't be set: " +
                            response.getString("message"));
                }
            };

            // Generating JSONObject to send message to server
            JSONObject msg = JSONService.generateJSONObject(ActionValues.REGISTERUSER.getValue(),
                    inputText.getText().toString(), null,"", "");

            // Sending message to server to register user
            Username.webSocket.sendMessageToServer(msg);
        });
    }
}

package com.example.se2_projekt_app.networking;

import android.util.Log;
import android.widget.TextView;

import com.example.se2_projekt_app.networking.JSON.GenerateJSONObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionHandler {
    TextView textViewServerResponse;
    JSONObject message;
    WebSocketClient networkHandler = new WebSocketClient();

    public void connectToWebSocketServer() {
        // register a handler for received messages when setting up the connection
        this.networkHandler.connectToServer(this::messageReceivedFromServer);
    }

    public void sendMessage(JSONObject message) throws JSONException {
        this.networkHandler.sendMessageToServer(message);

    }

    public void messageReceivedFromServer(JSONObject message) throws JSONException {
        Log.d("Network", message.get("Message").toString());
        this.textViewServerResponse.setText(message.get("Message").toString());
    }
}

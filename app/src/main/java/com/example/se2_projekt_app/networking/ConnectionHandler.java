package com.example.se2_projekt_app.networking;

import android.util.Log;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import lombok.Getter;

public class ConnectionHandler {
    TextView textViewServerResponse;
    @Getter
    JSONObject message, response;
    WebSocketClient networkHandler = new WebSocketClient();

    public void connectToWebSocketServer() {
        // register a handler for received messages when setting up the connection
        this.networkHandler.connectToServer(this::messageReceivedFromServer);
    }

    public void sendMessage(JSONObject message) throws JSONException {
        this.networkHandler.sendMessageToServer(message);

    }

    public void messageReceivedFromServer(String message) throws JSONException {
        if(message == null){
            System.out.println("No message from server.");
        }
        else this.response = new JSONObject(message);

//        Log.d("Network", this.response.getString("action"));
//        this.textViewServerResponse.setText(this.response.getString("action"));
    }
}

package com.example.se2_projekt_app.networking;

import android.util.Log;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import lombok.Getter;

/**
 * Handles connection and messages to and from the server.
 */
public class ConnectionHandler {
    @Getter
    private JSONObject response;
    WebSocketClient networkHandler = new WebSocketClient();
    private ServerResponseListener serverResponseListener = null;

    /**
     * Creates a webSocket connection to the server and get a message handler passed as parameter.
     */
    public void connectToWebSocketServer() {
        this.networkHandler.connectToServer(this::messageReceivedFromServer);
    }

    /**
     * Sends a message via the webSocket to the server.
     * @param message Message to send to server.
     */
    public void sendMessage(JSONObject message){
        this.networkHandler.sendMessageToServer(message);

    }


    /**
     * Hanldes received messages from the server.
     * @param message Message received from server.
     * @throws JSONException
     */
    public void messageReceivedFromServer(String message) throws JSONException {
        if(message == null){
            System.out.println("No message from server.");
        }
        else this.response = new JSONObject(message);

        if(serverResponseListener != null) {
            this.serverResponseListener.onResponseReceived(this.response);
        }
        Log.d("Network", this.response.getString("action"));
    }

    /**
     * Initialises object of ServerResponseListener.
     * @param listener
     */
    public void setServerResponseListener(ServerResponseListener listener) {
        this.serverResponseListener = listener;
    }
}
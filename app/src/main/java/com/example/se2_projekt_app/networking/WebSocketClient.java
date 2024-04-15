package com.example.se2_projekt_app.networking;

import android.util.Log;
import androidx.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {
    /**
     * Client for communication with backend.
     */

    private final String websocket_url= "ws://10.0.2.2:8080/welcome-to-the-moon"; //IP vom localhost
    private WebSocket webSocket;

    /**
     * Creates connection to the server.
     * @param messageHandler Handles message received from server.
     */
    public void connectToServer(WebSocketMessageHandler<String> messageHandler){
        if (messageHandler == null)
            throw new IllegalArgumentException("A messageHandler is needed");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(this.websocket_url).build();

        this.webSocket = client.newWebSocket(request, new WebSocketListener() {
            /**
             * Logic that gets executed on connection to server.
             * @param webSocket WebSocket connection to server.
             * @param response Response from server.
             */
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                Log.d("Network", "Connected");
            }

            /**
             * Passes received message from server to message Handler.
             * @param webSocket WebSocket connection to server.
             * @param message Message received from server.
             */

            public void onMessage(@NonNull WebSocket webSocket, @NonNull String message) {
                try {
                    messageHandler.onMessageReceived(message);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            /**
             * LOgic that gets executes if connection to server failes.
             * @param webSocket WebSocket connection to server.
             * @param tw Exception that gets thrown.
             * @param response Response from server.
             */

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable tw, Response response) {
                Log.d("Network", "Connection failure: " + tw.getMessage());

                if (response != null) {
                    Log.d("Network", "Response message: " + response.message());
                } else {
                    Log.d("Network", "Response object ist null.");
                }
            }
        });
    }

    /**
     * Sends message to server.
     * @param msg Message to send.
     */
    public void sendMessageToServer(JSONObject msg){
        this.webSocket.send(msg.toString());
    }

    /**
     * Closes webSocket connection to server.
     */
    @Override
    protected void finalize() throws Throwable{
        try{
            webSocket.close(1000, "Closing");
        } finally{
            super.finalize();
        }
    }

    /**
     * Method to manually close webSocket connection to server.
     */
    public void closeConnection() {
        if (webSocket != null) {
            webSocket.close(1000, "ActivityDestroyed"); // Use proper close code and reason
        }
    }
}

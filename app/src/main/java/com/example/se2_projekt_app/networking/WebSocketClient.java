package com.example.se2_projekt_app.networking;

import android.util.Log;

import com.example.se2_projekt_app.screens.MainMenu;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient extends Thread{
    /**
     * Client für die Kommunikation mit dem Backend.
     */

    private final String websocket_url= "ws://10.0.2.2:8080/welcome-to-the-moon"; //IP vom localhost
    private WebSocket webSocket;

    /**
     * Stellt eine Verbindung zum Server her.
     * @param messageHandler
     */
    public void connectToServer(WebSocketMessageHandler<String> messageHandler){
        if (messageHandler == null)
            throw new IllegalArgumentException("Ein messageHandler wird benötigt");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(this.websocket_url).build();

        this.webSocket = client.newWebSocket(request, new WebSocketListener() {
            /**
             * Logik die bei bestehender Verbindung mit dem Server ausgeführt wird.
             * @param webSocket
             * @param response
             */
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                Log.d("Network", "Verbunden");
            }

            /**
             *
             * @param webSocket
             * @param json
             */

            public void onMessage(WebSocket webSocket, String json) {
                try {
                    messageHandler.onMessageReceived(json);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            /**
             * Logik die bei fehlgeschlagenem Verbindungsaufbau mit dem Server ausgeführt wird.
             * @param webSocket
             * @param tw
             * @param response
             */

            @Override
            public void onFailure(WebSocket webSocket, Throwable tw, Response response) {
                Log.d("Network", "Verbindung fehlgeschlagen: " + tw.getMessage());

                // It's not safe to assume the response message is always JSON formatted or that it's even present.
                // In case of a failure, it's more important to log or handle the error rather than parsing the response message.
                if (response != null) {
                    Log.d("Network", "Response message: " + response.message());
                } else {
                    Log.d("Network", "Response object ist null, Nachricht kann nicht abgerufen werden.");
                }
            }
        });
    }

    /**
     * Schickt einen String an den Server.
     * @param msg
     */
    public void sendMessageToServer(JSONObject msg){
        this.webSocket.send(msg.toString());
    }

    /**
     * Schließt die Serververbindung.
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable{
        try{
            MainMenu.connectionHandler.networkHandler.webSocket.close(1000, "Closing");
        } finally{
            super.finalize();
        }
    }

    /**
     * Für Testzwecke benötigt.
     * @param first
     * @param second
     * @return
     */
    // Simple method to demonstrate unit testing and test coverage with sonarcloud
    public static String concatenateStrings(String first, String second){
        return first + second;
    }

    @Override
    public void run() {

    }

    public void closeConnection() {
        if (webSocket != null) {
            webSocket.close(1000, "ActivityDestroyed"); // Use proper close code and reason
        }
    }
}

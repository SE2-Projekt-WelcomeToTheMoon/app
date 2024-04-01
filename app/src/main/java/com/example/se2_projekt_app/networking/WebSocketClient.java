package com.example.se2_projekt_app.networking;

import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient{
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

        webSocket = client.newWebSocket(request, new WebSocketListener() {
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
             * @param text
             */

            @Override
            public void onMessage(WebSocket webSocket, String text){
                messageHandler.onMessageReceived(text);
            }

            /**
             * Logik die bei fehlgeschlagenem Verbindungsaufbau mit dem Server ausgeführt wird.
             * @param webSocket
             * @param tw
             * @param response
             */

            @Override
            public void onFailure(WebSocket webSocket, Throwable tw, Response response){
                Log.d("Network", "Verbindung fehlgeschlagen: " + tw.getMessage() + response.message());
            }
        });
    }

    /**
     * Schickt einen String an den Server.
     * @param msg
     */
    public void sendMessageToServer(String msg){
        webSocket.send(msg);
    }

    /**
     * Schließt die Serververbindung.
     * @throws Throwable
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
     * Für Testzwecke benötigt.
     * @param first
     * @param second
     * @return
     */
    // Simple method to demonstrate unit testing and test coverage with sonarcloud
    public static String concatenateStrings(String first, String second){
        return first + second;
    }
}

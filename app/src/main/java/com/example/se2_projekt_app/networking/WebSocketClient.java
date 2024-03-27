package com.example.se2_projekt_app.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {
    /**
     * Client Seite für die Kommunikation des Frontend mit dem Backend
     */

    private String websocket_url;
    private int port;
    private WebSocket webSocket;
    public WebSocketClient(String url, int port){
        this.websocket_url = url;
        this.port = port;
    }

    /**
     *
     * @param messageHandler
     */
    public void connectToServer(WebSocketMessageHandler messageHandler) throws IllegalAccessException {
        if (messageHandler == null)
            throw new IllegalAccessException("Ein messageHandler wird benötigt");


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(this.websocket_url).build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                Log.d("Network", "Verbunden");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text){
                messageHandler.onMessageReceived(text);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable tw, Response response){
                Log.d("Network", "Verbindung fehlgeschlagen");
            }
        });
    }

    public void sendMessageToServer(String msg){
        webSocket.send(msg);
    }

    @Override
    public void finalize() throws Throwable{
        try{
            webSocket.close(1000, "Closing");
        } finally{
            super.finalize();
        }
    }

    public static String concatenateStrings(String first, String second){
        return first + second;
    }
}

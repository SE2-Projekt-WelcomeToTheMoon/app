package com.example.se2_projekt_app.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.se2_projekt_app.networking.json.JSONKeys;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.PostOffice;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.networking.services.SendMessageService;
import com.example.se2_projekt_app.screens.Username;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import lombok.Setter;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Establishes and manages connection to the backend server.
 */
public class WebSocketClient implements Runnable{

    private static final String TAG = "WebSocketClient";

    private WebSocket websocket;
    //ResponseHandler passed when connection to server is being established
    private final PostOffice responseHandler = new PostOffice();
    private boolean connectionUp = false;
    @Setter
    private static boolean disconPurpously = false;
    private ResponseReceiver response;

    /**
     * Establishes connection to server and provides methods to handle it.
     * @param messageHandler Handles received messages from server.
     */
    public void connectToServer(PostOffice messageHandler) {
        if (messageHandler == null) {
            throw new IllegalArgumentException("A message handler is required.");
        }

        final String WEBSOCKET_URL = "ws://se2-demo.aau.at:53205/welcome-to-the-moon";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(WEBSOCKET_URL).build();
        websocket = client.newWebSocket(request, new WebSocketListener() {
            /**
             * Contains logic that is being executed when connection has been established.
             * @param webSocket WebSocket that established connection to server.
             * @param response Response from server.
             */
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                super.onOpen(webSocket, response);
                connectionUp = true;
                Log.i(TAG, "Connection to server established.");
            }

            /**
             * Receives messages from server and passes them to the PostOffice class.
             * @param webSocket WebSocket that established connection to server.
             * @param text Response from server.
             */
            @SneakyThrows
            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);
                JSONObject message;
                try {
                    message = new JSONObject(text);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                messageHandler.routeResponse(message);
                Log.i(TAG, "Message received from server.\n" + text);
            }

            /**
             * Contains logic that is being executed when connection is closing.
             * @param webSocket WebSocket that established connection to server.
             * @param code Status code of connection.
             * @param reason Reason why connection is being closed.
             */
            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.i(TAG, "Connection to server is closing... Status: " + code);
            }

            /**
             * Contains logic that is being executed after connection has been closed.
             * @param webSocket WebSocket that established connection to server.
             * @param code Status code of connection.
             * @param reason Reason why connection has been closed.
             */
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                connectionUp = false;
                Log.i(TAG,"Connection to server closed. Status: " + code);
            }

            /**
             * Contains logic that is being executed when connection has been terminated unexpectedly.
             * Reconnects to server when null response has been received from server.
             * @param webSocket WebSocket that established connection to server.
             * @param t Exception why connection terminated.
             * @param response Response from server.
             */
            @SneakyThrows
            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                if(checkResponse(response)) Log.w(TAG,"Connection termination unexpected. Error: "
                        + t.getMessage());
                connectionUp = false;
                TimeUnit.SECONDS.sleep(2);
                if(!disconPurpously){
                    Log.i(TAG, "Trying to reconnect...");
                    if(!connectionUp) reconnectToServer();
                    Log.i(TAG, "Reconnected.");
                }
            }
        });
    }

    /**
     * Method to send a message to the server, receives a JSON Object parsed to String as parameter.
     * @param message JSON Object parsed to String to be send to server.
     */
    public void sendMessageToServer(String message) {
        websocket.send(message);
        Log.i(TAG, "Message sent to server.");
    }

    /**
     * Method to gracefully disconnect from server.
     * @throws Throwable Exception during closure of server connection.
     */
    public void disconnectFromServer() throws Throwable {
        try {
            websocket.close(1000, "Closing");
            connectionUp = false;
            Log.i(TAG, "Begin closing connection to server...");
        } finally {
            super.finalize();
        }
    }

    /**
     * Helper method to check the if server sent a response or not.
     * @param response Response to check.
     * @return Boolean value if response contains (true) a message or not (false).
     */
    private boolean checkResponse(Response response) {
        if (response == null) {
            Log.w(TAG,"No response received.");
            return true;
        } else {
            Log.i(TAG,"Response received.");
            return false;
        }
    }

    private boolean reconnectToServer(){
        Username.webSocketClient.connectToServer(responseHandler);
        JSONObject message = new JSONService(
                JSONKeys.RECONNECT.getValue(), Username.user.getUsername(),
                true, "", "").generateJSONObject();
        SendMessageService.sendMessage(message);
        response = msg -> {
            boolean success = msg.getBoolean(JSONKeys.SUCCESS.getValue());
            if (success) {
                connectionUp = true;
            }
        };
        if(connectionUp) return true;
        return false;
    }

    /**
     * Implemented run() method from interface Runnable. Calls the connectToServer method and passes
     * the responseHandler to the method.
     */
    @Override
    public void run() {
        connectToServer(responseHandler);
    }
}

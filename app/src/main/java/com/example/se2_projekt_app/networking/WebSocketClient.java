package com.example.se2_projekt_app.networking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Establishes and manages connection to the backend server.
 */
public class WebSocketClient {

    private final String WEBSOCKET_URL = "ws://10.0.2.2:8080/welcome-to-the-moon";

    private final Logger logger = LogManager.getLogger(WebSocketClient.class);

    private WebSocket websocket;


    /**
     * Establishes connection to server and handles it.
     * @param messageHandler Handles received messages from server.
     */
    public void connectToServer(ResponseHandler<String> messageHandler) {
        if (messageHandler == null) {
            throw new IllegalArgumentException("A message handler is required.");
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(WEBSOCKET_URL).build();
        websocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                super.onOpen(webSocket, response);
                logger.info("Connection to server established.");
            }

            @SneakyThrows
            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);
                JSONObject message = null;
                try {
                    message = new JSONObject(text);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                messageHandler.onMessageReceived(message);
                logger.info("Message received from server.\n" + text);
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
                logger.info("Connection to server is closing... Status: " + code);
            }

            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                logger.info("Connection to server closed. Status: " + code);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                if(checkResponse(response)) logger.warn("Connection termination unexpected. Error: "
                        + t.getMessage());
            }
        });
    }

    public void sendMessageToServer(JSONObject msg) {
        String messageToSend = msg.toString();
        websocket.send(messageToSend);
    }

    public void disconnectFromServer() throws Throwable {
        try {
            websocket.close(1000, "Closing");
            logger.info("Begin closing connection to server...");
        } finally {
            super.finalize();
        }
    }

    private boolean checkResponse(Response response) {
        if (response == null) {
            logger.warn("No response received.");
            return false;
        } else {
            logger.info("Response received.");
            return true;
        }}
}

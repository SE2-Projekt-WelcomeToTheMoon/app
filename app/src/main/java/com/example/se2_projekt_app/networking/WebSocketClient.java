package com.example.se2_projekt_app.networking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.se2_projekt_app.networking.responsehandler.PostOffice;
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
     * Establishes connection to server and provides methods to handle it.
     * @param messageHandler Handles received messages from server.
     */
    public void connectToServer(PostOffice messageHandler) {
        if (messageHandler == null) {
            throw new IllegalArgumentException("A message handler is required.");
        }

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
                logger.info("Connection to server established.");
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
                JSONObject message = null;
                try {
                    message = new JSONObject(text);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                messageHandler.routeResponse(message);
                logger.info("Message received from server.\n" + text);
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
                logger.info("Connection to server is closing... Status: " + code);
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
                logger.info("Connection to server closed. Status: " + code);
            }

            /**
             * Contains logic that is being executed when connection has been terminated unexpectedly.
             * @param webSocket WebSocket that established connection to server.
             * @param t Exception why connection terminated.
             * @param response Response from server.
             */
            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                if(checkResponse(response)) logger.warn("Connection termination unexpected. Error: "
                        + t.getMessage());
            }
        });
    }

    /**
     * Method to send a message to the server, receives a JSON Object as parameter and casts it
     * to string to be able to send to server.
     * @param msg JSON Object to be send to server.
     */
    public void sendMessageToServer(JSONObject msg) {
        String messageToSend = msg.toString();
        websocket.send(messageToSend);
    }

    /**
     * Method to gracefully disconnect from server.
     * @throws Throwable Exception during closure of server connection.
     */
    public void disconnectFromServer() throws Throwable {
        try {
            websocket.close(1000, "Closing");
            logger.info("Begin closing connection to server...");
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
            logger.warn("No response received.");
            return false;
        } else {
            logger.info("Response received.");
            return true;
        }}
}

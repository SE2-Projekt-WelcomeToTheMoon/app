package com.example.se2_projekt_app.networking;

import org.json.JSONException;

/**
 * Interface to handle messages from server.
 * @param <T> Any object received from server.
 */
public interface WebSocketMessageHandler<T> {
    void onMessageReceived(T message) throws JSONException;
}

package com.example.se2_projekt_app.networking;

import org.json.JSONException;

public interface WebSocketMessageHandler<T> {
    void onMessageReceived(T message) throws JSONException;
}

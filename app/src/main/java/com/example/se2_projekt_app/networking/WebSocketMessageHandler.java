package com.example.se2_projekt_app.networking;

public interface WebSocketMessageHandler<T> {
    void onMessageReceived(T message);
}
